package com.bean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Product {
    private Integer id;
    private String name;
    private String remarks;

//    public Product( String name, String remarks) {
//        this.name = name;
//        this.remarks = remarks;
//    }


    public Product() {

    }

    public Product(Integer id, String name, String remarks) {
        this.id = id;
        this.name = name;
        this.remarks = remarks;
    }


    public  void say(){
        System.out.println("abc!!");
    }

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


    public static void main(String[] args) {

        List<Product> list = new ArrayList<Product>();

        list.add(new Product(1,"tom","123"));
        list.add(new Product(2,"tom4","1235"));
        list.add(new Product(3,"tom","1236"));

        List<String> collect = list.stream().map(x -> {
            if(x.getName().equals("tom")){
                return x.getName();
            }else {
                return null;
            }
        }).filter(x->x!=null).collect(Collectors.toList());
        System.out.println(collect);


    }
}
