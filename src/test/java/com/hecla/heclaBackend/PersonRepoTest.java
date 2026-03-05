package com.hecla.heclaBackend;

import com.hecla.heclaBackend.model.*;
import com.hecla.heclaBackend.repository.PersonRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.mongodb.test.autoconfigure.DataMongoTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Import;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.mongodb.MongoDBContainer;

import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
@Testcontainers
@Import(PersonRepo.class)
class PersonRepoTest {

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