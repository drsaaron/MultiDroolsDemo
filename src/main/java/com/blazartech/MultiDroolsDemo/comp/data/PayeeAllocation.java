/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blazartech.MultiDroolsDemo.comp.data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author aar1069
 */
public class PayeeAllocation implements Serializable, AmountObject {
    
    public PayeeAllocation(EventAllocation eventAllocation) {
        this.eventAllocation = eventAllocation;
    }
    
    private long id;
    private EventAllocation eventAllocation;
    private BigDecimal amount;
    private double compensationRate = -1;
    private ThresholdType thresholdType;

    public ThresholdType getThresholdType() {
        return thresholdType;
    }

    public void setThresholdType(ThresholdType thresholdType) {
        this.thresholdType = thresholdType;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public EventAllocation getEventAllocation() {
        return eventAllocation;
    }

    public void setEventAllocation(EventAllocation eventAllocation) {
        this.eventAllocation = eventAllocation;
    }

    @Override
    public BigDecimal getAmount() {
        return amount;
    }

    @Override
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public double getCompensationRate() {
        return compensationRate;
    }

    public void setCompensationRate(double compensationRate) {
        this.compensationRate = compensationRate;
    }

    @Override
    public String toString() {
        return "PayeeAllocation{" + "id=" + id + ", eventAllocation=" + eventAllocation + ", amount=" + amount + ", compensationRate=" + compensationRate + ", thresholdType=" + thresholdType + '}';
    }
    
    
}
