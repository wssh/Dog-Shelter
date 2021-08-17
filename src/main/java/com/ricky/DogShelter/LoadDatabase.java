package com.ricky.DogShelter;

import com.github.javafaker.Faker;

import com.ricky.DogShelter.Dog.Dog;
import com.ricky.DogShelter.Dog.DogRepository;
import com.ricky.DogShelter.Dog.Status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadDatabase {
    int n = 0;
    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);
    Faker faker = new Faker();
    @Bean
    CommandLineRunner initDatabase(DogRepository dogRepository) {
        while(n < 33){
            dogRepository.save(new Dog(faker.dog().name(), faker.dog().breed(), faker.dog().age(), faker.dog().gender(), Status.AVAILABLE));
            dogRepository.save(new Dog(faker.dog().name(), faker.dog().breed(), faker.dog().age(), faker.dog().gender(), Status.ON_HOLD));
            dogRepository.save(new Dog(faker.dog().name(), faker.dog().breed(), faker.dog().age(), faker.dog().gender(), Status.ADOPTED));
            n++;
        }
        return args -> {
            dogRepository.findAll().forEach(dog -> log.info("Preloaded " + dog));
        };
    }
}
