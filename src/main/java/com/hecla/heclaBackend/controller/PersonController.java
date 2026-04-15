package com.hecla.heclaBackend.controller;

import com.hecla.heclaBackend.model.DataTransferPerson;
import com.hecla.heclaBackend.model.DocumentPerson;
import com.hecla.heclaBackend.model.PersonsFilter;
import com.hecla.heclaBackend.service.PersonService;
import com.sun.tools.jconsole.JConsoleContext;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
  public DataTransferPerson createPerson(@RequestBody DataTransferPerson dtoPerson) {
    return personService.createPerson(dtoPerson);
  }

  @GetMapping("/persons")
  public Page<DataTransferPerson> readAllPersonsPaged(
          @RequestParam(required = false) Boolean deceased,
          @RequestParam(required = false) DocumentPerson.Gender gender,
          @RequestParam(required = false) Integer bornAfter,
          @RequestParam(required = false) Integer bornBefore,
          @RequestParam(required = false) Integer diedAfter,
          @RequestParam(required = false) Integer diedBefore,
          @RequestParam(required = false) String search,
          Pageable pageable
  ) {
    PersonsFilter filter = new PersonsFilter(
            deceased,
            gender,
            bornAfter,
            bornBefore,
            diedAfter,
            diedBefore
    );

    return personService.findAll(pageable, filter, search);
  }

  @GetMapping("/persons/{id}")
  public DataTransferPerson readPersonById(@PathVariable int id) {
    return personService.findById(id);
  }

  @PutMapping("/persons/{id}")
  public void updatePersonById(@PathVariable int id, @RequestBody DataTransferPerson person) {
    personService.updateById(id, person);
  }

  @DeleteMapping("/persons/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deletePersonById(@PathVariable int id) {
    personService.deleteById(id);
  }
}
