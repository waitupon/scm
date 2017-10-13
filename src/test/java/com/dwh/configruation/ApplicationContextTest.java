package com.dwh.configruation;

import com.bean.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestBeanConfiguration.class)
public class ApplicationContextTest {
    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private Environment evn;

    @Test
    public void testNull(){
        Assert.assertNotNull(applicationContext.getBean(User.class));
        Assert.assertNotNull(applicationContext.getBean(Runnable.class));
    }


    @Test
    public void testEvn(){
        Assert.assertEquals("127.0.0.1",evn.getProperty("server.address"));
    }
}
