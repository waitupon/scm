package com.event;

import org.springframework.context.ApplicationListener;

/**
 * Created by Administrator on 2017/9/28 0028.
 */
public class MyAppListener implements ApplicationListener<MyAppEvent> {
    public MyAppListener() {
    }

    @Override
    public void onApplicationEvent(MyAppEvent event) {
        System.out.println("-----------------");
    }
}
