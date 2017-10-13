package com.condition;


import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * Created by Administrator on 2017/9/27 0027.
 */

public class JBKCondition implements Condition {
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        String character = context.getEnvironment().getProperty("system.character");
       // String character = System.getProperty("system.character");
        if("GBK".equals(character)){
            return true;
        }
        return false;
    }
}
