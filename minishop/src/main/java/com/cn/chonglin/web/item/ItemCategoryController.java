package com.cn.chonglin.web.item;

import com.cn.chonglin.bussiness.base.vo.SelectOptionVo;
import com.cn.chonglin.bussiness.item.domain.ItemCategory;
import com.cn.chonglin.bussiness.item.service.ItemCategoryService;
import com.cn.chonglin.common.AppException;
import com.cn.chonglin.common.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * 商品分类
 */
@RestController
public class ItemCategoryController {
    @Autowired
    private ItemCategoryService itemCategoryService;

    @GetMapping(value = "api/brands", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseResult<List<SelectOptionVo>> getBrands(@RequestParam(defaultValue = "0") String blank){
        List<ItemCategory> itemCategories = itemCategoryService.findBrands();

        return ResponseResult.success(createKeyValue(itemCategories, blank));
    }

    @GetMapping(value = "api/models", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseResult<List<SelectOptionVo>> getModels(@RequestParam String brandId, @RequestParam(defaultValue = "0") String blank){
        List<ItemCategory> itemCategories = itemCategoryService.findModels(brandId);

        return ResponseResult.success(createKeyValue(itemCategories, blank));
    }

    private List<SelectOptionVo> createKeyValue(List<ItemCategory> itemCategories, String blank){
        List<SelectOptionVo> brands = new ArrayList<>();

        SelectOptionVo selectOptionVo = new SelectOptionVo();

        if("0".equals(blank)){
            selectOptionVo.setValue("");
            selectOptionVo.setText("");
            brands.add(selectOptionVo);
        }

        if(itemCategories != null){
            for (ItemCategory itemCategory : itemCategories){
                selectOptionVo = new SelectOptionVo();
                selectOptionVo.setValue(itemCategory.getCategoryId());
                selectOptionVo.setText(itemCategory.getName());

                brands.add(selectOptionVo);
            }
        }

        return brands;
    }

    @PostMapping("admin/item-categories/add")
    public ResponseResult<SelectOptionVo> addItemCategory(@RequestParam(defaultValue = "0") String parentCategoryId, @RequestParam String categoryName){

        try{
            ItemCategory itemCategory = itemCategoryService.save(parentCategoryId, categoryName);

            SelectOptionVo selectOptionVo = new SelectOptionVo();
            selectOptionVo.setValue(itemCategory.getCategoryId());
            selectOptionVo.setText(itemCategory.getName());

            return ResponseResult.success(selectOptionVo);

        }catch (AppException ex){
            return ResponseResult.error(ex.getCode(), ex.getMessage());
        }

    }

    @PostMapping("admin/item-categories/update")
    public ResponseResult<Object> updateBrand(@RequestParam String categoryId, @RequestParam String categoryName){

        try{
            itemCategoryService.update(categoryId, categoryName);

            return ResponseResult.success(null);

        }catch (AppException ex){
            return ResponseResult.error(ex.getCode(), ex.getMessage());
        }

    }

    @PostMapping("admin/item-categories/delete")
    public ResponseResult<Object> delete(@RequestParam String categoryId){
        try{
            itemCategoryService.delete(categoryId);

            return ResponseResult.success(null);

        }catch (AppException ex){
            return ResponseResult.error(ex.getCode(), ex.getMessage());
        }
    }

}
