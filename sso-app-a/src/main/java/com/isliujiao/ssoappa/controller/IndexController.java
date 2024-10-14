package com.isliujiao.ssoappa.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试A
 */
@RestController
public class IndexController {

    @GetMapping("/testA")
    public String testA() {
        return "输出testA";
    }
}
