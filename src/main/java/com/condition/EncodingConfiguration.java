package com.condition;

/**
 * Created by Administrator on 2017/9/27 0027.
 */

import com.bean.Apple;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;

@SpringBootConfiguration
public class EncodingConfiguration {

    @Bean
    @Conditional(JBKCondition.class)
    public Apple createApple(){
        return new Apple();
    }
}
