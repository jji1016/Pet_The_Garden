package com.petthegarden.petthegarden;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableJpaAuditing //이게 있어야 regdate가 자동으로 들어감
public class PetTheGardenProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(PetTheGardenProjectApplication.class, args);
    }

}
