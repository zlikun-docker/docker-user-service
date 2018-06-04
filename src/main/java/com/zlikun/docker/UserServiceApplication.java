package com.zlikun.docker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//生产环境中，使用Docker来配置环境变量实现动态配置
//本例调试时，在运行命令中手动添加环境变量即可
//@PropertySource("classpath:config.properties")
public class UserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }
}
