/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blazartech.MultiDroolsDemo.comp.process.drools;

import org.kie.api.definition.rule.Rule;
import org.kie.api.event.rule.DefaultRuleRuntimeEventListener;
import org.kie.api.event.rule.ObjectDeletedEvent;
import org.kie.api.event.rule.ObjectInsertedEvent;
import org.kie.api.event.rule.ObjectUpdatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 *
 * @author aar1069
 */
@Component
public class ObjectAddUpdateEventListener extends DefaultRuleRuntimeEventListener {
    
    private static final Logger logger = LoggerFactory.getLogger(ObjectAddUpdateEventListener.class);

    @Override
    public void objectUpdated(ObjectUpdatedEvent event) {
        Rule rule = event.getRule();
        String ruleName = (rule != null) ? rule.getName() : "<unknown>";
        Object object = event.getObject();
        super.objectUpdated(event); //To change body of generated methods, choose Tools | Templates.
        
        logger.debug("object " + object + " updated by rule " + ruleName);
    }

    @Override
    public void objectDeleted(ObjectDeletedEvent event) {
        Rule rule = event.getRule();
        String ruleName = (rule != null) ? rule.getName() : "<unknown>";
        Object object = event.getOldObject();
        super.objectDeleted(event); //To change body of generated methods, choose Tools | Templates.
        
        logger.debug("object " + object + " deleted by rule " + ruleName);
    }

    @Override
    public void objectInserted(ObjectInsertedEvent event) {
        Rule rule = event.getRule();
        String ruleName = (rule != null) ? rule.getName() : "<unknown>";
        Object object = event.getObject();
        super.objectInserted(event); //To change body of generated methods, choose Tools | Templates.
        
        logger.debug("object " + object + " inserted by rule " + ruleName);
    }
    
    
}
