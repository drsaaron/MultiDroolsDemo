/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.blazartech.MultiDroolsDemo;

import static java.lang.StrictMath.log;
import java.util.concurrent.Executor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 *
 * @author scott
 */
@Configuration 
@EnableAsync
public class AsyncConfiguration implements AsyncConfigurer {
    
    private static final Logger log = LoggerFactory.getLogger(AsyncConfiguration.class);
    
    @Value("${threadPool.minSize:6}")
    private int minSize;

    @Value("${threadPool.maxSize:10}")
    private int maxSize;

    @Value("${threadPool.queueCount:1000000}")
    private int queueSize;

    @Value("${threadPool.threadPrefix:asyncThread-}")
    private String threadPrefix;

    @Override
    @Bean(destroyMethod = "shutdown")
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(minSize);
        executor.setMaxPoolSize(maxSize);
        executor.setQueueCapacity(queueSize);
        executor.setThreadNamePrefix(threadPrefix);
        executor.initialize();
        return executor;
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return (ex, method, params) -> log.error("Uncaught async error", ex);
    }

}
