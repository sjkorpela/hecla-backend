package com.hecla.heclaBackend;

import com.hecla.heclaBackend.model.*;
import com.hecla.heclaBackend.repository.PersonRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.mongodb.test.autoconfigure.DataMongoTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.mongodb.MongoDBContainer;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
@Testcontainers
@Import(PersonRepo.class)
class PersonRepoTests {

  @Container
  @ServiceConnection
  static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:latest");

  @Autowired
  PersonRepo repo;

  @BeforeEach
  void clearDatabase() {
    repo.dropCollection();
  }



  @Test
  void createAllNullPerson() {
    DocumentPerson docPerson = repo.createPerson(PersonFixtures.allNullDto);
    assertEquals(docPerson, repo.findById(docPerson.getId()));
  }

  @Test
  void createPersonAndFindPersonWithData() {
    DocumentPerson docPerson = repo.createPerson(PersonFixtures.erkkiJokinenDto);
    assertEquals(docPerson, repo.findById(docPerson.getId()));
  }

  @Test
  void createSeveralPersonsWithDataAndFindAll() {
    DocumentPerson docPerson1 = repo.createPerson(PersonFixtures.erkkiJokinenDto);
    DocumentPerson docPerson2 = repo.createPerson(PersonFixtures.maijaKallioDto);
    DocumentPerson docPerson3 = repo.createPerson(PersonFixtures.jussiLindstromDto);

    assertEquals(3, repo.findAll().size());
  }

  @Test
  void createSeveralPersonsWithDataAndPageThem() {
    DocumentPerson docPerson1 = repo.createPerson(PersonFixtures.erkkiJokinenDto);
    DocumentPerson docPerson2 = repo.createPerson(PersonFixtures.maijaKallioDto);
    DocumentPerson docPerson3 = repo.createPerson(PersonFixtures.jussiLindstromDto);

    Pageable pageable = PageRequest.of(1, 2);

    Page<DocumentPerson> persons = repo.findAll(pageable, PersonsFilter.unfiltered());

    assertEquals(1, persons.getContent().size());
    assertEquals(2, persons.getContent().get(0).getId());
  }

  @Test
  void createSeveralPersonsWithDataAndSortThem() {
    DocumentPerson docPerson1 = repo.createPerson(PersonFixtures.erkkiJokinenDto);
    DocumentPerson docPerson2 = repo.createPerson(PersonFixtures.maijaKallioDto);
    DocumentPerson docPerson3 = repo.createPerson(PersonFixtures.jussiLindstromDto);

    List<DocumentPerson> inputPersons = Arrays.asList(docPerson1, docPerson2, docPerson3);
    inputPersons.sort(Comparator.comparing(DocumentPerson::getBirthYear));

    Pageable pageable = Pageable.unpaged(Sort.by("birthYear"));

    Page<DocumentPerson> persons = repo.findAll(pageable, PersonsFilter.unfiltered());

    assertEquals(3, persons.getContent().size());
    assertEquals(inputPersons.get(0).getBirthYear(), persons.getContent().get(0).getBirthYear());
    assertEquals(inputPersons.get(1).getBirthYear(), persons.getContent().get(1).getBirthYear());
    assertEquals(inputPersons.get(2).getBirthYear(), persons.getContent().get(2).getBirthYear());
  }

  @Test
  void createSeveralPersonsWithDataAndSortAndPageThem() {
    DocumentPerson docPerson1 = repo.createPerson(PersonFixtures.erkkiJokinenDto);
    DocumentPerson docPerson2 = repo.createPerson(PersonFixtures.maijaKallioDto);
    DocumentPerson docPerson3 = repo.createPerson(PersonFixtures.jussiLindstromDto);

    List<DocumentPerson> inputPersons = Arrays.asList(docPerson1, docPerson2, docPerson3);
    inputPersons.sort(Comparator.comparing(DocumentPerson::getBirthYear));

    Pageable pageable = PageRequest.of(1, 1, Sort.by("birthYear"));

    Page<DocumentPerson> persons = repo.findAll(pageable, PersonsFilter.unfiltered());

    assertEquals(1, persons.getContent().size());
    assertEquals(inputPersons.get(1).getBirthYear(), persons.getContent().get(0).getBirthYear());
  }

  @Test
  void findByIdReturnsNullIfIdDoesNotExist() {
    assertNull(repo.findById(-1));
  }

  @Test
  void findAllReturnsEmptyListIfDatabaseIsEmpty() {
    assertTrue(repo.findAll().isEmpty());
  }

  @Test
  void updateByIdWorks() {
    DocumentPerson docPerson = repo.createPerson(PersonFixtures.maijaKallioDto);

    repo.updatePersonById(docPerson.getId(), PersonFixtures.erkkiJokinenDto);
    DocumentPerson expectedPerson = new DocumentPerson(
            docPerson.getId(),
            PersonFixtures.erkkiJokinenDto
    );

    assertEquals(
            expectedPerson,
            repo.findById(docPerson.getId())
    );
  }

  @Test
  void deleteByIdWorks() {
    DocumentPerson docPerson = repo.createPerson(PersonFixtures.erkkiJokinenDto);
    repo.deleteById(docPerson.getId());
    assertNull(repo.findById(docPerson.getId()));
  }

  @Test
  void existsByIdWorks() {
    int nextId = repo.getNextId();

    assertFalse(repo.existsById(nextId));

    DocumentPerson docPerson = repo.createPerson(PersonFixtures.jussiLindstromDto);

    assertTrue(repo.existsById(nextId));
  }

  @Test
  void getNextIdWorks() {
    assertEquals(0, repo.getNextId());

    DocumentPerson docPerson1 = repo.createPerson(PersonFixtures.erkkiJokinenDto);
    DocumentPerson docPerson2 = repo.createPerson(PersonFixtures.maijaKallioDto);
    DocumentPerson docPerson3 = repo.createPerson(PersonFixtures.jussiLindstromDto);

    assertEquals(docPerson3.getId() + 1, repo.getNextId());
  }

  @Test
  void dropCollectionWorks() {
    DocumentPerson docPerson1 = repo.createPerson(PersonFixtures.erkkiJokinenDto);
    DocumentPerson docPerson2 = repo.createPerson(PersonFixtures.maijaKallioDto);
    DocumentPerson docPerson3 = repo.createPerson(PersonFixtures.jussiLindstromDto);

    repo.dropCollection();

    assertEquals(0, repo.findAll().size());
  }
}