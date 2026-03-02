package com.hecla.heclaBackend.service;

import com.hecla.heclaBackend.model.DataTransferPerson;
import com.hecla.heclaBackend.model.DocumentPerson;
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
  MongoTemplate repo;

  @Autowired
  private ValidationService validationService;

  public void createPerson(DataTransferPerson person) throws BadRequestException {
    validationService.validateDataTransferPerson(person);
    repo.save(new DocumentPerson(0, person));
  }

  public List<DocumentPerson> findAll() {
    return repo.findAll(DocumentPerson.class);
  }

  public DocumentPerson findById(int id) {
    return repo.findById(id, DocumentPerson.class);
  }

  public void updateById(int id, DataTransferPerson person) throws BadRequestException {
    if (!validationService.personExistsById(id)) {
      throw new BadRequestException("Given id not found");
    }
    validationService.validateDataTransferPerson(person);
    repo.save(new DocumentPerson(id, person));
  }

//  public void patchById(int id, DataTransferPerson person) throws BadRequestException {
//    if (!validationService.personExistsById(id)) {
//      throw new BadRequestException("Given id not found");
//    }
//    validationService.validateDataTransferPerson(person);
//
//    Query idQuery = new Query(Criteria.where("_id").is(id));
//    Update update = new Update();
//
//    StringBuilder debug = new StringBuilder("Iterated fields: ");
//    AtomicInteger count = new AtomicInteger();
//
//
//
//    Arrays.stream(DataTransferPerson.class.getFields()).forEach(field -> {
//      count.getAndIncrement();
//      debug.append(field.getName());
//    });
//
//    debug.append(count.get() + "/" + DataTransferPerson.class.getFields().length);
//
//    update.set("birthPlace", debug.toString());
//
//    repo.updateFirst(idQuery, update, DocumentPerson.class);
//  }

  public void deleteById(int id) {
    repo.remove(new Query(Criteria.where("_id").is(id)), DocumentPerson.class);
  }

}