package com.homofabers.shopezy.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class ItemData implements Serializable {
    private String id;
    private String name;
    private float SP;
    private int quantity;
    private int invoiceId;
    private LocalDateTime dateSold;

    public ItemData(){}
    public ItemData(String id, String name, float SP, int quantity) {
        this.id = id;
        this.name = name;
        this.SP = SP;
        this.quantity = quantity;
    }

    public int getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(int invoiceId) {
        this.invoiceId = invoiceId;
    }

    public LocalDateTime getDateSold() {
        return dateSold;
    }

    public void setDateSold(LocalDateTime dateSold) {
        this.dateSold = dateSold;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getSP() {
        return SP;
    }

    public void setSP(float SP) {
        this.SP = SP;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
