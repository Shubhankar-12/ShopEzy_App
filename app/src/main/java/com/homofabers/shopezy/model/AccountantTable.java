package com.homofabers.shopezy.model;

public class AccountantTable {

    private int id;
    private String name;
    private String picDataText;

    public AccountantTable(String name, String picDataText) {
        this.name = name;
        this.picDataText = picDataText;
    }

    public AccountantTable(int id, String name, String picDataText) {
        this.id = id;
        this.name = name;
        this.picDataText = picDataText;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPicDataText() {
        return picDataText;
    }

    public void setPicDataText(String picDataText) {
        this.picDataText = picDataText;
    }
}
