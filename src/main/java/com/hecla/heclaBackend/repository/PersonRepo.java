package com.hecla.heclaBackend.repository;

import com.hecla.heclaBackend.model.DataTransferPerson;
import com.hecla.heclaBackend.model.DocumentPerson;
import com.hecla.heclaBackend.model.PersonsFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.stereotype.Repository;

import javax.print.Doc;
import java.util.List;
import java.util.regex.Pattern;

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

  public Page<DocumentPerson> findAll(Pageable pageable, PersonsFilter filter, String search) {
    Query query = new Query();

    query.with(pageable);

    if (filter.deceased() != null && filter.deceased()) query.addCriteria(
            Criteria.where("deceased").is(true)
    );
    if (filter.deceased() != null && !filter.deceased()) query.addCriteria(new Criteria().orOperator(
            Criteria.where("deceased").is(false),
            Criteria.where("deceased").isNull()
    ));
    if (filter.gender() != null) query.addCriteria(
            Criteria.where("gender").is(filter.gender())
    );
    if (filter.bornAfter() != null) query.addCriteria(
            Criteria.where("birthYear").gt(filter.bornAfter())
    );
    if (filter.bornBefore() != null) query.addCriteria(
            Criteria.where("birthYear").lt(filter.bornBefore())
    );
    if (filter.diedAfter() != null) query.addCriteria(
            Criteria.where("deathYear").gt(filter.diedAfter())
    );
    if (filter.diedBefore() != null) query.addCriteria(
            Criteria.where("deathYear").lt(filter.diedBefore())
    );

    if (search != null)  {
      Pattern pattern = Pattern.compile(
              Pattern.quote(search),
              Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE
      );
      query.addCriteria(new Criteria().orOperator(
              Criteria.where("firstNames.name").regex(pattern),
              Criteria.where("lastNames.name").regex(pattern)
      ));
    }

    List<DocumentPerson> persons = repo.find(query, DocumentPerson.class);
    long count = repo.count(query, DocumentPerson.class);
    return new PageImpl<DocumentPerson>(persons, pageable, count);
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
