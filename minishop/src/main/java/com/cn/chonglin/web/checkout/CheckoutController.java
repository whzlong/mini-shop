package com.cn.chonglin.web.checkout;

import com.braintreegateway.*;
import com.cn.chonglin.StartupRunner;
import com.cn.chonglin.bussiness.checkout.CheckoutService;
import com.cn.chonglin.bussiness.checkout.vo.CheckoutPageVo;
import com.cn.chonglin.bussiness.order.service.OrderService;
import com.cn.chonglin.common.ResponseResult;
import com.cn.chonglin.common.Responses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Arrays;

/**
 * Checkout
 *
 */
@Controller
public class CheckoutController {

    private Transaction.Status[] TRANSACTION_SUCCESS_STATUSES = new Transaction.Status[] {
            Transaction.Status.AUTHORIZED,
            Transaction.Status.AUTHORIZING,
            Transaction.Status.SETTLED,
            Transaction.Status.SETTLEMENT_CONFIRMED,
            Transaction.Status.SETTLEMENT_PENDING,
            Transaction.Status.SETTLING,
            Transaction.Status.SUBMITTED_FOR_SETTLEMENT
    };

    @Autowired
    private CheckoutService checkoutService;

    @Autowired
    private OrderService orderService;

    @PostMapping(value = "client/checkouts", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody
    ResponseResult<Object> checkNonce(@RequestParam String amount
                                        , @RequestParam String nonce
                                        , ModelMap modelMap){

        BigDecimal decimalAmount;

        try{
            decimalAmount = new BigDecimal(amount);
        }catch (NumberFormatException ne){
            return ResponseResult.error(1, "");
        }

        TransactionRequest transactionRequest = new TransactionRequest()
                .amount(decimalAmount)
                .paymentMethodNonce(nonce)
                .options()
                    .submitForSettlement(true)
                    .done();

        Result<Transaction> result = StartupRunner.gateway.transaction().sale(transactionRequest);

        if (result.isSuccess()) {
            Transaction transaction = result.getTarget();

            return ResponseResult.success(Responses.id(transaction.getId()));
        } else if (result.getTransaction() != null) {
            Transaction transaction = result.getTransaction();
            return ResponseResult.success(Responses.id(transaction.getId()));
        } else {
            String errorString = "";
            for (ValidationError error : result.getErrors().getAllDeepValidationErrors()) {
                errorString += "Error: " + error.getCode() + ": " + error.getMessage() + "\n";
            }

            return ResponseResult.error(1, errorString);
        }
    }

    @GetMapping(value = "client/checkouts/{transactionId}")
    public String getTransaction(@PathVariable String transactionId, ModelMap modelMap){
        Transaction transaction;
        CreditCard creditCard;
        Customer customer;

        try{
            transaction = StartupRunner.gateway.transaction().find(transactionId);

            if(Arrays.asList(TRANSACTION_SUCCESS_STATUSES).contains(transaction.getStatus())){
                orderService.createOrderFromCart();

//                creditCard = transaction.getCreditCard();
//                customer = transaction.getCustomer();

                modelMap.addAttribute("result", true);
            }else{
                modelMap.addAttribute("result", false);
            }


        }catch (Exception ex){
            System.out.println("Exception: " + ex);
            modelMap.addAttribute("result", false);
        }

        return "client/payment-end";
    }

    @GetMapping(value = "client/check-out/init", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody
    ResponseResult<CheckoutPageVo> getInitData(){
        return ResponseResult.success(checkoutService.getInitData());
    }

}
