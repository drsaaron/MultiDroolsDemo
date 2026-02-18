/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.blazartech.MultiDroolsDemo;

import java.util.concurrent.ForkJoinPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 *
 * @author scott
 */
@Configuration 
@EnableAsync
public class AsyncConfiguration {
    
    private static final Logger log = LoggerFactory.getLogger(AsyncConfiguration.class);
    
    @Value("${threadPool.minSize:6}")
    private int minSize;

    @Value("${threadPool.maxSize:10}")
    private int maxSize;

    @Value("${threadPool.queueCount:1000000}")
    private int queueSize;

    @Value("${threadPool.threadPrefix:asyncThread-}")
    private String threadPrefix;

    @Bean(destroyMethod = "shutdown")
    @Scope("prototype")
    public ForkJoinPool forkJoinPool() {
        return new ForkJoinPool(maxSize);
    }
}
