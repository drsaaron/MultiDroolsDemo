/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blazartech.MultiDroolsDemo.rate.process.drools;

import com.blazartech.MultiDroolsDemo.comp.data.CompensationProduct;
import com.blazartech.MultiDroolsDemo.comp.data.CompensationProgram;
import com.blazartech.MultiDroolsDemo.comp.data.ThresholdType;

/**
 *
 * @author aar1069
 */
public class RateDeterminationData {
    
    private RateTableType rateTable;
    
    private CompensationProgram program;
    private CompensationProduct product;
    private ThresholdType thresholdType;
    
    private double rate;

    public RateTableType getRateTable() {
        return rateTable;
    }

    public void setRateTable(RateTableType rateTable) {
        this.rateTable = rateTable;
    }

    public CompensationProgram getProgram() {
        return program;
    }

    public void setProgram(CompensationProgram program) {
        this.program = program;
    }

    public CompensationProduct getProduct() {
        return product;
    }

    public void setProduct(CompensationProduct product) {
        this.product = product;
    }

    public ThresholdType getThresholdType() {
        return thresholdType;
    }

    public void setThresholdType(ThresholdType thresholdType) {
        this.thresholdType = thresholdType;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }
    
    
}
