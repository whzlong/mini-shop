package com.cn.chonglin.web.home;

import com.cn.chonglin.bussiness.home.HomeService;
import com.cn.chonglin.bussiness.home.vo.SpecialItemVo;
import com.cn.chonglin.common.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 主页
 *
 */
@RestController
public class HomeController {
    @Autowired
    private HomeService homeService;

    @GetMapping(value = "anonym/special-items", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseResult<SpecialItemVo> getItems(){
        return ResponseResult.success(homeService.getSpecilItems());
    }
}
