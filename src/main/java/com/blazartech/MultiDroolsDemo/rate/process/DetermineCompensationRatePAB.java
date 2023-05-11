/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blazartech.MultiDroolsDemo.rate.process;

import com.blazartech.MultiDroolsDemo.comp.data.CompensationProduct;
import com.blazartech.MultiDroolsDemo.comp.data.CompensationProgram;
import com.blazartech.MultiDroolsDemo.comp.data.ThresholdType;
import com.blazartech.MultiDroolsDemo.rate.data.CompensationRate;

/**
 *
 * @author aar1069
 */
public interface DetermineCompensationRatePAB {
    
    public CompensationRate determineRate(CompensationProgram program, CompensationProduct product, ThresholdType thresholdType);
}
