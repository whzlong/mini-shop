package com.cn.chonglin.bussiness.item.service;

import com.cn.chonglin.bussiness.item.dao.ItemDao;
import com.cn.chonglin.bussiness.item.dao.ItemTypeDao;
import com.cn.chonglin.bussiness.item.domain.Item;
import com.cn.chonglin.bussiness.item.domain.ItemType;
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
    ItemDao dao;

    @Autowired
    ItemTypeDao itemTypeDao;

    public Item findByKey(long id){
        return dao.findByKey(id);
    }

    /**
     * 根据设备型号获取具体维修服务
     *
     * @param modelId
     * @return
     */
    public List<Item> findItemsByModel(String modelId){
        return dao.findItemsByModel(modelId);
    }

    /**
     * 获取打折维修服务
     *
     * @return
     */
    public List<Item> findDiscountItems(){
        return dao.findDiscountItems(true);
    }

    /**
     * 获取新的维修服务
     *
     * @return
     */
    public List<Item> findNewItems(){
        return dao.findNewItems(true);
    }

    /**
     * 查找商品类别
     *
     * @param parentTypeId
     *          所属商品类别ID
     * @return
     */
    public List<ItemType> findItemTypes(String parentTypeId){
        return itemTypeDao.findItemTypes(parentTypeId);
    }
}
