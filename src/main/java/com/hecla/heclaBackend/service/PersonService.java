package com.hecla.heclaBackend.service;

import com.hecla.heclaBackend.model.DataTransferPerson;
import com.hecla.heclaBackend.model.DocumentPerson;
import com.hecla.heclaBackend.repository.PersonRepo;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class PersonService {

  @Autowired
  PersonRepo repo;

  @Autowired
  private ValidationService validationService;

  public void createPerson(DataTransferPerson person) throws BadRequestException {
    validationService.validateDataTransferPerson(person);
    repo.createPerson(new DocumentPerson(0, person));
  }

  public List<DataTransferPerson> findAll() {
    List<DocumentPerson> persons = repo.findAll();
    return persons.stream().map(DocumentPerson::toDataTransferPerson).toList();
  }

  public DataTransferPerson findById(int id) {
    DocumentPerson person = repo.findById(id);
    return person != null ? person.toDataTransferPerson() : null;
  }

  public void updateById(int id, DataTransferPerson person) throws BadRequestException {
    if (!validationService.personExistsById(id)) {
      throw new BadRequestException("Given id not found");
    }
    validationService.validateDataTransferPerson(person);
    repo.updatePerson(new DocumentPerson(id, person));
  }

  public void deleteById(int id) {
    repo.deleteById(id);
  }

}