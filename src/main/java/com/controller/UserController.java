package com.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.xml.ws.RequestWrapper;

/**
 * Created by Administrator on 2017/9/29 0029.
 */
@Controller
public class UserController {


    @RequestMapping("/login")
    public String login(){
        return "/login";
    }



}
