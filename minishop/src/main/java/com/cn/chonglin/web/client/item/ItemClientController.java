package com.cn.chonglin.web.client.item;

import com.cn.chonglin.bussiness.base.dao.SettingDao;
import com.cn.chonglin.bussiness.item.domain.Item;
import com.cn.chonglin.bussiness.item.domain.ItemCategory;
import com.cn.chonglin.bussiness.item.service.ItemService;
import com.cn.chonglin.common.ResponseResult;
import com.cn.chonglin.web.client.item.vo.ItemTypeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("client")
public class ItemClientController {
    @Autowired
    private ItemService itemService;

    @Autowired
    private SettingDao settingDao;

    @GetMapping(value = "items")
    public String itemList(@RequestParam(required = false) String brandId, @RequestParam(required = false) String modelId, ModelMap modelMap){
        List<ItemCategory> brands = itemService.findItemTypes("0");
        modelMap.addAttribute("brands", getItemTypeVo(brands));


        if(brands != null && brands.size() > 0){
            List<ItemCategory> models = itemService.findItemTypes(brandId==null ? brands.get(0).getCategoryId() : brandId);
            modelMap.addAttribute("models", getItemTypeVo(models));

            modelMap.addAttribute("selectedBrand", brandId == null ? brands.get(0).getCategoryId() : brandId);


            if(models != null && models.size() > 0){
                List<Item> items = itemService.findItemsByModel(models.get(0).getCategoryId());
                modelMap.addAttribute("repairs", items);

                modelMap.addAttribute("selectedModel", modelId == null ? models.get(0).getCategoryId() : modelId);
            }

        }

        return "client/item-list";
    }


    /**
     * 获取总的分类列表
     *
     * @return
     */
    @GetMapping(value = "itemTypes", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody ResponseResult<List<ItemTypeVo>> getRootItemTypes(){
        List<ItemCategory> brands = itemService.findItemTypes("0");

        return ResponseResult.success(getItemTypeVo(brands));
    }

    /**
     * 根据父分类列表获取子列表
     *
     * @param id
     *        父分类ID
     * @return
     */
    @GetMapping(value = "itemTypes/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody ResponseResult<List<ItemTypeVo>> getItemTypes(@PathVariable String id){
        List<ItemCategory> brands = itemService.findItemTypes(id);

        return ResponseResult.success(getItemTypeVo(brands));
    }

    private List<ItemTypeVo> getItemTypeVo(List<ItemCategory> brands){
        List<ItemTypeVo> voList = new ArrayList<>();

        if(brands != null){
            ItemTypeVo itemTypeVo;

            for(ItemCategory itemCategory : brands){
                itemTypeVo = new ItemTypeVo();

                itemTypeVo.setItemTypeId(itemCategory.getCategoryId());
                itemTypeVo.setName(itemCategory.getName());

                voList.add(itemTypeVo);
            }
        }

        return voList;
    }

    @RequestMapping(value = "/items/{id}", method = RequestMethod.GET)
    public String index(ModelMap model, @PathVariable String id){
        Item item = itemService.findByKey(id);



        model.addAttribute("item", item);
        model.addAttribute("setting",settingDao.queryForObject());

        return "client/item-detail";
    }




}
