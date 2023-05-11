/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blazartech.MultiDroolsDemo.compprocess;

import com.blazartech.MultiDroolsDemo.comp.data.CompensableEvent;
import com.blazartech.MultiDroolsDemo.comp.data.EventAllocation;
import com.blazartech.MultiDroolsDemo.comp.data.PayeeAllocation;
import com.blazartech.MultiDroolsDemo.comp.process.drools.DroolsCalculationService;
import java.util.Collection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author aar1069
 */
@Service
public class CompensationCalculationPABDroolsImpl implements CompensationCalculationPAB {

    private static final Logger logger = LoggerFactory.getLogger(CompensationCalculationPABDroolsImpl.class);
    
    @Autowired
    private DroolsCalculationService calculationService;
    
    @Override
    public Collection<PayeeAllocation> calculateCompensation(CompensableEvent compensableEvent, EventAllocation eventAllocation) {
        logger.info("calculating compensation for event allocation " + eventAllocation);
        
        return calculationService.deriveCompensation(eventAllocation);
    }
    
}
