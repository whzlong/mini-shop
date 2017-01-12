package com.cn.chonglin.web.admin.home;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 管理员账户控制器
 *
 * @author wu
 */
@Controller
@RequestMapping(value = "admin")
public class AdminHomeController {

    @GetMapping(value = "")
    public String home(){

        return "admin/home";
    }

}
