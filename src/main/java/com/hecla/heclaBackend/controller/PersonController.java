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

  @PostMapping("/persons")
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

  @GetMapping("/persons")
  public ResponseEntity<?> readAllPersons() {
    return ResponseEntity.ok().body(personService.findAll());
  }

  @GetMapping("/persons/{id}")
  public ResponseEntity<?> readPersonById(@PathVariable int id) {
    return ResponseEntity.ok().body(personService.findById(id));
  }

  @PutMapping("/persons/{id}")
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

  @DeleteMapping("/persons/{id}")
  public void deletePersonById(@PathVariable int id) {
    personService.deleteById(id);
  }
}
