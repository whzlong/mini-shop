package com.cn.chonglin.config;

import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.sql.DataSource;

/**
 * 应用系统配置
 *
 * @author wu
 */
@Configuration
@EnableScheduling
public class MinishopConfig {
    @Bean(name = "businessDatasource")
    @Primary
    @ConfigurationProperties(prefix="spring.business.datasource")
    public DataSource businessDataSource(){
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "loggerDatasource")
    @ConfigurationProperties(prefix="spring.logger.datasource")
    public DataSource loggerDataSource(){
        return DataSourceBuilder.create().build();
    }
}
