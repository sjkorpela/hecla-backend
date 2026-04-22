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
import org.springframework.data.mongodb.core.aggregation.*;
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

    Criteria filterCriteria = new Criteria();

    if (filter.deceased() != null && filter.deceased())
      filterCriteria.and("deceased").is(true);
    if (filter.deceased() != null && !filter.deceased())
      filterCriteria.andOperator(new Criteria().orOperator(
            Criteria.where("deceased").is(false),
            Criteria.where("deceased").isNull()
    ));
    if (filter.gender() != null) filterCriteria.and("gender").is(filter.gender());

    if (filter.bornAfter() != null && filter.bornBefore() != null) {
      filterCriteria.and("birthYear").gte(filter.bornAfter()).lte(filter.bornBefore());
    } else if (filter.bornAfter() != null) {
      filterCriteria.and("birthYear").gte(filter.bornAfter());
    } else if (filter.bornBefore() != null) {
      filterCriteria.and("birthYear").lte(filter.bornBefore());
    }

    if (filter.diedAfter() != null && filter.diedBefore() != null) {
      filterCriteria.and("deathYear").gte(filter.diedAfter()).lte(filter.diedBefore());
    } else if (filter.diedAfter() != null) {
      filterCriteria.and("deathYear").gte(filter.diedAfter());
    } else if (filter.diedBefore() != null) {
      filterCriteria.and("deathYear").lte(filter.diedBefore());
    }

    if (search != null)  {
      Pattern pattern = Pattern.compile(
              Pattern.quote(search),
              Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE
      );
      filterCriteria.andOperator(new Criteria().orOperator(
              Criteria.where("firstNames.name").regex(pattern),
              Criteria.where("lastNames.name").regex(pattern)
      ));
    }

    if (pageable.getSort().getOrderFor("name") != null) {
      MatchOperation match = Aggregation.match(filterCriteria);

      AggregationExpression nicknameFirstName = ArrayOperators.ArrayElemAt.arrayOf(
              ArrayOperators.Filter.filter("firstNames")
                      .as("fn")
                      .by(ComparisonOperators.Eq.valueOf("$$fn.nickname").equalToValue(true))
      ).elementAt(0);

      AggregationExpression currentLastName = ArrayOperators.ArrayElemAt.arrayOf(
              ArrayOperators.Filter.filter("lastNames")
                      .as("ln")
                      .by(ComparisonOperators.Eq.valueOf("$$ln.current").equalToValue(true))
      ).elementAt(0);

      AddFieldsOperation addFields = Aggregation.addFields()
              .addFieldWithValue("_sortFirstName", nicknameFirstName)
              .addFieldWithValue("_sortLastName", currentLastName)
              .build();

      Sort.Direction direction = pageable.getSort().getOrderFor("name").getDirection();

      SortOperation nameSort = Aggregation.sort(
              Sort.by(direction, "_sortFirstName.name")
                      .and(Sort.by(direction, "_sortLastName.name"))
      );


      SkipOperation skip = Aggregation.skip((long) pageable.getPageNumber() * pageable.getPageSize());
      LimitOperation limit = Aggregation.limit(pageable.getPageSize());

      Aggregation aggregation = Aggregation.newAggregation(
              DocumentPerson.class,
              match,
              addFields,
              nameSort,
              skip,
              limit
      );

      List<DocumentPerson> results = repo.aggregate(aggregation, DocumentPerson.class, DocumentPerson.class)
              .getMappedResults();

      long count = repo.count(new Query(), DocumentPerson.class);

      return new PageImpl<>(results, pageable, count);
    }

    query.addCriteria(filterCriteria);

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
