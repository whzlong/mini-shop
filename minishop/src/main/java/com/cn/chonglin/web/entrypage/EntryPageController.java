package com.cn.chonglin.web.entrypage;

import com.cn.chonglin.StartupRunner;
import com.cn.chonglin.bussiness.base.service.SettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 各个页面入口
 */
@Controller
public class EntryPageController {

    @Autowired
    private SettingService settingService;

    /**
     * 主页
     *
     * @return
     */
    @GetMapping("/")
    public String home(){
        return "client/home";
    }

    /**
     * 管理后台－预约列表
     *
     * @param modelMap
     * @return
     */
    @GetMapping(value = "admin/appointment-list/index")
    public String index(ModelMap modelMap){
        modelMap.addAttribute("appointmentActive", true);

        return "admin/appointment/appointment-list";
    }

    /**
     * 管理后台－订单
     *
     * @param modelMap
     * @return
     */
    @GetMapping(value = "admin/order-list")
    public String orderListPage(ModelMap modelMap){
        modelMap.addAttribute("orderActive", true);

        return "admin/order/order-list";
    }

    /**
     * 管理后台－商品
     *
     * @param modelMap
     * @return
     */
    @GetMapping(value = "admin/item-list")
    public String itemListPage(ModelMap modelMap){
        modelMap.addAttribute("itemActive", true);

        return "admin/item/item-list";
    }

    /**
     * 管理后台－商品分类
     *
     * @param modelMap
     * @return
     */
    @GetMapping(value = "admin/item-category")
    public String itemCategoryPage(ModelMap modelMap){
        modelMap.addAttribute("itemCategoryActive", true);

        return "admin/item/item-category";
    }

    /**
     * 管理后台－账户列表
     *
     * @param modelMap
     * @return
     */
    @GetMapping(value = "admin/user-list")
    public String userListpage(ModelMap modelMap){
        modelMap.addAttribute("userActive", true);

        return "admin/user/user-list";
    }

    /**
     * 管理后台－优惠券
     *
     * @param modelMap
     * @return
     */
    @GetMapping(value = "admin/coupon-list")
    public String couponListPage(ModelMap modelMap){
        modelMap.addAttribute("couponActive", true);

        return "admin/coupon/coupon-list";
    }

    /**
     * 客户端－购物车
     *
     * @param modelMap
     * @return
     */
    @GetMapping(value = "client/cart")
    public String cartEntryPage(ModelMap modelMap){

        modelMap.addAttribute("currency", settingService.findSetting().getCurrency());

        return "client/cart";
    }

    /**
     * 客户端－结账
     *
     * @param modelMap
     * @return
     */
    @GetMapping(value="client/check-out")
    public String checkoutEntryPage(ModelMap modelMap){
        //支付时携带此token向Braintree服务器发送请求
        modelMap.addAttribute("clientToken", StartupRunner.gateway.clientToken().generate());

        return "client/checkout";
    }

    /**
     * 客户端－选择服务
     *
     * @param modelMap
     * @return
     */
    @GetMapping(value = "client/select-service")
    public String selectServiceEntryPage(ModelMap modelMap){
        return "client/select-service";
    }

    @GetMapping(value = "client/item-detail/{itemId}")
    public String itemDetailEntryPage(@PathVariable String itemId, ModelMap modelMap){
        modelMap.addAttribute("itemId", itemId);
        modelMap.addAttribute("currency", settingService.findSetting().getCurrency());

        return "client/item-detail";
    }

    /**
     * 客户端－客户预约列表
     *
     * @return
     */
    @GetMapping(value = "client/appointment-list")
    public String showClientAppointments(){
        return "client/appointment-customer";
    }
}
