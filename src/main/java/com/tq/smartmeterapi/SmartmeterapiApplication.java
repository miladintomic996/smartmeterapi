package com.tq.smartmeterapi;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Smart Meter API", version = "1.0", description = "API for managing smart meter data"))
public class SmartmeterapiApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmartmeterapiApplication.class, args);
    }

}
