/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blazartech.MultiDroolsDemo.comp.process.drools;

import java.math.BigDecimal;

/**
 *
 * @author aar1069
 */
public class CalculationData {
    
    private boolean eligibleToCalc = false;
    private double compensationRate = 0;
    private BigDecimal compensationAmount;

    public boolean isEligibleToCalc() {
        return eligibleToCalc;
    }

    public void setEligibleToCalc(boolean eligibleToCalc) {
        this.eligibleToCalc = eligibleToCalc;
    }

    public double getCompensationRate() {
        return compensationRate;
    }

    public void setCompensationRate(double compensationRate) {
        this.compensationRate = compensationRate;
    }

    public BigDecimal getCompensationAmount() {
        return compensationAmount;
    }

    public void setCompensationAmount(BigDecimal compensationAmount) {
        this.compensationAmount = compensationAmount;
    }

    @Override
    public String toString() {
        return "CalculationData{" + "eligibleToCalc=" + eligibleToCalc + ", compensationRate=" + compensationRate + ", compensationAmount=" + compensationAmount + '}';
    }
    
    
}
