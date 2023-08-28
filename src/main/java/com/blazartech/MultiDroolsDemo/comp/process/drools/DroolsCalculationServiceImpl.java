/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blazartech.MultiDroolsDemo.comp.process.drools;

import com.blazartech.MultiDroolsDemo.comp.data.CompensableEvent;
import com.blazartech.MultiDroolsDemo.comp.data.EventAllocation;
import com.blazartech.MultiDroolsDemo.comp.data.PayeeAllocation;
import jakarta.inject.Provider;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import org.kie.api.runtime.KieSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 *
 * @author aar1069
 */
@Service
public class DroolsCalculationServiceImpl implements DroolsCalculationService {

    private static final Logger logger = LoggerFactory.getLogger(DroolsCalculationServiceImpl.class);

    private Collection<PayeeAllocation> getPayeeAllocations(KieSession session) {
        /*
        The rules create payee allocations as needed.  Get the generated payee
        allocation objects, sort by threshold type (to facilitate evaluating results
        in unit tests, and return the list.
         */
        List<PayeeAllocation> results = session.getObjects(o -> o instanceof PayeeAllocation).stream()
                .map(o -> (PayeeAllocation) o)
                .sorted((a1, a2) -> a1.getThresholdType().compareTo(a2.getThresholdType()))
                .collect(Collectors.toList());

        return results;
    }

    @Autowired
    @Qualifier("compSession")
    private Provider<KieSession> sessionProvider;

    @Override
    public Collection<PayeeAllocation> deriveCompensation(EventAllocation eventAllocation) {
        logger.info("running drools rules for " + eventAllocation);

        KieSession kieSession = sessionProvider.get();
        kieSession.insert(eventAllocation);
        kieSession.fireAllRules();

        Collection<PayeeAllocation> allocations = getPayeeAllocations(kieSession);

        kieSession.dispose();

        return allocations;
    }

    @Override
    public Collection<PayeeAllocation> deriveCompensation(Collection<EventAllocation> eventAllocations) {

        KieSession kieSession = sessionProvider.get();
        eventAllocations.forEach(a -> kieSession.insert(a));
        kieSession.fireAllRules();

        Collection<PayeeAllocation> allocations = getPayeeAllocations(kieSession);

        kieSession.dispose();

        return allocations;
    }

    @Override
    public Collection<PayeeAllocation> deriveCompensationForCompensableEvent(CompensableEvent compensableEvent) {
        logger.debug("deriving compensation for " + compensableEvent);

        KieSession kieSession = sessionProvider.get();
        kieSession.insert(compensableEvent);
        kieSession.fireAllRules();

        Collection<PayeeAllocation> allocations = getPayeeAllocations(kieSession);

        kieSession.dispose();
        
        return allocations;
    }

    @Override
    public Collection<PayeeAllocation> deriveCompensationForCompensableEvent(Collection<CompensableEvent> compensableEvents) {

        KieSession kieSession = sessionProvider.get();
        compensableEvents.forEach(e -> kieSession.insert(e));
        kieSession.fireAllRules();

        Collection<PayeeAllocation> allocations = getPayeeAllocations(kieSession);
        
        kieSession.dispose();
        
        return allocations;
    }
}
