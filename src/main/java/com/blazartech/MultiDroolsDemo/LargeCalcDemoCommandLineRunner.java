/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blazartech.MultiDroolsDemo;

import com.blazartech.MultiDroolsDemo.comp.data.CompensableEvent;
import com.blazartech.MultiDroolsDemo.comp.data.CompensationProduct;
import com.blazartech.MultiDroolsDemo.comp.data.PayeeAllocation;
import com.blazartech.MultiDroolsDemo.comp.process.drools.DroolsCalculationService;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 *
 * @author aar1069
 */
@Component
public class LargeCalcDemoCommandLineRunner implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(LargeCalcDemoCommandLineRunner.class);

    @Autowired
    private DroolsCalculationService calcService;

    private static final int EVENT_COUNT = 100000;

    @Override
    public void run(String... args) throws Exception {
        logger.info("calculating for " + EVENT_COUNT + " compensable events");

        Collection<CompensableEvent> compensableEvents = new ArrayList<>();

        for (int i = 0; i < EVENT_COUNT; i++) {
            CompensableEvent compensableEvent = new CompensableEvent();
            compensableEvent.setId(1000 + i);
            compensableEvent.setProductContractNumber("TEST" + Integer.toString(i));
            CompensationProduct product = ((i & 0x01) == 0) ? CompensationProduct.VariableAnnuity : CompensationProduct.WholeLife;
            compensableEvent.setProduct(product);
            compensableEvent.setAmount(new BigDecimal("1000"));
            compensableEvents.add(compensableEvent);
        }
        
        Collection<PayeeAllocation> compensation = calcService.deriveCompensationForCompensableEvent(compensableEvents);
        
        logger.info("calculated " + compensation.size() + " comp records");
    }

}
