package com.hecla.heclaBackend.repository;

import com.hecla.heclaBackend.model.DocumentPerson;
import org.springframework.data.mongodb.repository.MongoRepository;

import javax.swing.text.Document;

public interface PersonRepo extends MongoRepository<DocumentPerson, Integer> {

}
