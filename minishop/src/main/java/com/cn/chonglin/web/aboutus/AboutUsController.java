package com.cn.chonglin.web.aboutus;

import com.cn.chonglin.bussiness.base.service.SettingService;
import com.cn.chonglin.bussiness.base.vo.CompanyVo;
import com.cn.chonglin.common.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 关于我们
 *
 */
@RestController
public class AboutUsController {
    @Autowired
    private SettingService settingService;

    @GetMapping(value = "client/company", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseResult<CompanyVo> aboutUs(){
        return ResponseResult.success(settingService.findCompanyInfo());
    }
}
