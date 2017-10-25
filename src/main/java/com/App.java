package com;

import com.bean.Apple;
import com.bean.Product;
import com.bean.User;
import com.condition.JBKCondition;
import com.dao.ProductDao;
import com.dao.UserDao;
import com.event.MyAppEvent;
import com.event.MyAppListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.io.IOException;


/**
 * Created by Administrator on 2017/9/26 0026.
 */
@SpringBootApplication
@EnableTransactionManagement
public class App {

//    @Bean
//    public Runnable getRunnable (){
//        return ()-> System.out.println("hah");
//    }

    @Bean
    @Conditional(JBKCondition.class)
    public Apple createApple(){
        return new Apple();
    }

    public static void main(String[] args) throws IOException {
        ConfigurableApplicationContext ctx = SpringApplication.run(App.class,args);
/*        ctx.addApplicationListener(new MyAppListener());
        System.out.println(ctx.getBean(User.class));
        System.out.println(ctx.getBean(Apple.class));
        ctx.getBean(Runnable.class).run();
        ctx.publishEvent(new MyAppEvent(new Object()));*/
//        ProductDao productDao = ctx.getBean(ProductDao.class);
//        productDao.insert(new Product("p1","哈哈"));
    }
}
