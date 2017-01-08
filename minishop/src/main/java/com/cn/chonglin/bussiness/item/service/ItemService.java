package com.cn.chonglin.bussiness.item.service;

import com.cn.chonglin.bussiness.item.dao.ItemDao;
import com.cn.chonglin.bussiness.item.domain.Item;
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

    public Item findByKey(long id){
        return dao.findByKey(id);
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
}
