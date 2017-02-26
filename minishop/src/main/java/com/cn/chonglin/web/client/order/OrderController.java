package com.cn.chonglin.web.client.order;

import com.cn.chonglin.bussiness.item.service.ItemService;
import com.cn.chonglin.bussiness.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 订单
 */
@Controller
@RequestMapping("client")
public class OrderController {

    @Autowired
    private ItemService itemService;

    @Autowired
    private OrderService orderService;

    /**
     * 预约
     *
     * @param appointmentForm
     * @param bindingResult
     * @return
     */
//    @RequestMapping(value = "book", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
//    public @ResponseBody
//    ResponseResult<Object> book(@RequestBody @Valid AppointmentForm appointmentForm, BindingResult bindingResult){
//        if(bindingResult.hasErrors()){
//            return ResponseResult.error(bindingResult.getFieldErrors());
//        }
//
//        orderService.save(appointmentForm.getItemId(), appointmentForm.getBookDate(), appointmentForm.getBookTime());
//
//        return ResponseResult.success(null);
//    }
}
