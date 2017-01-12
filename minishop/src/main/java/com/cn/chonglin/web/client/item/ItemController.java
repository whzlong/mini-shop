package com.cn.chonglin.web.client.item;

import com.cn.chonglin.bussiness.item.domain.Item;
import com.cn.chonglin.bussiness.item.domain.ItemType;
import com.cn.chonglin.bussiness.item.service.ItemService;
import com.cn.chonglin.common.ResponseResult;
import com.cn.chonglin.web.client.item.form.AppointmentForm;
import com.cn.chonglin.web.client.item.vo.ItemTypeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("client")
public class ItemController{
    @Autowired
    private ItemService itemService;

    @GetMapping(value = "items")
    public String itemList(@RequestParam(required = false) String brandId, @RequestParam(required = false) String modelId, ModelMap modelMap){
        List<ItemType> brands = itemService.findItemTypes("0");
        modelMap.addAttribute("brands", getItemTypeVo(brands));


        if(brands != null && brands.size() > 0){
            List<ItemType> models = itemService.findItemTypes(brandId==null ? brands.get(0).getTypeId() : brandId);
            modelMap.addAttribute("models", getItemTypeVo(models));

            modelMap.addAttribute("selectedBrand", brandId == null ? brands.get(0).getTypeId() : brandId);


            if(models != null && models.size() > 0){
                List<Item> items = itemService.findItemsByModel(models.get(0).getTypeId());
                modelMap.addAttribute("repairs", items);

                modelMap.addAttribute("selectedModel", modelId == null ? models.get(0).getTypeId() : modelId);
            }

        }

        return "client/item-list";
    }

//    @GetMapping(value = "/itemType/items")
//    public String getItemsByBrand(){
//
//    }

    /**
     * 获取总的分类列表
     *
     * @return
     */
    @GetMapping(value = "itemTypes", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody ResponseResult<List<ItemTypeVo>> getRootItemTypes(){
        List<ItemType> brands = itemService.findItemTypes("0");

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
        List<ItemType> brands = itemService.findItemTypes(id);

        return ResponseResult.success(getItemTypeVo(brands));
    }

    private List<ItemTypeVo> getItemTypeVo(List<ItemType> brands){
        List<ItemTypeVo> voList = new ArrayList<>();

        if(brands != null){
            ItemTypeVo itemTypeVo;

            for(ItemType itemType : brands){
                itemTypeVo = new ItemTypeVo();

                itemTypeVo.setItemTypeId(itemType.getTypeId());
                itemTypeVo.setName(itemType.getName());

                voList.add(itemTypeVo);
            }
        }

        return voList;
    }

    @RequestMapping(value = "/items/{id}", method = RequestMethod.GET)
    public String index(ModelMap model, @PathVariable long id){
        Item item = itemService.findByKey(id);

        model.addAttribute("item", item);

        return "client/item-detail";
    }

    /**
     * 预约
     *
     * @param appointmentForm
     * @param bindingResult
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "book", method = RequestMethod.POST)
    public String book(@Valid AppointmentForm appointmentForm, BindingResult bindingResult, ModelMap modelMap){
        Item item = itemService.findByKey(Long.valueOf(appointmentForm.getItemId()));

        modelMap.addAttribute("item", item);

        if(bindingResult.hasErrors()){
            modelMap.addAttribute("errors", getErrorsMsg(bindingResult.getFieldErrors()));
        }

        modelMap.addAttribute("infoMsg", "You have booked an appointment successfully!");

        return "client/item-detail";
    }

    private String getErrorsMsg(List<FieldError> fieldErrors){
        StringBuffer builder = new StringBuffer();

        fieldErrors.forEach(e -> {
            builder.append(String.format("%s<br>", e.getDefaultMessage()));
        });

        return builder.toString();
    }
}
