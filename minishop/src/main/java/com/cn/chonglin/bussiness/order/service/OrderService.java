package com.cn.chonglin.bussiness.order.service;

import com.cn.chonglin.bussiness.appointment.domain.Appointment;
import com.cn.chonglin.bussiness.base.dao.SettingDao;
import com.cn.chonglin.bussiness.base.dao.UserDao;
import com.cn.chonglin.bussiness.base.domain.Setting;
import com.cn.chonglin.bussiness.base.domain.User;
import com.cn.chonglin.bussiness.cart.service.CartService;
import com.cn.chonglin.bussiness.cart.vo.CartItemVo;
import com.cn.chonglin.bussiness.cart.vo.CartVo;
import com.cn.chonglin.bussiness.item.dao.ItemDao;
import com.cn.chonglin.bussiness.item.domain.Item;
import com.cn.chonglin.bussiness.item.service.ItemService;
import com.cn.chonglin.bussiness.mail.OrderMonitor;
import com.cn.chonglin.bussiness.mail.OrderStatusSender;
import com.cn.chonglin.bussiness.order.dao.OrderDao;
import com.cn.chonglin.bussiness.order.dao.OrderDetailDao;
import com.cn.chonglin.bussiness.order.domain.Order;
import com.cn.chonglin.bussiness.order.domain.OrderDetail;
import com.cn.chonglin.bussiness.order.vo.OrderDetailVo;
import com.cn.chonglin.bussiness.order.vo.OrderVo;
import com.cn.chonglin.common.ListPage;
import com.cn.chonglin.common.mail.MailService;
import com.cn.chonglin.constants.DropdownListContants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
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
    private SettingDao settingDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private ItemDao itemDao;

    @Autowired
    private OrderDetailDao orderDetailDao;

    @Autowired
    private ItemService itemService;

    @Autowired
    private CartService cartService;

    @Autowired
    private MailService mailService;

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

    /**
     * 对购物车中的商品进行结算后生成订单
     *
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void createOrderFromCart(){
        CartVo cartVo = cartService.getCart();
        //订单号
        long orderId = createOrderId();


        //增加订单明细
        OrderDetail orderDetail;
        Item item;
        for(CartItemVo cartItemVo : cartVo.getCartItemVos()){
            orderDetail = new OrderDetail();

            orderDetail.setOrderId(orderId);
            orderDetail.setItemId(cartItemVo.getItemId());
            orderDetail.setQuantity(cartItemVo.getQuantity());

            item = itemService.findByKey(cartItemVo.getItemId());

            if(DropdownListContants.ITEM_STATE_DISCOUNT.equals(item.getState())){
                orderDetail.setOrderPrice(item.getDiscountPrice());
            }else{
                orderDetail.setOrderPrice(item.getUnitPrice());
            }

            orderDetailDao.insert(orderDetail);
        }

        //增加订单
        Order order = new Order();

        order.setOrderId(orderId);
        order.setUserId(cartVo.getCartId());
        order.setPayDate(LocalDate.now());
        order.setTotalAmount(orderDetailDao.getSumPrice(orderId));
        order.setState(DropdownListContants.ORDER_STATE_PAID);

        orderDao.insert(order);

        //更新库存
        try {

            for(CartItemVo cartItemVo : cartVo.getCartItemVos()){
                item = itemDao.findOneForUpdate(cartItemVo.getItemId());
                //减库存
                item.setStock(item.getStock() - cartItemVo.getQuantity());
                itemDao.update(item);
            }

        }catch (DataAccessException ex){
            Setting setting = settingDao.queryForObject();

            //通知管理员，系统发生异常
            List<String> mailList = new ArrayList<>();
            mailList.add(setting.getEmail());
            mailService.asynSend(new OrderMonitor(order), mailList);
        }

        //清空购物车中的商品
        cartService.clearCart(cartVo.getCartId());

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

    /**
     * 将订单状态发送给客户
     *
     * @param order
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void emailToCustomer(Order order){
        //更新订单
        update(order);

        Order currentOrder = orderDao.findByKey(order.getOrderId());

        User user = userDao.findByKey(currentOrder.getUserId());

        //发送帐户验证邮件
        List<String> mailList = new ArrayList<>();
        mailList.add(user.getEmail());
        mailService.asynSend(new OrderStatusSender(order), mailList);
    }

}
