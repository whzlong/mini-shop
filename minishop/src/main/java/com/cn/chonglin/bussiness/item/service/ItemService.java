package com.cn.chonglin.bussiness.item.service;

import com.cn.chonglin.bussiness.item.dao.ItemDao;
import com.cn.chonglin.bussiness.item.dao.ItemCategoryDao;
import com.cn.chonglin.bussiness.item.domain.Item;
import com.cn.chonglin.bussiness.item.domain.ItemCategory;
import com.cn.chonglin.common.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 商品服务
 *
 * @author wu
 */
@Service
@Transactional
public class ItemService {
    @Autowired
    ItemDao itemDao;

    @Autowired
    ItemCategoryDao itemCategoryDao;

    public Item findByKey(String id){
        return itemDao.findByKey(id);
    }

    /**
     * 根据设备型号获取具体维修服务
     *
     * @param modelId
     * @return
     */
    public List<Item> findItemsByModel(String modelId){
        return itemDao.findItemsByModel(modelId);
    }

    /**
     * 获取打折维修服务
     *
     * @return
     */
    public List<Item> findDiscountItems(){
        return itemDao.findDiscountItems();
    }

    /**
     * 获取新的维修服务
     *
     * @return
     */
    public List<Item> findNewItems(){
        return itemDao.findNewItems();
    }

    /**
     * 查找商品类别
     *
     * @param parentTypeId
     *          所属商品类别ID
     * @return
     */
    public List<ItemCategory> findItemTypes(String parentTypeId){
        return itemCategoryDao.findItemCategories(parentTypeId);
    }

    public int getListCount(String brand, String model){
        return itemDao.getListCount(brand, model);
    }

    public List<Item> queryForList(String brand, String model, int limit, int offset){
        return itemDao.queryForList(brand, model, limit, offset);
    }

    public void add(Item item){
        item.setItemId(IdGenerator.getUuid());

        item.setBrandName(itemCategoryDao.getItemCategoryName(item.getBrandId()));
        item.setModelName(itemCategoryDao.getItemCategoryName(item.getModelId()));

        itemDao.insert(item);
    }

    public void update(Item item){
        item.setBrandName(itemCategoryDao.getItemCategoryName(item.getBrandId()));
        item.setModelName(itemCategoryDao.getItemCategoryName(item.getModelId()));
        itemDao.update(item);
    }

}
