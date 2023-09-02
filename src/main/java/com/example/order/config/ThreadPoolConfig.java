package com.example.order.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.concurrent.CustomizableThreadFactory;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

/**
 * @author : Franky
 * @description :
 * @date : 2020/5/14 3:38 PM
 */

@Configuration
public class ThreadPoolConfig {

    @Primary
    @Bean
    public ThreadPoolExecutor threadPoolTaskExecutor() {
        return new ThreadPoolExecutor(2 * Runtime.getRuntime().availableProcessors(), 1024,
                60L, TimeUnit.MILLISECONDS,
                new SynchronousQueue(), new CustomizableThreadFactory("rule-threadpoll-"));
    }
}

