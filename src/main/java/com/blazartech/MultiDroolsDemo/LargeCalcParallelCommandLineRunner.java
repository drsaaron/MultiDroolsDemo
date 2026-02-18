/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.blazartech.MultiDroolsDemo;

import com.blazartech.MultiDroolsDemo.comp.data.CompensableEvent;
import com.blazartech.MultiDroolsDemo.comp.data.CompensationRecord;
import com.blazartech.MultiDroolsDemo.comp.process.drools.DroolsCalculationService;
import java.util.Collection;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 *
 * @author scott
 */
@Component
@Order(3)
public class LargeCalcParallelCommandLineRunner extends LargeCalcDemoBase implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(LargeCalcParallelCommandLineRunner.class);
    
    @Autowired
    private DroolsCalculationService calcService;

    private Collection<CompensationRecord> calculateCompensation(CompensableEvent event) {
        return calcService.deriveCompensationForCompensableEvent(event);
    }

    @Override
    public Collection<CompensationRecord> calculateCompensation(Collection<CompensableEvent> compensableEvents) {
        
        return compensableEvents.parallelStream() // run the calculation for each comp event separately and in parallel
                .map(e -> calculateCompensation(e))
                .flatMap(c -> c.stream())
                .collect(Collectors.toList());
    }

    @Override
    public String executionMode() {
        return "parallel";
    }

}
