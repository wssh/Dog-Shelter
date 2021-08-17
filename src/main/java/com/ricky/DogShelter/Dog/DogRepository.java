package com.ricky.DogShelter.Dog;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DogRepository extends JpaRepository<Dog, Long>{
    List<Dog> findByGender(String gender);
    List<Dog> findByStatus(Status status);
    List<Dog> findByBreed(String breed);
}
