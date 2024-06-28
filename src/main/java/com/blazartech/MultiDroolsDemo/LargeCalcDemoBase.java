/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.blazartech.MultiDroolsDemo;

import com.blazartech.MultiDroolsDemo.comp.data.AmountObject;
import com.blazartech.MultiDroolsDemo.comp.data.CompensableEvent;
import com.blazartech.MultiDroolsDemo.comp.data.Product;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author scott
 */
public abstract class LargeCalcDemoBase {

    protected static final int EVENT_COUNT = 100000;

    protected BigDecimal accumulateAmount(Collection<? extends AmountObject> collection) {
        return collection.stream()
                .map(AmountObject::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
    
    public Collection<CompensableEvent> createCompensableEvents() {
        Collection<CompensableEvent> compensableEvents = new ArrayList<>();

        for (int i = 0; i < EVENT_COUNT; i++) {
            CompensableEvent compensableEvent = new CompensableEvent();
            compensableEvent.setId(1000 + i);
            compensableEvent.setPolicyNumber("TEST" + Integer.toString(i));
            Product product = ((i & 0x01) == 0) ? Product.VariableAnnuity : Product.WholeLife;
            compensableEvent.setProduct(product);
            compensableEvent.setAmount(new BigDecimal("1000"));
            compensableEvents.add(compensableEvent);
        }
        
        return compensableEvents;
    }
}
