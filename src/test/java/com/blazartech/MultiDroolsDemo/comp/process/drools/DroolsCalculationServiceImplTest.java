/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blazartech.MultiDroolsDemo.comp.process.drools;

import com.blazartech.MultiDroolsDemo.comp.data.CompensableEvent;
import com.blazartech.MultiDroolsDemo.comp.data.Product;
import com.blazartech.MultiDroolsDemo.comp.data.CompensationProgram;
import com.blazartech.MultiDroolsDemo.comp.data.EventAllocation;
import com.blazartech.MultiDroolsDemo.comp.data.CompensationRecord;
import com.blazartech.MultiDroolsDemo.comp.data.ThresholdType;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.kie.api.event.rule.AgendaEventListener;
import org.kie.api.event.rule.RuleRuntimeEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * @author aar1069
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {
    DroolsCalculationServiceImplTest.DroolsCalculationServiceImplTestConfiguration.class,
    DroolsCalculationSessionConfiguration.class
})
public class DroolsCalculationServiceImplTest {
    
    private static final Logger logger = LoggerFactory.getLogger(DroolsCalculationServiceImplTest.class);
    
    @Configuration
    public static class DroolsCalculationServiceImplTestConfiguration {
        
        @Bean
        public DroolsCalculationServiceImpl instance() {
            return new DroolsCalculationServiceImpl();
        }
        
        @Bean
        public AgendaEventListener agendaEventListener() {
            return new TrackingAgendaEventListener();
        }
        
        @Bean
        public RuleRuntimeEventListener updateListener() {
            return new ObjectAddUpdateEventListener();
        }
    }
    
    @Autowired
    private DroolsCalculationServiceImpl instance;
    
    public DroolsCalculationServiceImplTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of deriveCompensation method, of class DroolsCalculationServiceImpl.
     */
    @Test
    public void testDeriveCompensation_wholeLife_FYC() {
        logger.info("deriveCompensation");
        
        CompensableEvent compensableEvent = new CompensableEvent();
        compensableEvent.setId(100);
        compensableEvent.setPolicyNumber("TESTWL");
        compensableEvent.setProduct(Product.WholeLife);
        compensableEvent.setAmount(new BigDecimal("1000"));
        
        EventAllocation eventAllocation = new EventAllocation();
        eventAllocation.setCompensableEvent(compensableEvent);
        eventAllocation.setAgentNumber("DBS001");
        eventAllocation.setId(1000);
        eventAllocation.setProgram(CompensationProgram.FYC);
        
        Collection<CompensationRecord> result = instance.deriveCompensation(eventAllocation);
        assertNotNull(result);
        assertEquals(1, result.size());
        
        CompensationRecord payeeAllocation = result.iterator().next();
        assertEquals(0.5, payeeAllocation.getCompensationRate(), 0.01);
        
        assertNotNull(payeeAllocation.getAmount());
        assertEquals(500, payeeAllocation.getAmount().doubleValue(), 0.01);
    }
    
    @Test
    public void testDeriveCompensation_wholeLife_renewal() {
        logger.info("deriveCompensation_wholelife_renewal");
        
        CompensableEvent compensableEvent = new CompensableEvent();
        compensableEvent.setId(100);
        compensableEvent.setPolicyNumber("TESTWL");
        compensableEvent.setProduct(Product.WholeLife);
        compensableEvent.setAmount(new BigDecimal("1000"));
        
        EventAllocation eventAllocation = new EventAllocation();
        eventAllocation.setCompensableEvent(compensableEvent);
        eventAllocation.setAgentNumber("DBS001");
        eventAllocation.setId(1000);
        eventAllocation.setProgram(CompensationProgram.RenewalCommission);
        
        Collection<CompensationRecord> result = instance.deriveCompensation(eventAllocation);
        assertNotNull(result);
        assertEquals(1, result.size());
        
        CompensationRecord payeeAllocation = result.iterator().next();
        assertEquals(0.05, payeeAllocation.getCompensationRate(), 0.01);
        
        assertNotNull(payeeAllocation.getAmount());
        assertEquals(50, payeeAllocation.getAmount().doubleValue(), 0.01);
    }
    
