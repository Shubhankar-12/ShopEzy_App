package com.homofabers.shopezy.model;

import java.time.LocalDateTime;

public class stockItem {

    private String barcode;
    private String product_name;
    private float CP;
    private float SP;
    private float quantity;
    private float MRP;
    private LocalDateTime dateUpdated;

    public stockItem(){
    }

    public stockItem(String barcode, String product_name, float CP, float SP,
                     float quantity, float MRP, LocalDateTime dateUpdated) {
        this.barcode = barcode;
        this.product_name = product_name;
        this.CP = CP;
        this.SP = SP;
        this.quantity = quantity;
        this.MRP = MRP;
        this.dateUpdated = dateUpdated;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public float getCP() {
        return CP;
    }

    public void setCP(float CP) {
        this.CP = CP;
    }

    public float getSP() {
        return SP;
    }

    public void setSP(float SP) {
        this.SP = SP;
    }

    public float getQuantity() {
        return quantity;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    public float getMRP() {
        return MRP;
    }

    public void setMRP(float MRP) {
        this.MRP = MRP;
    }

    public LocalDateTime getdateUpdated() {
        return dateUpdated;
    }

    public void setdateUpdated(LocalDateTime dateUpdated) {
        this.dateUpdated = dateUpdated;
    }
}
