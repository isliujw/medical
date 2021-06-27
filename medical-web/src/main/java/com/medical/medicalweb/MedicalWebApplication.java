package com.medical.medicalweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class MedicalWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(MedicalWebApplication.class, args);
    }

}
