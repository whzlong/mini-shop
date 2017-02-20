package com.cn.chonglin.web.admin.item.form;

import com.cn.chonglin.bussiness.item.domain.Item;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.DecimalMin;
import java.math.BigDecimal;

public class ItemForm {
    private String itemId;

    @NotEmpty(message = "Please input the item name.")
    @Length(max = 100)
    private String itemName;

    @DecimalMin(value = "0", message = "Please input valid unit price.")
    private BigDecimal unitPrice;

    @DecimalMin(value = "0", message = "Please input valid discount price.")
    private BigDecimal discountPrice;

    private String smallImage;
    private String bigImage;

    @NotEmpty(message = "Please input the currency.")
    private String currency;

    @Range(min = 0, max = 99999, message = "Please input the number in 0 to 99999")
    private int stock;

    private String brandId;
    private String modelId;
    private String state;

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public BigDecimal getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(BigDecimal discountPrice) {
        this.discountPrice = discountPrice;
    }

    public String getSmallImage() {
        return smallImage;
    }

    public void setSmallImage(String smallImage) {
        this.smallImage = smallImage;
    }

    public String getBigImage() {
        return bigImage;
    }

    public void setBigImage(String bigImage) {
        this.bigImage = bigImage;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    public String getModelId() {
        return modelId;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Item toDomain(){
        Item item = new Item();

        item.setItemId(this.itemId);
        item.setItemName(this.itemName);
        item.setUnitPrice(this.unitPrice);
        item.setDiscountPrice(this.discountPrice);
        item.setBigImage(this.bigImage);
        item.setSmallImage(this.smallImage);
        item.setCurrency(this.currency);
        item.setBrandId(this.brandId);
        item.setModelId(this.modelId);
        item.setStock(this.stock);
        item.setState(this.state);

        return item;
    }
}
