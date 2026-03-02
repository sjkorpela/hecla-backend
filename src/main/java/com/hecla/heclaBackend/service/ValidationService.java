package com.hecla.heclaBackend.service;

import com.hecla.heclaBackend.model.DataTransferPerson;
import com.hecla.heclaBackend.repository.PersonRepo;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ValidationService {

  @Autowired
  private PersonRepo repo;

  public void validateDataTransferPerson(DataTransferPerson person) throws BadRequestException {
    if (person.fatherId() != null && !repo.existsById(person.fatherId())) {
      throw new BadRequestException("Given father not found!");
    }

    if (person.motherId() != null && !repo.existsById(person.motherId())) {
      throw new BadRequestException("Given mother not found!");
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
