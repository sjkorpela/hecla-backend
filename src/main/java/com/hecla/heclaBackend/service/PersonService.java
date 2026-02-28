package com.hecla.heclaBackend.service;

import com.hecla.heclaBackend.model.Person;
import com.hecla.heclaBackend.repository.PersonRepo;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonService {

  @Autowired
  private PersonRepo repo;

  public void createPerson(Person person) throws BadRequestException {
    if (repo.existsById(person.getId())) {
      throw new BadRequestException("Person with id " + person.getId() + " already exists");
    }
    repo.save(person);
  }

  public List<Person> findAll() {
    return repo.findAll();
  }

  public Optional<Person> findById(int id) {
    return repo.findById(id);
  }

  public void updateById(int id, Person person) {
    repo.save(person);
  }

  public void deleteById(int id) {
    repo.deleteById(id);
  }

}