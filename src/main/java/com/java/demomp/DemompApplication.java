package com.java.demomp;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.java.demomp.sys.mapper")
public class DemompApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemompApplication.class, args);
    }

}
