package com.cn.chonglin.web.item.form;

import com.cn.chonglin.bussiness.item.domain.Item;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import java.math.BigDecimal;

public class ItemForm {
    private String itemId;

    @NotEmpty(message = "Please input the item name.")
    @Length(max = 100)
    private String itemName;

    @DecimalMin(value = "0", message = "Please input valid unit price.")
    @DecimalMax(value = "999999999", message = "Please input valid unit price.")
    private BigDecimal unitPrice;

    @DecimalMin(value = "0", message = "Please input valid discount price.")
    @DecimalMax(value = "999999999", message = "Please input valid discount price.")
    private BigDecimal discountPrice;

    private MultipartFile itemListImage;
    private MultipartFile itemDetailImage;

    @Range(min = 0, max = 99999, message = "Please input the number in 0 to 99999")
    private int stock;

    @NotEmpty(message = "Please input the brand.")
    private String brand;

    @NotEmpty(message = "Please input the model.")
    private String model;

    @NotEmpty(message = "Please input the state.")
    private String state;

    private String description;

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

    public MultipartFile getItemListImage() {
        return itemListImage;
    }

    public void setItemListImage(MultipartFile itemListImage) {
        this.itemListImage = itemListImage;
    }

    public MultipartFile getItemDetailImage() {
        return itemDetailImage;
    }

    public void setItemDetailImage(MultipartFile itemDetailImage) {
        this.itemDetailImage = itemDetailImage;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Item toDomain(){
        Item item = new Item();

        item.setItemId(this.itemId);
        item.setItemName(this.itemName);
        item.setUnitPrice(this.unitPrice);
        item.setDiscountPrice(this.discountPrice);
        item.setItemDetailImage(this.itemDetailImage.getName());
        item.setItemListImage(this.itemListImage.getName());
        item.setBrandId(this.brand);
        item.setModelId(this.model);
        item.setStock(this.stock);
        item.setState(this.state);
        item.setDescription(this.description);

        return item;
    }
}
