package com.cn.chonglin.web.client.home;

import com.cn.chonglin.bussiness.item.domain.Item;
import com.cn.chonglin.bussiness.item.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * 主页控制器
 *
 * @author wu
 */
@Controller
public class HomeController {
    @Autowired
    private ItemService itemService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(ModelMap modelMap){
        //打折维修服务
        List<Item> discountItems = itemService.findDiscountItems();

        //新上线维修服务
        List<Item> newItems = itemService.findNewItems();

        modelMap.addAttribute("discountItems", discountItems);
        modelMap.addAttribute("newItems", newItems);


        return "client/index";
    }
}
