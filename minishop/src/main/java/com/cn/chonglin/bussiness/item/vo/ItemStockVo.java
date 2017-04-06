package com.cn.chonglin.bussiness.item.vo;

/**
 * 商品库存
 */
public class ItemStockVo {
    /**
     * 商品ID
     */
    private String itemId;

    /**
     * 库存状态(0:没有库存； 1: 有库存; 2: 库存不足)
     */
    private String stockStatus;

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getStockStatus() {
        return stockStatus;
    }

    public void setStockStatus(String stockStatus) {
        this.stockStatus = stockStatus;
    }
}
