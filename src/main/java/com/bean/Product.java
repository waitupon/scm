package com.bean;

public class Product {
    private Integer id;
    private String name;
    private String remarks;

//    public Product( String name, String remarks) {
//        this.name = name;
//        this.remarks = remarks;
//    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
