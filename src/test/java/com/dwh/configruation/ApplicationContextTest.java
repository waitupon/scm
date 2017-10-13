package com.dwh.configruation;

import com.bean.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestBeanConfiguration.class)
public class ApplicationContextTest {
    @Autowired
    private ApplicationContext applicationContext;

    @Test
    public void testNull(){
        Assert.assertNotNull(applicationContext.getBean(User.class));
        Assert.assertNotNull(applicationContext.getBean(Runnable.class));
    }
}
