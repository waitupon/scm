package com.mybatis;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application.properties")
public class DataSourceConfiguration {
    @Value("${spring.datasource.driver-class-name}")
    private String driver;
    @Value("${spring.datasource.url}")
    private String url;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;

    public BasicDataSource datasource(){
        BasicDataSource dataSoure = new BasicDataSource();
        dataSoure.setDriverClassName(driver);
        dataSoure.setUrl(url);
        dataSoure.setUsername(username);
        dataSoure.setPassword(password);
        return dataSoure;
    }
}
