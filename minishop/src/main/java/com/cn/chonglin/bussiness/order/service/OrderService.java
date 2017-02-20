package com.cn.chonglin.bussiness.order.service;

import com.cn.chonglin.bussiness.base.dao.UserDao;
import com.cn.chonglin.bussiness.base.domain.User;
import com.cn.chonglin.bussiness.item.domain.Item;
import com.cn.chonglin.bussiness.item.service.ItemService;
import com.cn.chonglin.bussiness.order.dao.OrderDao;
import com.cn.chonglin.bussiness.order.dao.OrderDetailDao;
import com.cn.chonglin.bussiness.order.domain.Order;
import com.cn.chonglin.bussiness.order.domain.OrderDetail;
import com.cn.chonglin.bussiness.order.vo.OrderVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
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

    @Transactional(propagation = Propagation.REQUIRED)
    public void save(String itemId, String bookDate, String bookTime){
        Order order = new Order();
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userDao.findByEmail(email);
        Item item = itemService.findByKey(itemId);

        //订单号
        long orderId = createOrderId();

        order.setOrderId(orderId);
        order.setCustomerId(user.getId());
        order.setCustomerName(user.getFirstName() + " " + user.getLastName());
        order.setOrderDate(bookDate);
        order.setOrderTime(bookTime);
        //折扣价一般情况下与单价一样
        order.setTotalAmount(item.getDiscountPrice());
        order.setComment(" ");
        order.setState("Appointment");

        orderDao.insert(order);

        OrderDetail orderDetail = new OrderDetail();

        orderDetail.setOrderId(orderId);
        orderDetail.setItemId(itemId);
        orderDetail.setItemName(item.getItemName());
        orderDetail.setQuantity(1);
        orderDetail.setUnitPrice(item.getUnitPrice());
        orderDetail.setDiscountPrice(item.getDiscountPrice());

        orderDetailDao.insert(orderDetail);
    }

    public int getOrdersCount(String orderId,  String orderDateFrom, String orderDateTo, String state){
        return orderDao.getRecordCount(orderId, orderDateFrom, orderDateTo, state);
    }

    public List<OrderVo> queryForList(String orderId,  String orderDateFrom, String orderDateTo, String state, int limit, int offset){
        return orderDao.queryForList(orderId, orderDateFrom, orderDateTo, state, limit, offset);
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

    public List<OrderDetail> queryOrderDetails(String orderId){
        return orderDetailDao.findByKey(Long.valueOf(orderId));
    }


    @Transactional(propagation = Propagation.REQUIRED)
    public void delete(Long id){
        orderDao.delete(id);
        orderDetailDao.delete(id);
    }
}
