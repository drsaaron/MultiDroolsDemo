/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blazartech.MultiDroolsDemo.comp.process.drools;

import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.KieModule;
import org.kie.api.event.rule.AgendaEventListener;
import org.kie.api.event.rule.RuleRuntimeEventListener;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.internal.io.ResourceFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 *
 * @author aar1069
 */
@Configuration
@EnableAsync
public class DroolsCalculationSessionConfiguration {
    
    private static final String CALCULATION_FILE = "Calculation.drl";
    private static final String ELIG_CALC_FILE = "EligibleToCalc.drl";
    private static final String INIT_ALLOCATIONS_FILE = "InitializePayeeAllocations.drl";
    private static final String INIT_EVENT_ALLOCATION_FILE = "InitializeEventAllocations.drl";
    private static final String DETERMINE_RATE_FILE = "DetermineCompensationRate.drl.xlsx";
    
    private static final String[] DRL_FILES = { ELIG_CALC_FILE, CALCULATION_FILE, INIT_ALLOCATIONS_FILE, INIT_EVENT_ALLOCATION_FILE, DETERMINE_RATE_FILE };
 
    @Bean("compContainer")
    public KieContainer kieContainer() {
        KieServices kieServices = KieServices.Factory.get();
 
        KieFileSystem kieFileSystem = kieServices.newKieFileSystem();
        for (String drlFile : DRL_FILES) {
            kieFileSystem.write(ResourceFactory.newClassPathResource(getClass().getPackage().getName().replace('.', '/') + "/" + drlFile));
        }
        KieBuilder kieBuilder = kieServices.newKieBuilder(kieFileSystem);
        kieBuilder.buildAll();
        KieModule kieModule = kieBuilder.getKieModule();
 
        return kieServices.newKieContainer(kieModule.getReleaseId());
    }
    
    @Autowired
    private RuleRuntimeEventListener updateListener;
    
    @Autowired
    private AgendaEventListener agendaEventListener;
    
    @Bean("compSession")
    @Scope("prototype")
    public KieSession kieSession(@Qualifier("compContainer") KieContainer container) {
        KieSession session = container.newKieSession();
        session.addEventListener(agendaEventListener);
        session.addEventListener(updateListener);
        return session;
    }
}
