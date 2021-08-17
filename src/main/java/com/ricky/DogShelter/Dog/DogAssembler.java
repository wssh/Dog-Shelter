package com.ricky.DogShelter.Dog;

import org.springframework.stereotype.Component;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class DogAssembler implements RepresentationModelAssembler<Dog, EntityModel<Dog>> {
    @Override
    public EntityModel<Dog> toModel(Dog dog){
        // Unconditional links to single-item resource and aggregate root
            EntityModel<Dog> dogModel = EntityModel.of(dog,
            linkTo(methodOn(DogController.class).one(dog.getId())).withSelfRel(),
            linkTo(methodOn(DogController.class).all()).withRel("dogs"));

        // Conditional links based on state of the order
        if (dog.getStatus() == Status.AVAILABLE){
            dogModel.add(linkTo(methodOn(DogController.class).hold(dog.getId())).withRel("hold"));
        }
        else if (dog.getStatus() == Status.ON_HOLD) {
        dogModel.add(linkTo(methodOn(DogController.class).cancel(dog.getId())).withRel("cancel"));
        dogModel.add(linkTo(methodOn(DogController.class).adopt(dog.getId())).withRel("adopt"));
        }
        return dogModel;
    } 
}
