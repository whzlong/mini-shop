package com.cn.chonglin.bussiness.home.vo;

import com.cn.chonglin.bussiness.item.domain.Item;

import java.util.List;

/**
 * 主页VO
 *
 */
public class SpecialItemVo {
    private List<Item> newItems;
    private List<Item> discountItems;

    public List<Item> getNewItems() {
        return newItems;
    }

    public void setNewItems(List<Item> newItems) {
        this.newItems = newItems;
    }

    public List<Item> getDiscountItems() {
        return discountItems;
    }

    public void setDiscountItems(List<Item> discountItems) {
        this.discountItems = discountItems;
    }
}
