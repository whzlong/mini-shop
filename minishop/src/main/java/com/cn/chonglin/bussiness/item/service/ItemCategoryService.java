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
        return itemCategoryDao.findItemCategories(brandId);
    }

    public ItemCategory findByKey(String categoryId){
        return itemCategoryDao.findByKey(categoryId);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public ItemCategory save(String brandName){
        String categoryId = IdGenerator.getUuid();
        itemCategoryDao.insert(categoryId, "0", brandName);

        return itemCategoryDao.findByKey(categoryId);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void update(String categoryId, String name){
        itemCategoryDao.update(categoryId, name);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteBrand(String brandId){
        //删除属于Brand的Model数据
        itemCategoryDao.deleteByParentId(brandId);
        //删除Brand数据
        itemCategoryDao.delete(brandId);
    }
}
