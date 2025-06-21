package com.reservation.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@ConfigurationPropertiesScan(basePackages = {"com.reservation.api", "com.reservation.authentication", "com.reservation.common"})
@SpringBootApplication(scanBasePackages = {"com.reservation.api", "com.reservation.authentication", "com.reservation.common"})
public class ApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiApplication.class, args);
    }

}
