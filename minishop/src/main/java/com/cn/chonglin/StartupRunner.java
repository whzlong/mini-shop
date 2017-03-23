package com.cn.chonglin;

import com.braintreegateway.BraintreeGateway;
import com.cn.chonglin.config.properties.BraintreeProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class StartupRunner implements CommandLineRunner{
    private static final Logger logger = LoggerFactory.getLogger(StartupRunner.class);
    @Autowired
    private BraintreeProperties braintreeProperties;
    public static BraintreeGateway gateway;

    @Override
    public void run(String... args) throws Exception {
        try{
            gateway = new BraintreeGateway(braintreeProperties.getEnviroment()
                    , braintreeProperties.getMerchantId()
                    , braintreeProperties.getPublicKey()
                    , braintreeProperties.getPrivateKey());
        }catch (Exception ex){
            logger.error("Could not load Braintree configuration from config file or system environment.");
            System.err.println("Could not load Braintree configuration from config file or system environment.");
            System.exit(1);
        }

    }
}
