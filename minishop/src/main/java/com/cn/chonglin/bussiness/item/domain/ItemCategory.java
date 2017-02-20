package com.cn.chonglin.bussiness.item.domain;

import java.sql.Timestamp;

/**
 * 商品类别
 *
 * @author wu
 */
public class ItemCategory {
    /**
     * 商品类别ID
     */
    private String categoryId;

    /**
     * 商品父类别ID
     */
    private String parentCategoryId;

    /**
     * 名称
     */
    private String name;

    /**
     * 创建日时
     */
    private Timestamp createdAt;

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getParentCategoryId() {
        return parentCategoryId;
    }

    public void setParentCategoryId(String parentCategoryId) {
        this.parentCategoryId = parentCategoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}
