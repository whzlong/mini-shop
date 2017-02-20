package com.cn.chonglin.web.admin.item;

import com.cn.chonglin.bussiness.item.domain.Item;
import com.cn.chonglin.bussiness.item.service.ItemService;
import com.cn.chonglin.common.AppException;
import com.cn.chonglin.web.admin.item.form.ItemForm;
import com.cn.chonglin.web.admin.item.vo.ItemListPageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@Controller
@RequestMapping("admin")
public class AdminItemController {
    /**
     * 列表显示数量
     */
    private static final int listSize = 5;

    /**
     * 显示分页数量
     */
    private static final int pageSize = 5;

    @Autowired
    private ItemService itemService;

    @RequestMapping(value = "item-list", method = {RequestMethod.GET, RequestMethod.POST})
    public String list(@RequestParam(required = false, defaultValue = "") String brand
            , @RequestParam(required = false, defaultValue = "") String model
            , @RequestParam(required = false, defaultValue = "1") @Min(1) int beginPage
            , @RequestParam(required = false, defaultValue = "1") @Min(1) int currentPage
            , ModelMap modelMap
    ){
        int totalPage = 0;
        int recordCount = itemService.getListCount(brand, model);

        List<Item> items = itemService.queryForList(brand, model, listSize, listSize*(currentPage - 1));

        modelMap.addAttribute("items", items);

        if(recordCount % listSize == 0){
            totalPage = recordCount/listSize;
        }else{
            totalPage = recordCount/listSize + 1;
        }

        int endPage;
        if(totalPage - beginPage >= pageSize){
            endPage = beginPage + pageSize - 1;
        }else {
            endPage = totalPage;
        }

        ItemListPageVo listPageVo = new ItemListPageVo();

        listPageVo.setTotalPage(totalPage);
        listPageVo.setCurrentPage(currentPage);
        listPageVo.setBeginPage(beginPage);
        listPageVo.setEndPage(endPage);
        listPageVo.setLeftPage(totalPage - beginPage);
        listPageVo.setListSize(listSize);
        listPageVo.setPageSize(pageSize);

        listPageVo.setBrand(brand);
        listPageVo.setModel(model);

        modelMap.addAttribute("listPageVo", listPageVo);
        modelMap.addAttribute("itemActive", true);

        return "admin/item/item-list";
    }

    @GetMapping(value = "items/edit")
    public String edit(@RequestParam String itemId, ModelMap modelMap){
        Item item = itemService.findByKey(itemId);

        modelMap.addAttribute("item", item);

        return "admin/item/item-edit";
    }

    @PostMapping(value = "items/edit")
    public String update(@Valid ItemForm itemForm, ModelMap modelMap){
        try{
            itemService.update(itemForm.toDomain());
        }catch (AppException ex){
            modelMap.addAttribute("error", ex.getMessage());
            modelMap.addAttribute("item", itemForm);

            return "admin/item/item-edit";
        }

        return "redirect:/admin/item-list";
    }

    @GetMapping(value = "items/add")
    public String add(){
        return "admin/item/item-add";
    }

    @PostMapping(value = "items/add")
    public String insert(@Valid ItemForm itemForm, ModelMap modelMap){
        try{
            itemService.add(itemForm.toDomain());
        }catch (AppException ex){
            modelMap.addAttribute("error", ex.getMessage());
            modelMap.addAttribute("item", itemForm);

            return "admin/item/item-add";
        }

        return "admin/item/item-add";
    }

    @GetMapping(value = "item-category")
    public String itemCategoryIndex(ModelMap modelMap){


        modelMap.addAttribute("itemCategoryActive", true);
        return "admin/item/item-category";
    }
}
