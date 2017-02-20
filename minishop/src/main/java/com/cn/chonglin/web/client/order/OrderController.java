package com.cn.chonglin.web.client.order;

import com.cn.chonglin.bussiness.item.domain.Item;
import com.cn.chonglin.bussiness.item.service.ItemService;
import com.cn.chonglin.bussiness.order.service.OrderService;
import com.cn.chonglin.web.client.order.form.AppointmentForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.List;

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
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "book", method = RequestMethod.POST)
    public String book(@Valid AppointmentForm appointmentForm, BindingResult bindingResult, ModelMap modelMap){
        Item item = itemService.findByKey(appointmentForm.getItemId());

        modelMap.addAttribute("item", item);

        if(bindingResult.hasErrors()){
            modelMap.addAttribute("errors", getErrorsMsg(bindingResult.getFieldErrors()));
        }

        orderService.save(item.getItemId(), appointmentForm.getBookDate(), appointmentForm.getBookTime());

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
