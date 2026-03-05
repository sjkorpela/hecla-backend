package com.hecla.heclaBackend.controller;

import com.hecla.heclaBackend.model.DataTransferPerson;
import com.hecla.heclaBackend.service.PersonService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api")
public class PersonController {

  @Autowired
  private PersonService personService;

  @PostMapping("/persons")
  @ResponseStatus(HttpStatus.CREATED)
  public DataTransferPerson createPerson(@RequestBody DataTransferPerson dtoPerson) throws BadRequestException {
    return personService.createPerson(dtoPerson);
  }

  @GetMapping("/persons")
  public List<DataTransferPerson> readAllPersons() {
    return personService.findAll();
  }

  @GetMapping("/persons/{id}")
  public DataTransferPerson readPersonById(@PathVariable int id) {
    return personService.findById(id);
  }

  @PutMapping("/persons/{id}")
  public void updatePersonById(@PathVariable int id, @RequestBody DataTransferPerson person) throws BadRequestException {
    personService.updateById(id, person);
  }

  @DeleteMapping("/persons/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deletePersonById(@PathVariable int id) {
    personService.deleteById(id);
  }
}
