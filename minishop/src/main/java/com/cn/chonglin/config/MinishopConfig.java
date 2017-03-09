package com.cn.chonglin.config;

import com.cn.chonglin.config.properties.BraintreeProperties;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
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
@EnableConfigurationProperties({BraintreeProperties.class})
public class MinishopConfig {
//    @Bean
//    public ObjectMapper jacksonObjectMapper() {
//        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL)
//                .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
//        return objectMapper;
//    }

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
