package com.cn.chonglin;

import com.braintreegateway.BraintreeGateway;
import com.cn.chonglin.common.BraintreeGatewayFactory;
import com.cn.chonglin.config.properties.BraintreeProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ClassUtils;

import java.io.File;

@SpringBootApplication
@Configuration
@EnableConfigurationProperties({BraintreeProperties.class})
public class Application {
	public static String DEFAULT_CONFIG_FILENAME = ClassUtils.getDefaultClassLoader().getResource("").getPath() + "braintree.properties";
	public static BraintreeGateway gateway;

	public static void main(String[] args) {

		File configFile = new File(DEFAULT_CONFIG_FILENAME);

		try {
			if(configFile.exists() && !configFile.isDirectory()) {
				gateway = BraintreeGatewayFactory.fromConfigFile(configFile);
			} else {
				gateway = BraintreeGatewayFactory.fromConfigMapping(System.getenv());
			}
		} catch (NullPointerException e) {
			System.err.println("Could not load Braintree configuration from config file or system environment.");
			System.exit(1);
		}

		SpringApplication.run(Application.class, args);
	}
}
