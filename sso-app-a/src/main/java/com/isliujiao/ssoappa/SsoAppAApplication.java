package com.isliujiao.ssoappa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
public class SsoAppAApplication {

    public static void main(String[] args) {
        SpringApplication.run(SsoAppAApplication.class, args);
    }

}
