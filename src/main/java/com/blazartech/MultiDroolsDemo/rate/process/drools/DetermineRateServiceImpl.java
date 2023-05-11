/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blazartech.MultiDroolsDemo.rate.process.drools;

import com.blazartech.MultiDroolsDemo.comp.data.CompensationProduct;
import com.blazartech.MultiDroolsDemo.comp.data.CompensationProgram;
import com.blazartech.MultiDroolsDemo.comp.data.ThresholdType;
import com.blazartech.MultiDroolsDemo.rate.data.CompensationRate;
import jakarta.inject.Provider;
import org.kie.api.runtime.KieSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 *
 * @author aar1069
 */
@Component
public class DetermineRateServiceImpl implements DetermineRateService {

    private static final Logger logger = LoggerFactory.getLogger(DetermineRateServiceImpl.class);
    
    @Autowired
    @Qualifier("rateTableSession")
    private Provider<KieSession> rateTableProvider;
    
    public RateDeterminationData determineCompensationRate(CompensationProgram program, CompensationProduct product, ThresholdType thresholdType) {
        RateDeterminationData rateDeterminationData = new RateDeterminationData();
        rateDeterminationData.setProduct(product);
        rateDeterminationData.setProgram(program);
        rateDeterminationData.setThresholdType(thresholdType);
        
        KieSession kieSession = rateTableProvider.get();
        kieSession.insert(rateDeterminationData);
        kieSession.fireAllRules();
        kieSession.dispose();

        return rateDeterminationData;
    }

    @Override
    public CompensationRate determineRate(CompensationProgram program, CompensationProduct product, ThresholdType thresholdType) {
        
        RateDeterminationData rateDetermination = determineCompensationRate(program, product, thresholdType);
        
        CompensationRate rate = new CompensationRate();
        rate.setProduct(product);
        rate.setProgram(program);
        rate.setThresholdType(thresholdType);
        rate.setRate(rateDetermination.getRate());
        
        return rate;
    }

}
