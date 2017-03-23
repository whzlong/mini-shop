package com.cn.chonglin.bussiness.item.service;

import com.cn.chonglin.bussiness.cart.dao.CartItemDao;
import com.cn.chonglin.bussiness.cart.vo.CartItemVo;
import com.cn.chonglin.bussiness.item.dao.ItemCategoryDao;
import com.cn.chonglin.bussiness.item.dao.ItemDao;
import com.cn.chonglin.bussiness.item.domain.Item;
import com.cn.chonglin.bussiness.item.domain.ItemCategory;
import com.cn.chonglin.bussiness.item.vo.ItemStockVo;
import com.cn.chonglin.bussiness.item.vo.ItemVo;
import com.cn.chonglin.common.IdGenerator;
import com.cn.chonglin.common.ListPage;
import com.cn.chonglin.constants.DropdownListContants;
import com.cn.chonglin.web.item.form.ItemForm;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 商品服务
 *
 * @author wu
 */
@Service
@Transactional(readOnly = true)
public class ItemService {
    private static final Logger logger = LoggerFactory.getLogger(ItemService.class);

    @Value("${web.upload-path}")
    private String webUploadPath;

    private static final String IMAGES_FOLDER_ROOT = "item-images";
    /**
     * 商品详情图片所在文件夹
     */
    private static final String IMAGES_FOLDER_DETAIL = "detail_images";

    /**
     * 商品缩略图片所在文件夹
     */
    private static final String IMAGES_FOLDER_THUMBNAIL = "thumbnail_images";

    @Autowired
    private ItemDao itemDao;

    @Autowired
    private ItemCategoryDao itemCategoryDao;

    @Autowired
    private CartItemDao cartItemDao;

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
        return itemCategoryDao.findByParentCategoryId(parentTypeId);
    }

    /**
     * 分页商品列表
     *
     * @param brand
     *          品牌ID
     * @param model
     *          型号ID
     * @param limit
     *          每页显示数量
     * @param page
     *          页数
     * @return
     */
    public ListPage<ItemVo> query(String brand, String model, int limit, int page){
        int count = itemDao.countItems(brand, model);

        List<ItemVo> itemVos = itemDao.query(brand, model, limit, page*limit);

        return new ListPage<>(count, itemVos);
    }

    /**
     * 保存商品信息（增加或更新）
     *
     * @param itemForm
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void save(ItemForm itemForm){

        String itemDetailImagePath = uploadFile(itemForm.getItemDetailImage(), IMAGES_FOLDER_DETAIL);

        String itemListImagePath = uploadFile(itemForm.getItemListImage(), IMAGES_FOLDER_THUMBNAIL);

        Item item = itemForm.toDomain();
        item.setItemDetailImage(itemDetailImagePath);
        item.setItemListImage(itemListImagePath);

        if(!StringUtils.isEmpty(itemForm.getItemId())){
            update(item);
        }else{
            add(item);
        }
    }

    /**
     * 增加商品
     *
     * @param item
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void add(Item item){
        item.setItemId(IdGenerator.getUuid());

        item.setBrandName(itemCategoryDao.getItemCategoryName(item.getBrandId()));
        item.setModelName(itemCategoryDao.getItemCategoryName(item.getModelId()));

        if(!StringUtils.isEmpty(item.getItemDetailImage())){
            item.setItemDetailImage("/" + item.getItemDetailImage());
        }

        if(!StringUtils.isEmpty(item.getItemListImage())){
            item.setItemListImage("/" + item.getItemListImage());
        }

        itemDao.insert(item);
    }

    /**
     * 更新商品
     *
     * @param item
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void update(Item item){
        Item updateItem = itemDao.findByKey(item.getItemId());

        updateItem.setBrandId(item.getBrandId());
        updateItem.setBrandName(itemCategoryDao.getItemCategoryName(item.getBrandId()));
        updateItem.setModelId(item.getModelId());
        updateItem.setModelName(itemCategoryDao.getItemCategoryName(item.getModelId()));
        updateItem.setItemName(item.getItemName());

        if(!StringUtils.isEmpty(item.getItemDetailImage())){
            updateItem.setItemDetailImage("/" + item.getItemDetailImage());
        }

        if(!StringUtils.isEmpty(item.getItemListImage())){
            updateItem.setItemListImage("/" + item.getItemListImage());
        }

        updateItem.setUnitPrice(item.getUnitPrice());
        updateItem.setDiscountPrice(item.getDiscountPrice());
        updateItem.setState(item.getState());
        updateItem.setStock(item.getStock());

        itemDao.update(updateItem);
    }

    /**
     * 上传商品图片
     *
     * @param multipartFile
     * @param filePath
     * @return
     */
    private String uploadFile(MultipartFile multipartFile, String filePath){
        String relativeFilePath = "";

        if(!multipartFile.isEmpty()){

            String fileName = multipartFile.getOriginalFilename();

            relativeFilePath = IMAGES_FOLDER_ROOT + "/" + filePath + "/" + fileName;

            File file = new File(webUploadPath + relativeFilePath);

            if(!file.getParentFile().exists()){
                file.getParentFile().mkdirs();
            }

            try{
                multipartFile.transferTo(file);
            }catch (IOException ex){
                logger.error("fileupload is failed. ({})", ex.getMessage());
            }
        }

        return relativeFilePath;
    }

    /**
     * 检查购物车中商品的库存
     *
     * @param cartId
     *         购物车ID
     */
    public List<ItemStockVo> checkCartItemsStock(String cartId){
        List<CartItemVo> cartItemVos = cartItemDao.findCartItems(cartId);

        List<String> itemIds = cartItemVos.stream().map(e -> e.getItemId()).collect(Collectors.toList());

        List<Item> items = itemDao.findItemsByItemIds(itemIds);

        Map<String, Integer> itemStockMap = new HashMap<>();

        for(Item item : items){
            itemStockMap.put(item.getItemId(), item.getStock());
        }

        List<ItemStockVo> itemStockVos = new ArrayList<>();

        ItemStockVo itemStockVo;

        for(CartItemVo cartItemVo : cartItemVos){
            itemStockVo = new ItemStockVo();
            itemStockVo.setItemId(cartItemVo.getItemId());

            if(itemStockMap.get(cartItemVo.getItemId()) - cartItemVo.getQuantity() > 0){
                itemStockVo.setStockStatus(DropdownListContants.STOCK_STATUS_HAS_VALUE);
            }else{
                itemStockVo.setStockStatus(DropdownListContants.STOCK_STATUS_HASNOT_VALUE);
            }

            itemStockVos.add(itemStockVo);
        }

        return itemStockVos;
    }
}
