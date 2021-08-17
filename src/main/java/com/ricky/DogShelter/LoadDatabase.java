package com.ricky.DogShelter;


import com.ricky.DogShelter.Dog.Dog;
import com.ricky.DogShelter.Dog.DogRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadDatabase {
    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(DogRepository dogRepository) {
        return args -> {
            dogRepository.save(new Dog("Jackie", "Chihuahua", "4", "female"));
            dogRepository.save(new Dog("William", "Labrador Retriever", "3", "male"));
            dogRepository.save(new Dog("Hyunwoo", "Boxer", "3", "male"));
            dogRepository.save(new Dog("Nadine", "Dachshund", "2", "female"));
            dogRepository.save(new Dog("Yuki", "Dalmatian", "4", "male"));
            dogRepository.save(new Dog("Alex", "Boston Terrier", "3", "male"));
            dogRepository.save(new Dog("Sissela", "Yorkshire Terrier", "1", "female"));
            dogRepository.save(new Dog("Hyejin", "Maltese", "1", "female"));
            dogRepository.save(new Dog("Nathapon", "Rottweiler", "6", "male"));
            dogRepository.findAll().forEach(dog -> log.info("Preloaded " + dog));
        };
    }
}
