package com.busd;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.braintreegateway.BraintreeGateway;

@SpringBootApplication
public class DemoApplication {
	
    
    @Autowired
    private BtConfigProperties btConfigProperties;
    
    
    @Bean
    public BraintreeGateway braintreeGateway(){
    	return new BraintreeGateway(
 				btConfigProperties.getEnvironment(),
 				btConfigProperties.getMerchantId(),
 				btConfigProperties.getPublicKey(),
 				btConfigProperties.getPrivateKey()
 	        );
    }

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
}
