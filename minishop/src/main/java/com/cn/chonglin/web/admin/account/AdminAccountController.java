package com.cn.chonglin.web.admin.account;

import com.cn.chonglin.bussiness.base.domain.User;
import com.cn.chonglin.bussiness.base.service.UserService;
import com.cn.chonglin.common.AppException;
import com.cn.chonglin.web.admin.account.form.UserForm;
import com.cn.chonglin.web.admin.account.vo.UserListPageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@Controller
@RequestMapping("admin")
public class AdminAccountController {
    /**
     * 列表显示数量
     */
    private static final int listSize = 5;

    /**
     * 显示分页数量
     */
    private static final int pageSize = 5;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "user-list", method = {RequestMethod.GET, RequestMethod.POST})
    public String list(@RequestParam(required = false, defaultValue = "") String email
                       , @RequestParam(required = false, defaultValue = "") String firstname
                       , @RequestParam(required = false, defaultValue = "0") String state
                        , @RequestParam(required = false, defaultValue = "1") @Min(1) int beginPage
                        , @RequestParam(required = false, defaultValue = "1") @Min(1) int currentPage
                        ,ModelMap modelMap){
        int totalPage = 0;
        int recordCount = userService.count(email, firstname, Integer.valueOf(state));

        List<User> users = userService.query(email, firstname, Integer.valueOf(state), listSize, listSize*(currentPage - 1));

        modelMap.addAttribute("users", users);

        if(recordCount % listSize == 0){
            totalPage = recordCount/listSize;
        }else{
            totalPage = recordCount/listSize + 1;
        }

        int endPage;
        if(totalPage - beginPage >= pageSize){
            endPage = beginPage + pageSize - 1;
        }else {
            endPage = totalPage;
        }

        UserListPageVo listPageVo = new UserListPageVo();

        listPageVo.setTotalPage(totalPage);
        listPageVo.setCurrentPage(currentPage);
        listPageVo.setBeginPage(beginPage);
        listPageVo.setEndPage(endPage);
        listPageVo.setLeftPage(totalPage - beginPage);
        listPageVo.setListSize(listSize);
        listPageVo.setPageSize(pageSize);


        listPageVo.setEmail(email);
        listPageVo.setFirstname(firstname);
        listPageVo.setState(state);

        modelMap.addAttribute("listPageVo", listPageVo);

        modelMap.addAttribute("userActive", true);
        return "admin/user/user-list";
    }


    @PostMapping(value = "users/{id}")
    public String disableUser(UserListPageVo params, @PathVariable String id, RedirectAttributes attributes){

        userService.disableUser(id);

        attributes.addAttribute("beginPage", params.getBeginPage());
        attributes.addAttribute("currentPage", params.getCurrentPage());

        attributes.addAttribute("email", params.getEmail());
        attributes.addAttribute("firstname", params.getFirstname());
        attributes.addAttribute("state", params.getState());


        return "redirect:/admin/user-list";
    }

    @PostMapping(value = "users/delete")
    public String deleteUser(UserListPageVo params, @RequestParam String id, RedirectAttributes attributes){

        userService.deleteUser(id);

        attributes.addAttribute("beginPage", params.getBeginPage());
        attributes.addAttribute("currentPage", params.getCurrentPage());

        attributes.addAttribute("email", params.getEmail());
        attributes.addAttribute("firstname", params.getFirstname());
        attributes.addAttribute("state", params.getState());

        return "redirect:/admin/user-list";
    }

    @GetMapping(value = "users/edit")
    public String editUser(@RequestParam String id, ModelMap modelMap){
        User user = userService.findByKey(id);

        modelMap.addAttribute("user", user);

        return "admin/user/user-edit";
    }

    @PostMapping(value = "users/edit")
    public String updateUserInfo(@Valid UserForm userForm, ModelMap modelMap){
        try{
            userService.update(userForm.toDomain());
        }catch (AppException ex){
            modelMap.addAttribute("error", ex.getMessage());
            modelMap.addAttribute("user", userForm);

            return "admin/user/user-edit";
        }

        return "redirect:/admin/user-list";
    }
}
