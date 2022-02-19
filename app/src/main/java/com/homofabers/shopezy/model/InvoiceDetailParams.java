package com.homofabers.shopezy.model;

import java.time.LocalDateTime;

public class InvoiceDetailParams {
    private int id;
    private int custom_id;
    private int total_amount;
    private String pay_mode;
    private int accountant;
    private int total_items;
    private LocalDateTime dateTime;
    private String accountant_name;
    private String custom_name;


    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public InvoiceDetailParams(){
    }

    public InvoiceDetailParams(int custom_id,
                               int total_amount, String pay_mode, int accountant,
                               int total_items , String custom_name, String accountant_name) {
        this.custom_id = custom_id;
        this.total_amount = total_amount;
        this.pay_mode = pay_mode;
        this.accountant = accountant;
        this.total_items = total_items;
        this.custom_name=custom_name;
        this.accountant_name = accountant_name;


    }

    public String getCustom_name() {
        return custom_name;
    }

    public void setCustom_name(String custom_name) {
        this.custom_name = custom_name;
    }

    public String getAccountant_name() {
        return accountant_name;
    }

    public void setAccountant_name(String accountant_name) {
        this.accountant_name = accountant_name;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCustom_id() {
        return custom_id;
    }

    public void setCustom_id(int custom_id) {
        this.custom_id = custom_id;
    }

    public int getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(int total_amount) {
        this.total_amount = total_amount;
    }

    public String getPay_mode() {
        return pay_mode;
    }

    public void setPay_mode(String pay_mode) {
        this.pay_mode = pay_mode;
    }

    public int getAccountant() {
        return accountant;
    }

    public void setAccountant(int accountant) {
        this.accountant = accountant;
    }

    public int getTotal_items() {
        return total_items;
    }

    public void setTotal_items(int total_items) {
        this.total_items = total_items;
    }
}
