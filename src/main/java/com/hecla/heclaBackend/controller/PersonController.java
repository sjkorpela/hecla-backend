package com.hecla.heclaBackend.controller;

import com.hecla.heclaBackend.model.Person;
import com.hecla.heclaBackend.service.PersonService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api")
public class PersonController {

  @Autowired
  private PersonService personService;

  @PostMapping("/person")
  public ResponseEntity<?> createPerson(@RequestBody Person person) {
    try {
      personService.createPerson(person);
      return ResponseEntity.ok().build();
    } catch (BadRequestException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    } catch (Exception e) {
      return ResponseEntity.internalServerError().body(e.getMessage());
    }
  }

  @GetMapping("/person")
  public ResponseEntity<?> readAllPersons() {
    try {
      return ResponseEntity.ok().body(personService.findAll());
    } catch (Exception e) {
      return ResponseEntity.internalServerError().body(e.getMessage());
    }
  }

  @GetMapping("/person/{id}")
  public ResponseEntity<?> readPersonById(@PathVariable int id) {
    try {
      return ResponseEntity.ok().body(personService.findById(id));
    } catch (Exception e) {
      return ResponseEntity.internalServerError().body(e.getMessage());
    }
  }

  @PutMapping("/person/{id}")
  public ResponseEntity<?> updatePersonById(@PathVariable int id, @RequestBody Person person) {
    try {
      personService.updateById(id, person);
      return ResponseEntity.ok().build();
    }catch (BadRequestException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    } catch (Exception e) {
      return ResponseEntity.internalServerError().body(e.getMessage());
    }
  }

  @DeleteMapping("/person/{id}")
  public ResponseEntity<?> deletePersonById(@PathVariable int id) {
    try {
      personService.deleteById(id);
      return ResponseEntity.ok().build();
    } catch (Exception e) {
      return ResponseEntity.internalServerError().body(e.getMessage());
    }
  }
}
