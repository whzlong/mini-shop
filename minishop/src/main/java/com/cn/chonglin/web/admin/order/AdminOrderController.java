package com.cn.chonglin.web.admin.order;

import com.cn.chonglin.bussiness.order.domain.OrderDetail;
import com.cn.chonglin.bussiness.order.service.OrderService;
import com.cn.chonglin.bussiness.order.vo.OrderVo;
import com.cn.chonglin.web.admin.order.vo.OrderListPageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.constraints.Min;
import java.util.List;

/**
 * 订单控制器
 *
 * @author wu
 */
@Controller
@RequestMapping("admin")
public class AdminOrderController {
    /**
     * 列表显示数量
     */
    private static final int listSize = 5;

    /**
     * 显示分页数量
     */
    private static final int pageSize = 5;

    @Autowired
    private OrderService orderService;

    @RequestMapping(value = "order-list", method = {RequestMethod.GET, RequestMethod.POST})
    public String list(@RequestParam(required = false, defaultValue = "") String orderId
                        , @RequestParam(required = false, defaultValue = "Appointment") String state
                       , @RequestParam(required = false, defaultValue = "") String orderDateFrom
                        , @RequestParam(required = false, defaultValue = "") String orderDateTo
                       , @RequestParam(required = false, defaultValue = "1") @Min(1) int beginPage
                       , @RequestParam(required = false, defaultValue = "1") @Min(1) int currentPage
                       , ModelMap modelMap
                       ){
        int totalPage = 0;
        int recordCount = orderService.getOrdersCount(orderId, orderDateFrom, orderDateTo, state);

        List<OrderVo> orders = orderService.queryForList(orderId, orderDateFrom, orderDateTo,state, listSize, listSize*(currentPage - 1));

        modelMap.addAttribute("orders", orders);

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

        OrderListPageVo listPageVo = new OrderListPageVo();

        listPageVo.setTotalPage(totalPage);
        listPageVo.setCurrentPage(currentPage);
        listPageVo.setBeginPage(beginPage);
        listPageVo.setEndPage(endPage);
        listPageVo.setLeftPage(totalPage - beginPage);
        listPageVo.setListSize(listSize);
        listPageVo.setPageSize(pageSize);


        listPageVo.setOrderId(orderId);
        listPageVo.setOrderDateFrom(orderDateFrom);
        listPageVo.setOrderDateTo(orderDateTo);
        listPageVo.setState(state);


        modelMap.addAttribute("listPageVo", listPageVo);
        modelMap.addAttribute("orderActive", true);

        return "admin/order/order-list";
    }

    @PostMapping(value = "orderDetails/{id}")
    public String queryForOrderDetailList(OrderListPageVo params, @PathVariable String id, ModelMap modelMap){

        List<OrderDetail> orderDetails = orderService.queryOrderDetails(id);

        modelMap.addAttribute("orderDetails", orderDetails);
        modelMap.addAttribute("previousPageVo", params);

        return "admin/order/order-detail";
    }

    @PostMapping(value = "orders/{id}")
    public String delete(OrderListPageVo params, @PathVariable String id, RedirectAttributes attributes){

        orderService.delete(Long.valueOf(id));

        attributes.addAttribute("beginPage", params.getBeginPage());
        attributes.addAttribute("currentPage", params.getCurrentPage());

        attributes.addAttribute("orderId", params.getOrderId());
        attributes.addAttribute("state", params.getState());
        attributes.addAttribute("orderDateFrom", params.getOrderDateFrom());
        attributes.addAttribute("orderDateTo", params.getOrderDateTo());

        return "redirect:/admin/order-list";
    }
}
