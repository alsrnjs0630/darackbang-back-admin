package com.lab.darackbang;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class DarackbangAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(DarackbangAdminApplication.class, args);
    }

}
