package com.hecla.heclaBackend.repository;

import com.hecla.heclaBackend.model.Person;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PersonRepo extends MongoRepository<Person, Integer> {

}
