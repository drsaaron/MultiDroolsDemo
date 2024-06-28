/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.blazartech.MultiDroolsDemo.comp.process.drools;

import com.blazartech.MultiDroolsDemo.comp.data.CompensableEvent;
import com.blazartech.MultiDroolsDemo.comp.data.CompensationRecord;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 *
 * @author scott
 */
@Service
public class DroolsCalculationAsyncServiceImpl implements DroolsCalculationAsyncService {
    
    @Autowired
    private DroolsCalculationService calcService;

    @Override
    @Async
    public CompletableFuture<Collection<CompensationRecord>> deriveCompensationForCompensableEvent(CompensableEvent compensableEvent) {
        return CompletableFuture.completedFuture(calcService.deriveCompensationForCompensableEvent(compensableEvent));
    }
}
