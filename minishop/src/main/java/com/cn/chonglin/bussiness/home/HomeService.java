package com.cn.chonglin.bussiness.home;

import com.cn.chonglin.bussiness.home.vo.SpecialItemVo;
import com.cn.chonglin.bussiness.item.domain.Item;
import com.cn.chonglin.bussiness.item.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 主页Service
 *
 */
@Service
public class HomeService {
    @Autowired
    private ItemService itemService;

    /**
     * 主页显示的新品和折扣品
     *
     * @return
     */
    public SpecialItemVo getSpecilItems(){
        //打折维修服务
        List<Item> discountItems = itemService.findDiscountItems();

        //新上线维修服务
        List<Item> newItems = itemService.findNewItems();

        SpecialItemVo specialItemVo = new SpecialItemVo();

        specialItemVo.setNewItems(newItems);
        specialItemVo.setDiscountItems(discountItems);

        return specialItemVo;
    }
}
