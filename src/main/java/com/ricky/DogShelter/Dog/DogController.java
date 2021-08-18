package com.ricky.DogShelter.Dog;

import java.util.List;
import java.util.stream.Collectors;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.hateoas.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DogController {
    private final DogRepository dogRepository;
    private final DogAssembler dogAssembler;

    DogController(DogRepository dogRepository, DogAssembler dogAssembler){
        this.dogRepository = dogRepository;
        this.dogAssembler = dogAssembler;
    }
        
    @GetMapping("/dog") //get all dogs
    CollectionModel<EntityModel<Dog>> all() {
        List<EntityModel<Dog>> dogs = dogRepository.findAll().stream() //
            .map(dogAssembler::toModel) 
            .collect(Collectors.toList());    
        return CollectionModel.of(dogs, linkTo(methodOn(DogController.class).all()).withSelfRel());
    }

    @GetMapping("/dog/{id}") //get dog {id}
    EntityModel<Dog> one(@PathVariable Long id) {
        Dog dog = dogRepository.findById(id)
        .orElseThrow(() -> new DogNotFoundException(id));
        return dogAssembler.toModel(dog);
    }

    @GetMapping("/dog/available") //get available dogs
    CollectionModel<EntityModel<Dog>> available() {
        List<EntityModel<Dog>> dogs = dogRepository.findByStatus(Status.AVAILABLE).stream()
            .map(dogAssembler::toModel)
            .collect(Collectors.toList());
        return CollectionModel.of(dogs, linkTo(methodOn(DogController.class).all()).withSelfRel());
    }

    @GetMapping("/dog/foreverhomed") //get adopted dogs
    CollectionModel<EntityModel<Dog>> adopted() {
        List<EntityModel<Dog>> dogs = dogRepository.findByStatus(Status.ADOPTED).stream()
            .map(dogAssembler::toModel)
            .collect(Collectors.toList());
        return CollectionModel.of(dogs, linkTo(methodOn(DogController.class).all()).withSelfRel());
    }
    
    @PostMapping("/dog") //add new dog
    ResponseEntity<EntityModel<Dog>> addDog(@RequestBody Dog dog) {
        dog.setStatus(Status.AVAILABLE);
        Dog newDog = dogRepository.save(dog);
        return ResponseEntity //
            .created(linkTo(methodOn(DogController.class).one(newDog.getId())).toUri()) //
            .body(dogAssembler.toModel(newDog));
    }

    @PutMapping("/dog/{id}") //edit dog info (cannot change status)
    ResponseEntity<?> editDog(@RequestBody Dog newDog, @PathVariable Long id){
        Dog updatedDog = dogRepository.findById(id)
        .map(dog -> {
            dog.setName(newDog.getName());
            dog.setAge(newDog.getAge());
            dog.setBreed(newDog.getBreed());
            dog.setGender(newDog.getGender());
            return dogRepository.save(dog);
        })
        .orElseGet(() -> {
            newDog.setId(id);
            return dogRepository.save(newDog);
        });

        EntityModel<Dog> entityModel = dogAssembler.toModel(updatedDog);
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    @PutMapping("/dog/{id}/hold") //set dog from available to on hold
    ResponseEntity<?> hold(@PathVariable Long id) {

        Dog dog = dogRepository.findById(id) // 
          .orElseThrow(() -> new DogNotFoundException(id));
    
        if (dog.getStatus() == Status.AVAILABLE) {
            dog.setStatus(Status.ON_HOLD);
            return ResponseEntity.ok(dogAssembler.toModel(dogRepository.save(dog)));
        }
    
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED) //
        .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE) //
        .body(org.springframework.hateoas.mediatype.problem.Problem.create().withTitle("Method not allowed") //
        .withDetail("You can't hold a dog that is in the " + dog.getStatus() + " status"));
    }

    @DeleteMapping("dog/{id}") //delete dog from database
    ResponseEntity<?> deleteDog(@PathVariable Long id) {
        dogRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/dog/{id}/cancel") //set dog from onhold back to available
    ResponseEntity<?> cancel(@PathVariable Long id) {

        Dog dog = dogRepository.findById(id) // 
          .orElseThrow(() -> new DogNotFoundException(id));
    
        if (dog.getStatus() == Status.ON_HOLD) {
            dog.setStatus(Status.AVAILABLE);
            return ResponseEntity.ok(dogAssembler.toModel(dogRepository.save(dog)));
        }
    
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED) //
        .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE) //
        .body(org.springframework.hateoas.mediatype.problem.Problem.create().withTitle("Method not allowed") //
        .withDetail("You can't cancel a dog that is in the " + dog.getStatus() + " status"));
    }
    

    @PutMapping("dog/{id}/adopt") //set dog from available/onhold to adopted <3
    ResponseEntity<?> adopt(@PathVariable Long id) {

        Dog dog = dogRepository.findById(id) // 
          .orElseThrow(() -> new DogNotFoundException(id));
    
        if (dog.getStatus() == Status.ON_HOLD || dog.getStatus() == Status.AVAILABLE) {
            dog.setStatus(Status.ADOPTED);
            return ResponseEntity.ok(dogAssembler.toModel(dogRepository.save(dog)));
        }
    
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED) //
        .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE) //
        .body(org.springframework.hateoas.mediatype.problem.Problem.create().withTitle("Method not allowed") //
        .withDetail("You can't adopt a dog that is in the " + dog.getStatus() + " status"));
    }

}
