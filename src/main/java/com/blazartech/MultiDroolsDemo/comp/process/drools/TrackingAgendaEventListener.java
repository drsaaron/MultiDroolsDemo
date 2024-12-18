/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blazartech.MultiDroolsDemo.comp.process.drools;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.drools.core.event.DefaultAgendaEventListener;
import org.kie.api.definition.rule.Rule;
import org.kie.api.event.rule.AfterMatchFiredEvent;
import org.kie.api.runtime.rule.Match;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * from https://stackoverflow.com/questions/19951880/drools-how-to-find-out-which-all-rules-were-matched
 * @author aar1069
 */
@Component
public class TrackingAgendaEventListener extends DefaultAgendaEventListener {
    
    private static final Logger log = LoggerFactory.getLogger(TrackingAgendaEventListener.class);

    private final List<Match> matchList = new ArrayList<>();

    @Override
    public void afterMatchFired(AfterMatchFiredEvent event) {
        Rule rule = event.getMatch().getRule();

        String ruleName = rule.getName();
        Map<String, Object> ruleMetaDataMap = rule.getMetaData();

        matchList.add(event.getMatch());
        StringBuilder sb = new StringBuilder("Rule fired: " + ruleName);

        if (!ruleMetaDataMap.isEmpty()) {
            sb.append("\n  With [").append(ruleMetaDataMap.size()).append("] meta-data:");
            ruleMetaDataMap.keySet().forEach(key -> {
                sb.append("\n    key=").append(key).append(", value=").append(ruleMetaDataMap.get(key));
            });
        }

        log.info(sb.toString());
    }

    public boolean isRuleFired(String ruleName) {
        return matchList.stream().anyMatch(a -> (a.getRule().getName().equals(ruleName)));
    }

    public void reset() {
        matchList.clear();
    }

    public final List<Match> getMatchList() {
        return matchList;
    }

    public String matchsToString() {
        if (matchList.isEmpty()) {
            return "No matchs occurred.";
        } else {
            StringBuilder sb = new StringBuilder("Matchs: ");
            matchList.forEach(match -> {
                sb.append("\n  rule: ").append(match.getRule().getName());
            });
            return sb.toString();
        }
    }

}
