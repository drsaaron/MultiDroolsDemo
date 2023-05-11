/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blazartech.MultiDroolsDemo.comp.process.drools;

import com.blazartech.MultiDroolsDemo.comp.data.CompensableEvent;
import com.blazartech.MultiDroolsDemo.comp.data.EventAllocation;
import com.blazartech.MultiDroolsDemo.comp.data.PayeeAllocation;
import java.util.Collection;

/**
 *
 * @author aar1069
 */
public interface DroolsCalculationService {
    
    public Collection<PayeeAllocation> deriveCompensation(EventAllocation eventAllocation);
    
    public Collection<PayeeAllocation> deriveCompensation(Collection<EventAllocation> eventAllocations);
    
    public Collection<PayeeAllocation> deriveCompensationForCompensableEvent(CompensableEvent compensableEvent);
    
    public Collection<PayeeAllocation> deriveCompensationForCompensableEvent(Collection<CompensableEvent> compensableEvents); 
}
