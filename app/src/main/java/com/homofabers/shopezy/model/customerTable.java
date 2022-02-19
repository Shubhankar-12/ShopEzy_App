package com.homofabers.shopezy.model;

public class customerTable {
    private int cust_id;
    private String cust_name;
    private String cust_email;
    private String customer_phone;

    public customerTable() {
    }

    public customerTable(int cust_id, String cust_name, String cust_email, String customer_phone) {
        this.cust_id = cust_id;
        this.cust_name = cust_name;
        this.cust_email = cust_email;
        this.customer_phone = customer_phone;
    }

    public int getCust_id() {
        return cust_id;
    }

    public void setCust_id(int cust_id) {
        this.cust_id = cust_id;
    }

    public String getCust_name() {
        return cust_name;
    }

    public void setCust_name(String cust_name) {
        this.cust_name = cust_name;
    }

    public String getCust_email() {
        return cust_email;
    }

    public void setCust_email(String cust_email) {
        this.cust_email = cust_email;
    }

    public String getCustomer_phone() {
        return customer_phone;
    }

    public void setCustomer_phone(String customer_phone) {
        this.customer_phone = customer_phone;
    }
}
