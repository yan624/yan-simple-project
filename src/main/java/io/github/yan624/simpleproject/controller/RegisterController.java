package io.github.yan624.simpleproject.controller;

import io.github.yan624.common.base.controller.BaseController;
import io.github.yan624.simpleproject.config.properties.SystemConfigProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.io.*;

/**
 * @Author: Zhu Chongyan
 * @program: simple project
 * @Date: 2019/03/27 16:30
 */
@RestController
@RequestMapping("/")
@Slf4j
public class RegisterController extends BaseController {

    @Autowired
    private SystemConfigProperties systemConfigProperties;

    @GetMapping("register.html")
    public ModelAndView showRegister(){
        return new ModelAndView(thymeleaf("register"));
    }

    @PostMapping("register")
    public ModelAndView register(String account, String password){
        return new ModelAndView(redirect("login.html"));
    }
}
