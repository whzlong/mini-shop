package com.cn.chonglin.web.order;

import com.cn.chonglin.bussiness.order.service.OrderService;
import com.cn.chonglin.bussiness.order.vo.OrderDetailVo;
import com.cn.chonglin.bussiness.order.vo.OrderVo;
import com.cn.chonglin.common.PaginationResult;
import com.cn.chonglin.common.ResponseResult;
import com.cn.chonglin.web.order.form.OrderForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

/**
 * 订单控制器
 *
 */
@RestController
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping(value = "admin/order-list", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public PaginationResult<OrderVo> query(@RequestParam(required = false) String orderId,
                                            @RequestParam(required = false) String orderDateFrom,
                                            @RequestParam(required = false) String orderDateTo,
                                           @RequestParam(required = false, defaultValue = "Appointment") String state,
                                           @RequestParam(required = false, defaultValue = "0") @Min(0) int currentPage,
                                           @RequestParam(required = false, defaultValue = "15") @Min(0) @Max(200) int size){

        currentPage -= 1;

        return PaginationResult.success(orderService.query(orderId, orderDateFrom, orderDateTo, state, size, currentPage));
    }

    @PostMapping(value = "admin/orders/update", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseResult<Object> update(@Valid OrderForm orderForm, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return ResponseResult.error(bindingResult.getFieldErrors());
        }

        orderService.update(orderForm.toDomain());

        return ResponseResult.success(null);
    }

    @PostMapping(value = "admin/orders/emailToCustomer", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseResult<Object> emailToCustomer(@Valid OrderForm orderForm, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return ResponseResult.error(bindingResult.getFieldErrors());
        }

        orderService.emailToCustomer(orderForm.toDomain());

        return ResponseResult.success(null);
    }

    @PostMapping(value = "admin/orders/delete", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseResult<Object> delete(@RequestParam String orderId){

        orderService.delete(Long.valueOf(orderId));

        return ResponseResult.success(null);
    }

    @GetMapping(value = "admin/orderDetails", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseResult<List<OrderDetailVo>> queryOrderDetails(@RequestParam String orderId){
        return ResponseResult.success(orderService.queryOrderDetails(orderId));
    }

}
