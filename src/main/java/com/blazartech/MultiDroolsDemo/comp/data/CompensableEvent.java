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
    private String productContractNumber;
    private BigDecimal amount;
    private CompensationProduct product;

    public CompensationProduct getProduct() {
        return product;
    }

    public void setProduct(CompensationProduct product) {
        this.product = product;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getProductContractNumber() {
        return productContractNumber;
    }

    public void setProductContractNumber(String productContractNumber) {
        this.productContractNumber = productContractNumber;
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
        return "CompensableEvent{" + "id=" + id + ", productContractNumber=" + productContractNumber + ", amount=" + amount + ", product=" + product + '}';
    }
    
    
}
