package com.cn.chonglin.web.item;

import com.cn.chonglin.bussiness.item.domain.Item;
import com.cn.chonglin.bussiness.item.service.ItemService;
import com.cn.chonglin.bussiness.item.vo.ItemStockVo;
import com.cn.chonglin.bussiness.item.vo.ItemVo;
import com.cn.chonglin.common.PaginationResult;
import com.cn.chonglin.common.ResponseResult;
import com.cn.chonglin.web.item.form.ItemForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

/**
 * 商品
 *
 */
@RestController
public class ItemController {

    @Autowired
    private ItemService itemService;

    @GetMapping(value = "admin/item-list", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public PaginationResult<ItemVo> query(@RequestParam(required = false, defaultValue = "") String brandId,
                                          @RequestParam(required = false, defaultValue = "") String modelId,
                                          @RequestParam(required = false, defaultValue = "0") @Min(0) int currentPage,
                                          @RequestParam(required = false, defaultValue = "15") @Min(0) @Max(200) int size){

        currentPage -= 1;

        return PaginationResult.success(itemService.query(brandId, modelId, size, currentPage));
    }

    @PostMapping(value = "admin/items", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseResult<Object> save(@Valid ItemForm itemForm, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return ResponseResult.error(bindingResult.getFieldErrors());
        }

        itemService.save(itemForm);

        return ResponseResult.success(null);
    }

    @GetMapping(value = "client/items", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseResult<List<Item>> queryItems(@RequestParam String modelId){

        return ResponseResult.success(itemService.findItemsByModel(modelId));
    }

    @GetMapping(value = "client/items/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseResult<Item> index(@PathVariable String id){

        return ResponseResult.success(itemService.findByKey(id));
    }

    @GetMapping(value = "item-stock/cart", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseResult<List<ItemStockVo>> checkCartItemsStock(@RequestParam String cartId){
        return ResponseResult.success(itemService.checkCartItemsStock(cartId));
    }
}
