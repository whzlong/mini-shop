package com.cn.chonglin.bussiness.item.service;

import com.cn.chonglin.bussiness.item.dao.ItemCategoryDao;
import com.cn.chonglin.bussiness.item.dao.ItemDao;
import com.cn.chonglin.bussiness.item.domain.Item;
import com.cn.chonglin.bussiness.item.domain.ItemCategory;
import com.cn.chonglin.bussiness.item.vo.ItemVo;
import com.cn.chonglin.bussiness.payment.PaymentService;
import com.cn.chonglin.common.IdGenerator;
import com.cn.chonglin.common.ListPage;
import com.cn.chonglin.web.item.form.ItemForm;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * 商品服务
 *
 * @author wu
 */
@Service
@Transactional
public class ItemService {
    private static final Logger logger = LoggerFactory.getLogger(ItemService.class);

    @Value("${uploadfilepath.detailimage}")
    private String detailImgFilePath;

    @Value("${uploadfilepath.listimage}")
    private String listImgFilePath;

    @Autowired
    private ItemDao itemDao;

    @Autowired
    private ItemCategoryDao itemCategoryDao;

    @Autowired
    private PaymentService paymentService;

    public Item findByKey(String id){
        paymentService.saveTransaction();
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
        return itemCategoryDao.findByParentCategoryId(parentTypeId);
    }

    public ListPage<ItemVo> query(String brand, String model, int limit, int page){
        int count = itemDao.countItems(brand, model);

        List<ItemVo> itemVos = itemDao.query(brand, model, limit, page*limit);

        return new ListPage<>(count, itemVos);
    }

    public void save(ItemForm itemForm){

        String itemDetailImagePath = uploadFile(itemForm.getItemDetailImage(), detailImgFilePath);

        String itemListImagePath = uploadFile(itemForm.getItemListImage(), listImgFilePath);

        Item item = itemForm.toDomain();
        item.setItemDetailImage(itemDetailImagePath);
        item.setItemListImage(itemListImagePath);

        if(!StringUtils.isEmpty(itemForm.getItemId())){
            update(item);
        }else{
            add(item);
        }
    }

    public void add(Item item){
        item.setItemId(IdGenerator.getUuid());

        item.setBrandName(itemCategoryDao.getItemCategoryName(item.getBrandId()));
        item.setModelName(itemCategoryDao.getItemCategoryName(item.getModelId()));

        itemDao.insert(item);
    }

    public void update(Item item){
        Item updateItem = itemDao.findByKey(item.getItemId());

        updateItem.setBrandId(item.getBrandId());
        updateItem.setBrandName(itemCategoryDao.getItemCategoryName(item.getBrandId()));
        updateItem.setModelId(item.getModelId());
        updateItem.setModelName(itemCategoryDao.getItemCategoryName(item.getModelId()));
        updateItem.setItemName(item.getItemName());

        if(!StringUtils.isEmpty(item.getItemDetailImage())){
            updateItem.setItemDetailImage(item.getItemDetailImage());
        }

        if(!StringUtils.isEmpty(item.getItemListImage())){
            updateItem.setItemListImage(item.getItemListImage());
        }

        updateItem.setUnitPrice(item.getUnitPrice());
        updateItem.setDiscountPrice(item.getDiscountPrice());
        updateItem.setState(item.getState());
        updateItem.setStock(item.getStock());

        itemDao.update(updateItem);
    }

    public String uploadFile(MultipartFile multipartFile, String fileUploadPath){
        String fileSavePath = "";

        if(!multipartFile.isEmpty()){

            String appAbsolutePath = org.springframework.util.ClassUtils.getDefaultClassLoader().getResource("static").getPath();

            String fileName = multipartFile.getOriginalFilename();

            fileSavePath = fileUploadPath + fileName;

            File file = new File(appAbsolutePath + fileSavePath);

            if(!file.getParentFile().exists()){
                file.getParentFile().mkdirs();
            }

            try{
                multipartFile.transferTo(file);
            }catch (IOException ex){
                logger.error("fileupload is failed. ({})", ex.getMessage());
            }
        }

        return fileSavePath;
    }

    public void delete(String itemId){

    }

}
