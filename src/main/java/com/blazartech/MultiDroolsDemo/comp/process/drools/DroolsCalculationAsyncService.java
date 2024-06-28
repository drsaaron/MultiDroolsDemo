/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.blazartech.MultiDroolsDemo.comp.process.drools;

import com.blazartech.MultiDroolsDemo.comp.data.CompensableEvent;
import com.blazartech.MultiDroolsDemo.comp.data.CompensationRecord;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;

/**
 *
 * @author scott
 */
public interface DroolsCalculationAsyncService {
    
    public CompletableFuture<Collection<CompensationRecord>> deriveCompensationForCompensableEvent(CompensableEvent compensableEvent);
}
