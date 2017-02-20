package com.cn.chonglin.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BaseController {
    @GetMapping(value = "/404")
    public String errorProcess(){
        return "redirect:/";
    }
}
