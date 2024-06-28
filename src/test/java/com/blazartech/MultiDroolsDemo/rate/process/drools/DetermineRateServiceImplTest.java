/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blazartech.MultiDroolsDemo.rate.process.drools;

import com.blazartech.MultiDroolsDemo.comp.data.Product;
import com.blazartech.MultiDroolsDemo.comp.data.CompensationProgram;
import com.blazartech.MultiDroolsDemo.comp.data.ThresholdType;
import com.blazartech.MultiDroolsDemo.comp.process.drools.ObjectAddUpdateEventListener;
import com.blazartech.MultiDroolsDemo.comp.process.drools.TrackingAgendaEventListener;
import com.blazartech.MultiDroolsDemo.rate.data.CompensationRate;
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
    DetermineRateServiceImplTest.DetermineRateServiceImplTestConfiguration.class,
    DroolsRateDeterminationConfiguration.class
})
public class DetermineRateServiceImplTest {
    
    private static final Logger logger = LoggerFactory.getLogger(DetermineRateServiceImplTest.class);
    
    @Configuration
    public static class DetermineRateServiceImplTestConfiguration {
        
        @Bean
        public DetermineRateServiceImpl instance() {
            return new DetermineRateServiceImpl();
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
    private DetermineRateServiceImpl instance;
    
    public DetermineRateServiceImplTest() {
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
     * Test of getRateDeterminationData method, of class DetermineRateServiceImpl.
     */
    @Test
    public void testDetermineRateTable_wholeLife() {
        logger.info("determineRateTable");
        
        CompensationProgram program = CompensationProgram.FYC;
        Product product = Product.WholeLife;
        ThresholdType thresholdType = ThresholdType.Below;

        RateDeterminationData result = instance.determineCompensationRate(program, product, thresholdType);
        
        assertNotNull(result);
        assertEquals(RateTableType.WholeLife, result.getRateTable());
        assertEquals(0.5, result.getRate(), 0.01);
    }
    
    @Test
    public void testDetermineRateTable_annuityThreshold() {
        logger.info("determineRateTable_annuityThreshold");
        
        CompensationProgram program = CompensationProgram.FYC;
        Product product = Product.VariableAnnuity;
        ThresholdType thresholdType = ThresholdType.Below;

        RateDeterminationData result = instance.determineCompensationRate(program, product, thresholdType);
        
        assertNotNull(result);
        assertEquals(RateTableType.AnnuityThreshold, result.getRateTable());
        assertEquals(0.4, result.getRate(), 0.01);
    }

    @Test
    public void testDetermineRateTable_annuityRenewal() {
        logger.info("determineRateTable_annuityThreshold");
        
        CompensationProgram program = CompensationProgram.RenewalCommission;
        Product product = Product.VariableAnnuity;
        ThresholdType thresholdType = ThresholdType.Below;

        RateDeterminationData result = instance.determineCompensationRate(program, product, thresholdType);
        
        assertNotNull(result);
        assertEquals(RateTableType.AnnuityPlain, result.getRateTable());
        assertEquals(0.03, result.getRate(), 0.001);
    }

    /**
     * Test of determineRate method, of class DetermineRateServiceImpl.
     */
    @Test
    public void testDetermineRate() {
        logger.info("determineRate");
        
        CompensationProgram program = CompensationProgram.FYC;
        Product product = Product.WholeLife;
        ThresholdType thresholdType = ThresholdType.Below;

        CompensationRate result = instance.determineRate(program, product, thresholdType);

        assertNotNull(result);
        assertEquals(0.5, result.getRate(), 0.01);
    }
    
}
