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
public class SimpleCalcDemoCommandLineRunner implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(SimpleCalcDemoCommandLineRunner.class);
    
    @Autowired
    private DroolsCalculationService calcService;
    
    @Override
    public void run(String... args) throws Exception {
        logger.info("running a simple calculation");
        
        CompensableEvent compensableEvent = new CompensableEvent();
        compensableEvent.setId(100);
        compensableEvent.setProductContractNumber("TESTWL");
        compensableEvent.setProduct(CompensationProduct.VariableAnnuity);
        compensableEvent.setAmount(new BigDecimal("1000"));
        
        Collection<PayeeAllocation> compensation = calcService.deriveCompensationForCompensableEvent(compensableEvent);
        
        compensation.forEach(a -> logger.info("compensation " + a));
    }
    
    
}
