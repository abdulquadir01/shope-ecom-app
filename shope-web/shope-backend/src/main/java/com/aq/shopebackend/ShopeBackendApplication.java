package com.aq.shopebackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;



@SpringBootApplication
@EntityScan({"com.aq.shopecommon.entity", "com.aq.shopebackend.user"})
public class ShopeBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShopeBackendApplication.class, args);
    }

}
