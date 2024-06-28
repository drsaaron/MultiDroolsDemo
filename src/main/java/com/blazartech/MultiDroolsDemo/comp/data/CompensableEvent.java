/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blazartech.MultiDroolsDemo.comp.data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author aar1069
 */
public class CompensableEvent implements Serializable, AmountObject {
    
    private long id;
    private String policyNumber;
    private BigDecimal amount;
    private Product product;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPolicyNumber() {
        return policyNumber;
    }

    public void setPolicyNumber(String productContractNumber) {
        this.policyNumber = productContractNumber;
    }

    @Override
    public BigDecimal getAmount() {
        return amount;
    }

    @Override
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "CompensableEvent{" + "id=" + id + ", policyNumber=" + policyNumber + ", amount=" + amount + ", product=" + product + '}';
    }
    
    
}
