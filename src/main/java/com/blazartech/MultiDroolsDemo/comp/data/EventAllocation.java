/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blazartech.MultiDroolsDemo.comp.data;

import java.io.Serializable;

/**
 *
 * @author aar1069
 */
public class EventAllocation implements Serializable {
    
    private long id;
    private String distributorNumber;
    private CompensationProgram program;
    private CompensableEvent compensableEvent;
    private boolean eligibleToCalc = false;

    public boolean isEligibleToCalc() {
        return eligibleToCalc;
    }

    public void setEligibleToCalc(boolean eligibleToCalc) {
        this.eligibleToCalc = eligibleToCalc;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDistributorNumber() {
        return distributorNumber;
    }

    public void setDistributorNumber(String distributorNumber) {
        this.distributorNumber = distributorNumber;
    }

    public CompensationProgram getProgram() {
        return program;
    }

    public void setProgram(CompensationProgram program) {
        this.program = program;
    }

    public CompensableEvent getCompensableEvent() {
        return compensableEvent;
    }

    public void setCompensableEvent(CompensableEvent compensableEvent) {
        this.compensableEvent = compensableEvent;
    }

    @Override
    public String toString() {
        return "EventAllocation{" + "id=" + id + ", distributorNumber=" + distributorNumber + ", program=" + program + ", compensableEvent=" + compensableEvent + ", eligibleToCalc=" + eligibleToCalc + '}';
    }
    
    
}
