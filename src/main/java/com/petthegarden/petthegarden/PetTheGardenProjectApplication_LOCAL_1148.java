package com.petthegarden.petthegarden;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class PetTheGardenProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(PetTheGardenProjectApplication.class, args);
    }

}
