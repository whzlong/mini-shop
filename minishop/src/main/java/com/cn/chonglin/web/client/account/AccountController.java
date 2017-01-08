package com.cn.chonglin.web.client.account;

import com.cn.chonglin.bussiness.base.service.UserService;
import com.cn.chonglin.bussiness.item.domain.Item;
import com.cn.chonglin.bussiness.item.service.ItemService;
import com.cn.chonglin.common.AppException;
import com.cn.chonglin.common.ResponseResult;
import com.cn.chonglin.web.client.account.form.RegisterForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.List;

/**
 * 帐户管理控制器
 *
 * @author wu
 */
@Controller
@RequestMapping("client")
public class AccountController {
    @Autowired
    private ItemService itemService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "register", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody
    ResponseResult<Object> register(@Valid RegisterForm userForm, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return ResponseResult.error(bindingResult.getFieldErrors());
        }

        try{

            if(!userForm.getPassword().equals(userForm.getConfirmPassword())){
                throw new AppException("The password and its confirm are not the same");
            }

            userService.save(userForm.toDomain());

            return ResponseResult.success(null);

        }catch (AppException ex){
            return ResponseResult.error(ex.getCode(), ex.getMessage());
        }
    }

    /**
     * 验证帐户
     * @param id
     *      验证码
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/confirm/{id}", method = RequestMethod.GET)
    public String confirmRegister(@PathVariable String id, ModelMap modelMap){
        String infoMsg = "Your account has been validated.";

        try{
            userService.confirmRegister(id);

        }catch (AppException ex){
            infoMsg = ex.getMessage();
        }

        modelMap.addAttribute("infoMsg", infoMsg);

        //打折维修服务
        List<Item> discountItems = itemService.findDiscountItems();

        //新上线维修服务
        List<Item> newItems = itemService.findNewItems();

        modelMap.addAttribute("discountItems", discountItems);
        modelMap.addAttribute("newItems", newItems);

        return "client/index";

    }

    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String loginPage(ModelMap modelMap, String error , String logout){

        if(error != null){
            modelMap.addAttribute("error", "Your username or password is invalid.");
        }

//        if(logout!= null){
//            modelMap.addAttribute("message", "You have been logged out successfully.");
//        }

        return "client/login";
    }

    @RequestMapping(value = "logout")
    public String logout(){
        return "redirect:/";
    }
}
