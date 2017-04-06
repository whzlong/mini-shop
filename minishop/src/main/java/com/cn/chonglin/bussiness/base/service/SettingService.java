package com.cn.chonglin.bussiness.base.service;

import com.cn.chonglin.bussiness.base.dao.SettingDao;
import com.cn.chonglin.bussiness.base.domain.Setting;
import com.cn.chonglin.bussiness.base.vo.CompanyVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 系统设置
 */
@Service
@Transactional(readOnly = true)
public class SettingService {
    @Autowired
    private SettingDao settingDao;

    public Setting findSetting(){
        return settingDao.queryForObject();
    }

    public CompanyVo findCompanyInfo(){
        Setting setting =  settingDao.queryForObject();

        CompanyVo companyVo = new CompanyVo();

        companyVo.setAddress(setting.getShopAddress());
        companyVo.setTelephone(setting.getShopTelephone());
        companyVo.setEmail(setting.getEmail());

        return companyVo;
    }
}
