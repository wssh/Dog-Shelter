package com.ricky.DogShelter.Dog;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Dog {
    @Id @GeneratedValue
    private Long id;
    private String name;
    private String breed;
    private String age;
    private String gender;
    private Status status;

    public Dog(){}

    public Dog(String name, String breed, String age, String gender, Status status){
        this.name = name;
        this.breed = breed;
        this.age = age;
        this.gender = gender;
        this.status = status;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id){
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBreed() {
        return this.breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public String getAge() {
        return this.age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getGender() {
        return this.gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Status getStatus(){
        return this.status;
    }

    public void setStatus(Status status){
        this.status = status;
    }

    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if(!(o instanceof Dog)) return false;
        Dog dog = (Dog)o;
        return Objects.equals(this.id, dog.id) && Objects.equals(this.name, dog.name) && Objects.equals(this.breed, dog.breed)
                && Objects.equals(this.gender, dog.gender) && Objects.equals(this.status, dog.status);
    }

    @Override
    public int hashCode(){
        return Objects.hash(this.id, this.name, this.breed, this.gender, this.age, this.status);
    }

    @Override
    public String toString(){
        return "Dog{" + "id=" + this.id + ", name='" + this.name + "\'" + ", breed=" + this.breed + "\'" + ", age='" + this.age + "\'" + ", gender='" + this.gender + "\'" + ", status='" + this.status + "\'" + "}";
    }
}
