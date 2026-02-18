/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.blazartech.MultiDroolsDemo;

import com.blazartech.MultiDroolsDemo.comp.data.AmountObject;
import com.blazartech.MultiDroolsDemo.comp.data.CompensableEvent;
import com.blazartech.MultiDroolsDemo.comp.data.CompensationRecord;
import com.blazartech.MultiDroolsDemo.comp.data.Product;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.util.StopWatch;

/**
 *
 * @author scott
 */
public abstract class LargeCalcDemoBase implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(LargeCalcDemoBase.class);
    
    protected static final int EVENT_COUNT = 100000;

    protected BigDecimal accumulateAmount(Collection<? extends AmountObject> collection) {
        return collection.stream()
                .map(AmountObject::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private CompensableEvent buildCompensableEvent(int i) {
        CompensableEvent compensableEvent = new CompensableEvent();
        compensableEvent.setId(1000 + i);
        compensableEvent.setPolicyNumber("TEST" + Integer.toString(i));
        Product product = ((i & 0x01) == 0) ? Product.VariableAnnuity : Product.WholeLife;
        compensableEvent.setProduct(product);
        compensableEvent.setAmount(new BigDecimal("1000"));
        return compensableEvent;
    }

    public Collection<CompensableEvent> createCompensableEvents() {
        return IntStream.range(0, EVENT_COUNT)
                .mapToObj(i -> buildCompensableEvent(i))
                .collect(Collectors.toList());
    }
    
    public void logResults(Collection<CompensableEvent> compensableEvents, Collection<CompensationRecord> compensation) {
        
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
        BigDecimal totalCompensation = accumulateAmount(compensation);
        BigDecimal totalCompEventAmount = accumulateAmount(compensableEvents);
        logger.info("total comp event amount: {}", currencyFormat.format(totalCompEventAmount));
        logger.info("total compensation: {}", currencyFormat.format(totalCompensation));
    }

    @Override
    public void run(String... args) throws Exception {
        
        logger.info("calculating for " + EVENT_COUNT + " compensable events, {}", executionMode());
        
        StopWatch clock = new StopWatch("Profiling for " + executionMode() + " call");
        clock.start(executionMode());

        Collection<CompensableEvent> compensableEvents = createCompensableEvents();
        
        Collection<CompensationRecord> compensation = calculateCompensation(compensableEvents);
        
        logger.info("calculated " + compensation.size() + " comp records");
        logResults(compensableEvents, compensation);        
        
        clock.stop();
        logger.info(clock.prettyPrint());
    }
    
    public abstract Collection<CompensationRecord> calculateCompensation(Collection<CompensableEvent> compensableEvents);
    public abstract String executionMode();
}
