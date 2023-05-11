/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blazartech.MultiDroolsDemo.rate.data;

import com.blazartech.MultiDroolsDemo.comp.data.CompensationProduct;
import com.blazartech.MultiDroolsDemo.comp.data.CompensationProgram;
import com.blazartech.MultiDroolsDemo.comp.data.ThresholdType;

/**
 *
 * @author aar1069
 */
public class CompensationRate {
    
    private CompensationProduct product;
    private CompensationProgram program;
    private ThresholdType thresholdType;
    
    private double rate;

    public CompensationProduct getProduct() {
        return product;
    }

    public void setProduct(CompensationProduct product) {
        this.product = product;
    }

    public CompensationProgram getProgram() {
        return program;
    }

    public void setProgram(CompensationProgram program) {
        this.program = program;
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
