/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blazartech.MultiDroolsDemo;

import com.blazartech.MultiDroolsDemo.comp.data.CompensableEvent;
import com.blazartech.MultiDroolsDemo.comp.data.PayeeAllocation;
import com.blazartech.MultiDroolsDemo.comp.process.drools.DroolsCalculationService;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Collection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

/**
 *
 * @author aar1069
 */
@Component
public class LargeCalcDemoCommandLineRunner extends LargeCalcDemoBase implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(LargeCalcDemoCommandLineRunner.class);
    
    @Autowired
    private DroolsCalculationService calcService;
    
    @Override
    public void run(String... args) throws Exception {
        logger.info("calculating for " + EVENT_COUNT + " compensable events, bulk");
        
        StopWatch clock = new StopWatch("Profiling for bulk call");
        clock.start("bulk");

        Collection<CompensableEvent> compensableEvents = createCompensableEvents();
        
        Collection<PayeeAllocation> compensation = calcService.deriveCompensationForCompensableEvent(compensableEvents);
        
        logger.info("calculated " + compensation.size() + " comp records");
        
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
        BigDecimal totalCompensation = accumulateAmount(compensation);
        BigDecimal totalCompEventAmount = accumulateAmount(compensableEvents);
        logger.info("total comp event amount: {}", currencyFormat.format(totalCompEventAmount));
        logger.info("total compensation: {}", currencyFormat.format(totalCompensation));
        
        clock.stop();
        logger.info(clock.prettyPrint());
    }

}
