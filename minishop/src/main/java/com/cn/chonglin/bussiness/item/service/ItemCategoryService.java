package com.cn.chonglin.bussiness.item.service;

import com.cn.chonglin.bussiness.item.dao.ItemCategoryDao;
import com.cn.chonglin.bussiness.item.domain.ItemCategory;
import com.cn.chonglin.common.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 商品分类
 */
@Service
@Transactional(readOnly = true)
public class ItemCategoryService {
    @Autowired
    private ItemCategoryDao itemCategoryDao;

    public List<ItemCategory> findBrands(){
        return itemCategoryDao.findBrands();
    }

    public List<ItemCategory> findModels(String brandId){
        return itemCategoryDao.findByParentCategoryId(brandId);
    }

    public ItemCategory findByKey(String categoryId){
        return itemCategoryDao.findByKey(categoryId);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public ItemCategory save(String parentCategoryId, String categoryName){
        String categoryId = IdGenerator.getUuid();
        itemCategoryDao.insert(categoryId, parentCategoryId, categoryName);

        return itemCategoryDao.findByKey(categoryId);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void update(String categoryId, String name){
        itemCategoryDao.update(categoryId, name);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void delete(String categoryId){

        List<ItemCategory> itemCategories = itemCategoryDao.findByParentCategoryId(categoryId);

        if(itemCategories != null){
            itemCategoryDao.deleteByParentCategoryId(categoryId);
        }

        itemCategoryDao.delete(categoryId);
    }
}
