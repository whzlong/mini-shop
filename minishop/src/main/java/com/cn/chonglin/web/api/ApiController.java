package com.cn.chonglin.web.api;

import com.cn.chonglin.bussiness.item.domain.ItemCategory;
import com.cn.chonglin.bussiness.item.service.ItemCategoryService;
import com.cn.chonglin.common.AppException;
import com.cn.chonglin.common.ResponseResult;
import com.cn.chonglin.web.api.vo.SelectOptionVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api")
public class ApiController {

    @Autowired
    private ItemCategoryService itemCategoryService;

    @GetMapping("brands")
    public ResponseResult<List<SelectOptionVo>> getBrands(@RequestParam(defaultValue = "0") String blank){
        List<ItemCategory> itemCategories = itemCategoryService.findBrands();

        return ResponseResult.success(createKeyValue(itemCategories, blank));
    }

    @GetMapping("models")
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

        for (ItemCategory itemCategory : itemCategories){
            selectOptionVo = new SelectOptionVo();
            selectOptionVo.setValue(itemCategory.getCategoryId());
            selectOptionVo.setText(itemCategory.getName());

            brands.add(selectOptionVo);
        }

        return brands;
    }

    @PostMapping("brands/add")
    public ResponseResult<SelectOptionVo> addBrand(@RequestParam String brandName){

        try{
            ItemCategory itemCategory = itemCategoryService.save(brandName);

            SelectOptionVo selectOptionVo = new SelectOptionVo();
            selectOptionVo.setValue(itemCategory.getCategoryId());
            selectOptionVo.setText(itemCategory.getName());

            return ResponseResult.success(selectOptionVo);

        }catch (AppException ex){
            return ResponseResult.error(ex.getCode(), ex.getMessage());
        }

    }

    @PostMapping("brands/update")
    public ResponseResult<Object> updateBrand(@RequestParam String brandId, @RequestParam String brandName){

        try{
            itemCategoryService.update(brandId, brandName);

            return ResponseResult.success(null);

        }catch (AppException ex){
            return ResponseResult.error(ex.getCode(), ex.getMessage());
        }

    }

    @GetMapping("brands/delete")
    public ResponseResult<Object> deleteBrand(@RequestParam String brandId){
        try{
            itemCategoryService.deleteBrand(brandId);

            return ResponseResult.success(null);

        }catch (AppException ex){
            return ResponseResult.error(ex.getCode(), ex.getMessage());
        }
    }
}
