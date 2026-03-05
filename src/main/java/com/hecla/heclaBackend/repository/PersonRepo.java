package com.hecla.heclaBackend.repository;

import com.hecla.heclaBackend.model.DocumentPerson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.swing.text.Document;
import java.util.List;
import java.util.random.RandomGenerator;

@Repository
public class PersonRepo {

  @Autowired
  MongoTemplate repo;

  public DocumentPerson createPerson(DocumentPerson person) {
    return repo.save(person);
  }

  public List<DocumentPerson> findAll() {
    return repo.findAll(DocumentPerson.class);
  }

  public DocumentPerson findById(int id) {
    return repo.findById(id, DocumentPerson.class);
  }

  public void updatePerson(DocumentPerson person) {
    repo.save(person);
  }

  public void deleteById(int id) {
    repo.remove(new Query(Criteria.where("_id").is(id)), DocumentPerson.class);
  }

  public boolean existsById(int id) {
    return repo.exists(new Query(Criteria.where("_id").is(id)), DocumentPerson.class);
  }

  public int getNextId() {
    Query query = new Query();
    query.with(Sort.by(Sort.Direction.DESC, "_id"));
    query.limit(1);

    DocumentPerson latestPerson = repo.findOne(query, DocumentPerson.class);
    return latestPerson == null ? 0 : latestPerson.getId() + 1;
  }

}
