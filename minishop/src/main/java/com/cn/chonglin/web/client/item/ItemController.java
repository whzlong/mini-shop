package com.cn.chonglin.web.client.item;

import com.cn.chonglin.bussiness.item.domain.Item;
import com.cn.chonglin.bussiness.item.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("client")
public class ItemController{
    @Autowired
    private ItemService itemService;

    @RequestMapping(value = "/items/{id}", method = RequestMethod.GET)
    public String index(ModelMap model, @PathVariable long id){
        Item item = itemService.findByKey(id);

        model.addAttribute("item", item);

        return "client/item-detail";
    }

    @RequestMapping(value = "book", method = RequestMethod.POST)
    public String book(@RequestParam String bookDate, @RequestParam String bookTime, @RequestParam String itemId){
        //String itemId = "3";

        return "redirect:items/" + itemId;
    }
}
