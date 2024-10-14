package com.isliujiao.ssoappb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
public class SsoAppBApplication {

    public static void main(String[] args) {
        SpringApplication.run(SsoAppBApplication.class, args);
    }

}
