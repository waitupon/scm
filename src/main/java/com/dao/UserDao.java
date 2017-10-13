package com.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by Administrator on 2017/10/2 0002.
 */
@Component
public class UserDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Transactional
    public void add(String... names) throws IOException {
            for (int i=0;i<names.length;i++){
                /*jdbcTemplate.execute("insert into base_user(name) values('"+names[i]+"')");
                if(i==0){
                    throw new IOException();
                }*/
                System.out.println(names[i]);
            }

    }
}
