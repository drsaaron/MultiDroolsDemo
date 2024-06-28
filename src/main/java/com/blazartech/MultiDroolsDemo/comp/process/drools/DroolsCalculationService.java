/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blazartech.MultiDroolsDemo.comp.process.drools;

import com.blazartech.MultiDroolsDemo.comp.data.CompensableEvent;
import com.blazartech.MultiDroolsDemo.comp.data.EventAllocation;
import com.blazartech.MultiDroolsDemo.comp.data.CompensationRecord;
import java.util.Collection;

/**
 *
 * @author aar1069
 */
public interface DroolsCalculationService {
    
    public Collection<CompensationRecord> deriveCompensation(EventAllocation eventAllocation);
    
    public Collection<CompensationRecord> deriveCompensation(Collection<EventAllocation> eventAllocations);
    
    public Collection<CompensationRecord> deriveCompensationForCompensableEvent(CompensableEvent compensableEvent);
    
    public Collection<CompensationRecord> deriveCompensationForCompensableEvent(Collection<CompensableEvent> compensableEvents); 
}