    @Test
    public void testDeriveCompensation_annuity_FYC() {
        logger.info("deriveCompensation_annuity_fyc");
        
        CompensableEvent compensableEvent = new CompensableEvent();
        compensableEvent.setId(100);
        compensableEvent.setPolicyNumber("TESTWL");
        compensableEvent.setProduct(Product.VariableAnnuity);
        compensableEvent.setAmount(new BigDecimal("1000"));
        
        EventAllocation eventAllocation = new EventAllocation();
        eventAllocation.setCompensableEvent(compensableEvent);
        eventAllocation.setAgentNumber("DBS001");
        eventAllocation.setId(1000);
        eventAllocation.setProgram(CompensationProgram.FYC);
        
        Collection<CompensationRecord> result = instance.deriveCompensation(eventAllocation);
        assertNotNull(result);
        assertEquals(2, result.size());
        
        CompensationRecord payeeAllocation = result.iterator().next();
        assertEquals(0.1, payeeAllocation.getCompensationRate(), 0.01);
        
        assertNotNull(payeeAllocation.getAmount());
        assertEquals(100, payeeAllocation.getAmount().doubleValue(), 0.01);
    }
    
    @Test
    public void testDeriveCompensation_annuity_Renewal() {
        logger.info("deriveCompensation_annuity_renewal");
        
        CompensableEvent compensableEvent = new CompensableEvent();
        compensableEvent.setId(100);
        compensableEvent.setPolicyNumber("TESTWL");
        compensableEvent.setProduct(Product.VariableAnnuity);
        compensableEvent.setAmount(new BigDecimal("1000"));
        
        EventAllocation eventAllocation = new EventAllocation();
        eventAllocation.setCompensableEvent(compensableEvent);
        eventAllocation.setAgentNumber("DBS001");
        eventAllocation.setId(1000);
        eventAllocation.setProgram(CompensationProgram.RenewalCommission);
        
        Collection<CompensationRecord> result = instance.deriveCompensation(eventAllocation);
        assertNotNull(result);
        assertEquals(1, result.size());
        
        CompensationRecord payeeAllocation = result.iterator().next();
        assertEquals(0.01, payeeAllocation.getCompensationRate(), 0.01);
        
        assertNotNull(payeeAllocation.getAmount());
        assertEquals(10, payeeAllocation.getAmount().doubleValue(), 0.01);
    }
    
    @Test
    public void testDeriveCompensation_multiple() {
        logger.info("testDeriveCompensation_multiple");
        
        CompensableEvent compensableEvent1 = new CompensableEvent();
        compensableEvent1.setId(1001);
        compensableEvent1.setPolicyNumber("TESTWL");
        compensableEvent1.setProduct(Product.VariableAnnuity);
        compensableEvent1.setAmount(new BigDecimal("1000"));
        
        EventAllocation eventAllocation1 = new EventAllocation();
        eventAllocation1.setCompensableEvent(compensableEvent1);
        eventAllocation1.setAgentNumber("DBS001");
        eventAllocation1.setId(10001);
        eventAllocation1.setProgram(CompensationProgram.RenewalCommission);
        
        CompensableEvent compensableEvent2 = new CompensableEvent();
        compensableEvent2.setId(1002);
        compensableEvent2.setPolicyNumber("TESTWL");
        compensableEvent2.setProduct(Product.VariableAnnuity);
        compensableEvent2.setAmount(new BigDecimal("1000"));
        
        EventAllocation eventAllocation2 = new EventAllocation();
        eventAllocation2.setCompensableEvent(compensableEvent2);
        eventAllocation2.setAgentNumber("DBS001");
        eventAllocation2.setId(10002);
        eventAllocation2.setProgram(CompensationProgram.FYC);
        
        CompensableEvent compensableEvent3 = new CompensableEvent();
        compensableEvent3.setId(1003);
        compensableEvent3.setPolicyNumber("TESTWL");
        compensableEvent3.setProduct(Product.WholeLife);
        compensableEvent3.setAmount(new BigDecimal("1000"));
        
        EventAllocation eventAllocation3 = new EventAllocation();
        eventAllocation3.setCompensableEvent(compensableEvent3);
        eventAllocation3.setAgentNumber("DBS001");
        eventAllocation3.setId(10003);
        eventAllocation3.setProgram(CompensationProgram.FYC);
        
        List<EventAllocation> eventAllocations = List.of(eventAllocation1, eventAllocation2, eventAllocation3);
        
        Collection<CompensationRecord> result = instance.deriveCompensation(eventAllocations);
        assertNotNull(result);
        
        // there should be 4 comp results
        assertEquals(4, result.size());
        
        // all 3 compensable events should be present
        Set<CompensableEvent> paidCompensableEvents = new HashSet<>();
        result.stream()
                .map(a -> a.getEventAllocation().getCompensableEvent())
                .forEach(e -> paidCompensableEvents.add(e));
        assertEquals(3, paidCompensableEvents.size());
        
        // check the annuity FYC ones.  There should be 2
        Predicate<CompensationRecord> testFilter = a -> a.getEventAllocation().getCompensableEvent().getId() == compensableEvent2.getId();
        long annuityFYCAllocationCount = result.stream()
                .filter(testFilter)
                .collect(Collectors.counting());
        assertEquals(2, annuityFYCAllocationCount);
        
        // check the rate and amount for the annuity FYC
        result.stream()
                .filter(testFilter)
                .forEach(a -> {
                    if (a.getThresholdType() == ThresholdType.Below) {
                        assertEquals(0.1, a.getCompensationRate(), 0.01);
                        assertEquals(100, a.getAmount().doubleValue(), 0.01);
                    } else {
                        assertEquals(.05, a.getCompensationRate(), 0.01);
                        assertEquals(50, a.getAmount().doubleValue(), 0.01);
                    }
                });
    }
    
