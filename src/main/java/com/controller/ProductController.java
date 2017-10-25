package com.controller;

import com.bean.Product;
import com.dao.ProductDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Administrator on 2017/9/29 0029.
 */
@Controller
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductDao productDao;

    @RequestMapping("/get/{id}")
    @ResponseBody
    public Object get(@PathVariable("id")String id){
        return productDao.getProduct(id);
    }



}
