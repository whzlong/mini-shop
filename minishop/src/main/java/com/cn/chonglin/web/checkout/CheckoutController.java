package com.cn.chonglin.web.checkout;

import com.cn.chonglin.bussiness.checkout.CheckoutService;
import com.cn.chonglin.bussiness.checkout.vo.CheckoutPageVo;
import com.cn.chonglin.common.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Checkout
 *
 */
@Controller
public class CheckoutController {

    @Autowired
    private CheckoutService checkoutService;

    @PostMapping(value = "client/checkouts", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody
    ResponseResult<Object> getTransaction(@RequestParam String amount
                                        , @RequestParam String nonce
                                        , ModelMap modelMap){


        return ResponseResult.success(null);
    }

    @GetMapping(value = "client/check-out", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody
    ResponseResult<CheckoutPageVo> getInitData(){
        return ResponseResult.success(checkoutService.getInitData());
    }
}
