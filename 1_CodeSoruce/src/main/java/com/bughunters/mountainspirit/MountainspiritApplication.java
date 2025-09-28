package com.bughunters.mountainspirit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class MountainspiritApplication {

    public static void main(String[] args) {
        SpringApplication.run(MountainspiritApplication.class, args);
    }

}
