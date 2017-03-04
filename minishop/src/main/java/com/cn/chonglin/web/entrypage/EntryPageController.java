package com.cn.chonglin.web.entrypage;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 各个页面入口
 */
@Controller
public class EntryPageController {
    /**
     * 订单
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
     * 商品
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
     * 商品分类
     *
     * @param modelMap
     * @return
     */
    @GetMapping(value = "admin/item-category")
    public String itemCategoryPage(ModelMap modelMap){
        return "admin/item/item-category";
    }

    @GetMapping(value = "admin/user-list")
    public String userListpage(ModelMap modelMap){
        modelMap.addAttribute("userActive", true);

        return "admin/user/user-list";
    }

    /**
     * 优惠券
     * @param modelMap
     * @return
     */
    @GetMapping(value = "admin/coupon-list")
    public String couponListPage(ModelMap modelMap){
        modelMap.addAttribute("couponActive", true);

        return "admin/coupon/coupon-list";
    }
}
