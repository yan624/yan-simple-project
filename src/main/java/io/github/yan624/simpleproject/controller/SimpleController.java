package io.github.yan624.simpleproject.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Zhu Chongyan
 * @program: yan-simple-project
 * @Date: 2019/01/06 13:54
 */
@RestController
@RequestMapping("/")
public class SimpleController {

    @GetMapping(value = "/demo", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String simple(){
        return "一个简单栗子";
    }
}
