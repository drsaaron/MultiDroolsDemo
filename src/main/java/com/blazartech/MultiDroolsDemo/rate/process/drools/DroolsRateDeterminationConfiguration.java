/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blazartech.MultiDroolsDemo.rate.process.drools;

import java.util.List;
import java.util.Properties;
import org.drools.decisiontable.DecisionTableProviderImpl;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.KieModule;
import org.kie.api.event.rule.AgendaEventListener;
import org.kie.api.event.rule.RuleRuntimeEventListener;
import org.kie.api.io.Resource;
import org.kie.api.io.ResourceConfiguration;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.internal.builder.DecisionTableConfiguration;
import org.kie.internal.builder.DecisionTableInputType;
import org.kie.internal.builder.RuleTemplateConfiguration;
import org.kie.internal.io.ResourceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 *
 * @author aar1069
 */
@Configuration
public class DroolsRateDeterminationConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(DroolsRateDeterminationConfiguration.class);
    
    private static final String DETERMINE_RATE_TABLE = "DetermineRateTable.drl.xlsx";
    private static final String WHOLE_LIFE_RATE_TABLE = "WholeLifeRateTable.drl.xlsx";
    private static final String ANNUITY_THRESHOLD_RATE_TABLE = "AnnuityThresholdRateTable.drl.xlsx";

    private static final String[] RATE_TABLES = {DETERMINE_RATE_TABLE, WHOLE_LIFE_RATE_TABLE, ANNUITY_THRESHOLD_RATE_TABLE};

    private void logRules(String fileName) {
        Resource dt = ResourceFactory.newClassPathResource(getClass().getPackage().getName().replace('.', '/') + "/" + fileName);

        DecisionTableProviderImpl decisionTableProvider = new DecisionTableProviderImpl();
        DecisionTableConfiguration providerConfig = new DecisionTableConfiguration() {
            @Override
            public void setInputType(DecisionTableInputType dtit) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public DecisionTableInputType getInputType() {
                return DecisionTableInputType.XLSX;
            }

            @Override
            public void setWorksheetName(String string) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public String getWorksheetName() {
                return "Sheet1";
            }

            @Override
            public void addRuleTemplateConfiguration(Resource rsrc, int i, int i1) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public List<RuleTemplateConfiguration> getRuleTemplateConfigurations() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public boolean isTrimCell() {
                return false;
            }

            @Override
            public void setTrimCell(boolean bln) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public Properties toProperties() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public ResourceConfiguration fromProperties(Properties prprts) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
            
        };

        String drl = decisionTableProvider.loadFromResource(dt, providerConfig);
        logger.info("drl for determine rate: " + drl);
    }
    
    @Bean("rateTableContainer")
    public KieContainer rateTableContainer() {
        
        // log one of the rules from a rate table, for giggles.
        for (String ruleTable : RATE_TABLES) {
            logRules(ruleTable);
        }
        
        // now build the beans.
        KieServices kieServices = KieServices.Factory.get();

        KieFileSystem kieFileSystem = kieServices.newKieFileSystem();
        for (String drlFile : RATE_TABLES) {
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

    @Bean("rateTableSession")
    @Scope("prototype")
    public KieSession rateTableSession(@Qualifier("rateTableContainer") KieContainer container) {
        KieSession session = container.newKieSession();
        session.addEventListener(agendaEventListener);
        session.addEventListener(updateListener);
        return session;
    }
}
