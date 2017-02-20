package com.cn.chonglin.web.admin.calendar;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 工作日历
 *
 * @author wu
 */
@Controller
@RequestMapping("admin")
public class CalendarController {
    @GetMapping("calendar")
    public String index(ModelMap modelMap){
        modelMap.addAttribute("scheduleActive", true);
        return "admin/calendar";
    }
}
