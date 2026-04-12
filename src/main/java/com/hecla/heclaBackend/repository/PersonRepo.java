package com.hecla.heclaBackend.repository;

import com.hecla.heclaBackend.model.DataTransferPerson;
import com.hecla.heclaBackend.model.DocumentPerson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import javax.print.Doc;
import java.util.List;

@Repository
public class PersonRepo {

  @Autowired
  MongoTemplate repo;

  public DocumentPerson createPerson(DataTransferPerson dtoPerson) {
    DocumentPerson docPerson = new DocumentPerson(getNextId(), dtoPerson);
    return repo.save(docPerson);
  }

  public List<DocumentPerson> findAll() {
    return repo.findAll(DocumentPerson.class);
  }

  public List<DocumentPerson> findAll(Pageable pageable, Sort sort) {
    Query query = new Query();
    query.with(pageable);
    query.with(sort);

    return repo.find(query, DocumentPerson.class);
  }

  public DocumentPerson findById(int id) {
    return repo.findById(id, DocumentPerson.class);
  }

  public void updatePersonById(int id, DataTransferPerson dtoPerson) {
    DocumentPerson docPerson = new DocumentPerson(id, dtoPerson);
    repo.save(docPerson);
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

  public void dropCollection() {
    repo.dropCollection(DocumentPerson.class);
  }

}
