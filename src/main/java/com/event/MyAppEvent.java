package com.event;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

/**
 * Created by Administrator on 2017/9/28 0028.
 */
public class MyAppEvent extends ApplicationEvent {

    /**
     * Create a new ApplicationEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     */
    public MyAppEvent(Object source) {
        super(source);
    }
}
