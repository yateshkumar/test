package com.equalexperts.shoppingcart.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Configuration class for the shopping cart application.
 *
 * @author yateshkumar
 */
@Configuration
public class ShoppingCartConfig {

    /**
     * Creates and configures a RestTemplate bean.
     *
     * @return a RestTemplate instance.
     */
    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
