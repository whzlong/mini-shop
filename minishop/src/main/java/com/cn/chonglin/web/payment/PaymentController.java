package com.cn.chonglin.web.payment;

import com.cn.chonglin.Application;
import com.cn.chonglin.common.ResponseResult;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Paypal支付
 */
@Controller
public class PaymentController {

    @GetMapping(value = "client/client-token", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody
    ResponseResult<Object> getClientToken(){
        ClientTokenVo clientTokenVo = new ClientTokenVo();

        clientTokenVo.setClientToken(Application.gateway.clientToken().generate());

        return ResponseResult.success(clientTokenVo);
    }
}
