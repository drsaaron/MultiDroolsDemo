/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blazartech.MultiDroolsDemo;

import com.blazartech.MultiDroolsDemo.comp.data.CompensableEvent;
import com.blazartech.MultiDroolsDemo.comp.data.CompensationRecord;
import com.blazartech.MultiDroolsDemo.comp.process.drools.DroolsCalculationService;
import java.util.Collection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 *
 * @author aar1069
 */
@Component
@Order(2)
public class LargeCalcDemoCommandLineRunner extends LargeCalcDemoBase implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(LargeCalcDemoCommandLineRunner.class);
    
    @Autowired
    private DroolsCalculationService calcService;

    @Override
    public Collection<CompensationRecord> calculateCompensation(Collection<CompensableEvent> compensableEvents) {
        return calcService.deriveCompensationForCompensableEvent(compensableEvents);
    }

    @Override
    public String executionMode() {
        
        return "bulk";
    }

}
