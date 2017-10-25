package com.dao;

import com.bean.Product;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ProductDao {
        @Select("select * from product where id = #{id}")
        public Product getProduct(String id);

        @Insert("insert into product(name,remarks) values (#{name},#{remarks}) ")
        public void insert(Product product);
}
