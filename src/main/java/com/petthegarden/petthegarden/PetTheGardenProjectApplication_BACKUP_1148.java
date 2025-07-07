package com.petthegarden.petthegarden;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
<<<<<<< HEAD
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
=======
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
>>>>>>> 400f9c8e9a8ab491bb1aaaeecf5f3874e8b2cb42
public class PetTheGardenProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(PetTheGardenProjectApplication.class, args);
    }

}
