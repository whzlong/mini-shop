package com.cn.chonglin.bussiness.item.domain;

import java.sql.Timestamp;

/**
 * 商品类别
 *
 * @author wu
 */
public class ItemType {
    /**
     * 商品类别ID
     */
    private String typeId;

    /**
     * 商品父类别ID
     */
    private String parentTypeId;

    /**
     * 名称
     */
    private String name;

    /**
     * 创建日时
     */
    private Timestamp createdAt;

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getParentTypeId() {
        return parentTypeId;
    }

    public void setParentTypeId(String parentTypeId) {
        this.parentTypeId = parentTypeId;
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
