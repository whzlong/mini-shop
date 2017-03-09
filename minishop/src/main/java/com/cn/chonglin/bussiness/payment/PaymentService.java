package com.cn.chonglin.bussiness.payment;

import com.cn.chonglin.config.properties.BraintreeProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 支付Service
 */
@Service
public class PaymentService {
    @Autowired
    private BraintreeProperties braintreeProperties;

//    static {
//        BraintreeGateway gateway = ;
//    }
    public void saveTransaction(){
        System.out.println("dd");
    }
}