    @Test
    public void testDeriveCompensation_wholeLife_compensableEvent() {
        logger.info("deriveCompensation_wholeLife_compensableEvent");
        
        CompensableEvent compensableEvent = new CompensableEvent();
        compensableEvent.setId(100);
        compensableEvent.setPolicyNumber("TESTWL");
        compensableEvent.setProduct(Product.WholeLife);
        compensableEvent.setAmount(new BigDecimal("1000"));
                
        Collection<CompensationRecord> result = instance.deriveCompensationForCompensableEvent(compensableEvent);
        assertNotNull(result);
        
        // there should be two compensation events, one fyc and one renewal.
        assertEquals(2, result.size());
        
        // check the fyc record.
        CompensationRecord fycAllocation = null;
        for (CompensationRecord a : result) {
            if (a.getEventAllocation().getProgram() == CompensationProgram.FYC) {
                fycAllocation = a;
                break;
            }
        }
        assertNotNull(fycAllocation);
        assertEquals(0.5, fycAllocation.getCompensationRate(), 0.01);
        
        assertNotNull(fycAllocation.getAmount());
        assertEquals(500, fycAllocation.getAmount().doubleValue(), 0.01);
    }
    
    @Test
    public void testDeriveCompensation_multiple_compensableEvent() {
        logger.info("deriveCompensation_multiple_compensableEvent");
        
        CompensableEvent compensableEvent1 = new CompensableEvent();
        compensableEvent1.setId(100);
        compensableEvent1.setPolicyNumber("TESTWL");
        compensableEvent1.setProduct(Product.WholeLife);
        compensableEvent1.setAmount(new BigDecimal("1000"));
        
        CompensableEvent compensableEvent2 = new CompensableEvent();
        compensableEvent2.setId(1002);
        compensableEvent2.setPolicyNumber("TESTWL");
        compensableEvent2.setProduct(Product.VariableAnnuity);
        compensableEvent2.setAmount(new BigDecimal("1000"));
        
        List<CompensableEvent> compensableEvents = List.of(compensableEvent1, compensableEvent2);
                
        Collection<CompensationRecord> result = instance.deriveCompensationForCompensableEvent(compensableEvents);
        assertNotNull(result);
        
        // there should be 5 compensation events, fyc and renewal for the whole life, 
        // fyc above and below plus renewal for annuity.
        assertEquals(5, result.size());
        
        // check the fyc record.
        CompensationRecord fycAllocation = null;
        for (CompensationRecord a : result) {
            if (a.getEventAllocation().getProgram() == CompensationProgram.FYC && a.getEventAllocation().getCompensableEvent().getProduct() == Product.WholeLife) {
                fycAllocation = a;
                break;
            }
        }
        assertNotNull(fycAllocation);
        assertEquals(0.5, fycAllocation.getCompensationRate(), 0.01);
        
        assertNotNull(fycAllocation.getAmount());
        assertEquals(500, fycAllocation.getAmount().doubleValue(), 0.01);
    }
}
