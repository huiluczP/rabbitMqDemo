package com.huiluczp.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitInfoConfig {
    public static final String EXCHANGE = "exchange.message";
    public static final String ROUTING = "routing.message";
    public static final String QUEUE = "queue.message";
}
