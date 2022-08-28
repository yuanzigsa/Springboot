package com.yuanzi.springbootvue_demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class SpringbootvueDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootvueDemoApplication.class, args);
    }

    @GetMapping("/")
    public String index(){
        return "欢迎来到元子的第一个页面";
    }

}
