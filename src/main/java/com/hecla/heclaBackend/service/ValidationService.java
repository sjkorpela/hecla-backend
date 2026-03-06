package com.hecla.heclaBackend.service;

import com.hecla.heclaBackend.model.DataTransferPerson;
import com.hecla.heclaBackend.model.DocumentPerson;
import com.hecla.heclaBackend.repository.PersonRepo;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.Map;

@Service
public class ValidationService {

  @Autowired
  PersonRepo repo;

  public void validateDataTransferPerson(DataTransferPerson person) {

    if (person.fatherId() != null && !repo.existsById(person.fatherId())) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Given father not found!");
    }

    if (person.motherId() != null && !repo.existsById(person.motherId())) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Given mother not found!");
    }

    // Gender validation: not necessary because enum?

    // Birth year validation

    // Birthplace validation

    // Alive validation

    // Death year validation

    // Death place validation

    // First names validation

    // Last names validation

    // Additional info validation
  }
}
