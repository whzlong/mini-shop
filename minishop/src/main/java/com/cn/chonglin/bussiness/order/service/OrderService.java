package com.cn.chonglin.bussiness.order.service;

import com.cn.chonglin.bussiness.appointment.domain.Appointment;
import com.cn.chonglin.bussiness.base.dao.UserDao;
import com.cn.chonglin.bussiness.base.domain.User;
import com.cn.chonglin.bussiness.item.domain.Item;
import com.cn.chonglin.bussiness.item.service.ItemService;
import com.cn.chonglin.bussiness.order.dao.OrderDao;
import com.cn.chonglin.bussiness.order.dao.OrderDetailDao;
import com.cn.chonglin.bussiness.order.domain.Order;
import com.cn.chonglin.bussiness.order.domain.OrderDetail;
import com.cn.chonglin.bussiness.order.vo.OrderDetailVo;
import com.cn.chonglin.bussiness.order.vo.OrderVo;
import com.cn.chonglin.common.ListPage;
import com.cn.chonglin.constants.DropdownListContants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * 订单Service
 *
 * @author wu
 */
@Service
@Transactional(readOnly = true)
public class OrderService {
    @Autowired
    private UserDao userDao;

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private OrderDetailDao orderDetailDao;

    @Autowired
    private ItemService itemService;

    /**
     * 预约完成确认后生成订单
     *
     * @param appointment
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void createOrderFromAppointment(Appointment appointment){
        Order order = new Order();

        User user = userDao.findByKey(appointment.getUserId());

        Item item = itemService.findByKey(appointment.getItemId());

        //订单号
        long orderId = createOrderId();

        order.setOrderId(orderId);
        order.setUserId(appointment.getUserId());
        if(DropdownListContants.ITEM_STATE_DISCOUNT.equals(item.getState())){
            order.setTotalAmount(item.getDiscountPrice());
        }else{
            order.setTotalAmount(item.getUnitPrice());
        }

        order.setState(DropdownListContants.ORDER_STATE_APPOINTMENT);

        orderDao.insert(order);

        OrderDetail orderDetail = new OrderDetail();

        orderDetail.setOrderId(orderId);
        orderDetail.setItemId(appointment.getItemId());
        orderDetail.setQuantity(1);

        orderDetailDao.insert(orderDetail);

    }

    public ListPage<OrderVo> query(String orderId, String orderDateFrom, String orderDateTo, String state, int limit, int page){

        int count = orderDao.getRecordCount(orderId, orderDateFrom, orderDateTo, state);

        List<OrderVo> orderVos = orderDao.query(orderId, orderDateFrom, orderDateTo, state, limit, page*limit);

        return new ListPage<>(count, orderVos);
    }

    /**
     * 生成订单号
     *
     * @return
     */
    private long createOrderId(){
        //创建订单号
        long orderId = 1L;

        while (true){

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyMMdd");

            String orderDate = simpleDateFormat.format(new java.util.Date());

            java.util.Random random = new java.util.Random();


            orderId = Long.valueOf(orderDate + String.valueOf(random.nextInt(999999)));

            if(orderDao.checkOrderId(orderId) == 0){
                break;
            }
        }

        return orderId;
    }

    public List<OrderDetailVo> queryOrderDetails(String orderId){
        return orderDetailDao.query(Long.valueOf(orderId));
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void delete(Long id){
        orderDao.delete(id);
        orderDetailDao.delete(id);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void update(Order order){
        Order currentOrder = orderDao.findByKey(order.getOrderId());

        currentOrder.setPayDate(order.getPayDate());
        currentOrder.setState(order.getState());
        currentOrder.setShipAddress(order.getShipAddress());
        currentOrder.setComment(order.getComment());

        orderDao.update(currentOrder);

        //当支付完成后，将商品成交价更新至订单明细表
        if(DropdownListContants.ORDER_STATE_PAID.equals(order.getState())){
            List<OrderDetailVo> orderDetailVos = orderDetailDao.query(order.getOrderId());

            for(OrderDetailVo orderDetailVo : orderDetailVos){
                if(DropdownListContants.ITEM_STATE_DISCOUNT.equals(orderDetailVo.getItemState())){
                    orderDetailDao.updateOrderPrice(order.getOrderId(), orderDetailVo.getItemId(), orderDetailVo.getDiscountPrice());
                }else{
                    orderDetailDao.updateOrderPrice(order.getOrderId(), orderDetailVo.getItemId(), orderDetailVo.getUnitPrice());
                }

            }
        }

    }
}
