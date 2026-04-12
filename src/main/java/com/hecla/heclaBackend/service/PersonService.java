package com.hecla.heclaBackend.service;

import com.hecla.heclaBackend.model.DataTransferPerson;
import com.hecla.heclaBackend.model.DocumentPerson;
import com.hecla.heclaBackend.repository.PersonRepo;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class PersonService {

  @Autowired
  PersonRepo repo;

  @Autowired
  private ValidationService validationService;

  public DataTransferPerson createPerson(DataTransferPerson dtoPerson) {
    if (dtoPerson == null) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No person given!");
    }
    validationService.validateDataTransferPerson(dtoPerson);
    return repo.createPerson(dtoPerson).toDataTransferPerson();
  }

  public List<DataTransferPerson> findAll() {
    List<DocumentPerson> persons = repo.findAll();
    return persons.stream().map(DocumentPerson::toDataTransferPerson).toList();
  }

  public List<DataTransferPerson> findAllPagedAndSorted(Pageable pageable, Sort sort) {
    List<DocumentPerson> persons = repo.findAll(pageable, sort);
    return persons.stream().map(DocumentPerson::toDataTransferPerson).toList();
  }

  public DataTransferPerson findById(int id) {
    DocumentPerson person = repo.findById(id);
    if (person == null) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }
    return person.toDataTransferPerson();
  }

  public void updateById(int id, DataTransferPerson dtoPerson) {
    if (dtoPerson == null) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No person given!");
    }
    if (!repo.existsById(id)) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }
    validationService.validateDataTransferPerson(dtoPerson);
    repo.updatePersonById(id, dtoPerson);
  }

  public void deleteById(int id) {
    repo.deleteById(id);
  }

}