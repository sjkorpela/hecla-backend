package com.hecla.heclaBackend.controller;

import com.hecla.heclaBackend.model.DataTransferPerson;
import com.hecla.heclaBackend.service.PersonService;
import com.mongodb.client.result.UpdateResult;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@RestController
@RequestMapping(value = "/api")
public class PersonController {

  @Autowired
  private PersonService personService;

  @PostMapping("/persons")
  public void createPerson(@RequestBody DataTransferPerson person) throws BadRequestException {
    personService.createPerson(person);
    throw new ResponseStatusException(HttpStatus.CREATED);
  }

  @GetMapping("/persons")
  public ResponseEntity<?> readAllPersons() {
    return ResponseEntity.ok().body(personService.findAll());
  }

  @GetMapping("/persons/{id}")
  public ResponseEntity<?> readPersonById(@PathVariable int id) {
    return ResponseEntity.ok().body(personService.findById(id));
  }

  @PutMapping("/persons/{id}")
  public void updatePersonById(@PathVariable int id, @RequestBody DataTransferPerson person) throws BadRequestException {
    personService.updateById(id, person);
    throw new ResponseStatusException(HttpStatus.OK);
  }

//  @PatchMapping("/persons/{id}")
//  public UpdateResult patchPersonById(
//          @PathVariable int id,
//          @RequestBody DataTransferPerson person // Map<String, Object>
//  ) throws BadRequestException {
//    personService.patchById(id, person);
//    throw new ResponseStatusException(HttpStatus.OK);
//  }

  @DeleteMapping("/persons/{id}")
  public void deletePersonById(@PathVariable int id) {
    personService.deleteById(id);
  }
}
