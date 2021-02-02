package com.udemy.egitim;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

//@ServletComponentScan
@SpringBootApplication
public class EgitimApplication {
    public static void main(String[] args) {
        SpringApplication.run(EgitimApplication.class,args);
    }
}
