/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.blazartech.MultiDroolsDemo;

import static com.blazartech.MultiDroolsDemo.LargeCalcDemoBase.EVENT_COUNT;
import com.blazartech.MultiDroolsDemo.comp.data.CompensableEvent;
import com.blazartech.MultiDroolsDemo.comp.data.PayeeAllocation;
import com.blazartech.MultiDroolsDemo.comp.process.drools.DroolsCalculationAsyncService;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

/**
 *
 * @author scott
 */
@Component
public class LargeCalcParallelCommandLineRunner extends LargeCalcDemoBase implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(LargeCalcParallelCommandLineRunner.class);

    @Autowired
    private DroolsCalculationAsyncService calcService;

    private CompletableFuture<Collection<PayeeAllocation>> calculateCompensation(CompensableEvent event) {
        return calcService.deriveCompensationForCompensableEvent(event);
    }

    private Collection<PayeeAllocation> getAllocations(Future<Collection<PayeeAllocation>> f) {
        try {
            return f.get();
        } catch (InterruptedException | ExecutionException ex) {
            throw new RuntimeException("error getting allocations: " + ex.getMessage(), ex);
        }
    }

    @Override
    public void run(String... args) throws Exception {
        logger.info("calculating for " + EVENT_COUNT + " compensable events, parallel");

        StopWatch clock = new StopWatch("Profiling for parallel call");
        clock.start("parallel");

        Collection<CompensableEvent> compensableEvents = createCompensableEvents();
        Collection<Future<Collection<PayeeAllocation>>> calcFutures = compensableEvents.stream()
                .map(e -> calculateCompensation(e))
                .collect(Collectors.toList());

        Collection<PayeeAllocation> compensation = calcFutures.stream()
                .map(f -> getAllocations(f))
                .flatMap(c -> c.stream())
                .collect(Collectors.toList());

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
