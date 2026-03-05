package com.hecla.heclaBackend.service;

import com.hecla.heclaBackend.model.DataTransferPerson;
import com.hecla.heclaBackend.model.DocumentPerson;
import com.hecla.heclaBackend.repository.PersonRepo;
import lombok.NonNull;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class PersonService {

  @Autowired
  PersonRepo repo;

  @Autowired
  private ValidationService validationService;

  public DataTransferPerson createPerson(DataTransferPerson dtoPerson) throws BadRequestException {
    validationService.validateDataTransferPerson(dtoPerson);
    return repo.createPerson(dtoPerson).toDataTransferPerson();
  }

  public List<DataTransferPerson> findAll() {
    List<DocumentPerson> persons = repo.findAll();
    return persons.stream().map(DocumentPerson::toDataTransferPerson).toList();
  }

  public DataTransferPerson findById(int id) {
    DocumentPerson person = repo.findById(id);
    if (person == null) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }
    return person.toDataTransferPerson();
  }

  public void updateById(int id, DataTransferPerson dtoPerson) throws BadRequestException {
    if (!repo.existsById(id)) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }
    validationService.validateDataTransferPerson(dtoPerson);
    DocumentPerson docPerson = new DocumentPerson(dtoPerson);
    docPerson.setId(id);
    repo.updatePerson(docPerson);
  }

  public void deleteById(int id) {
    repo.deleteById(id);
  }

}