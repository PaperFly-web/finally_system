package com.cslg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CslgApplicaton8001 {
    public static void main(String[] args) {
        SpringApplication.run(CslgApplicaton8001.class,args);
    }
}
