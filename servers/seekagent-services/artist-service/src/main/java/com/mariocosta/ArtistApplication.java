package com.mariocosta;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(
        basePackages = "com.mariocosta.clients"
)
public class ArtistApplication {
    public static void main(String[] args) {
        SpringApplication.run(ArtistApplication.class, args);
    }
}
