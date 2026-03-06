package com.hecla.heclaBackend;

import com.hecla.heclaBackend.model.DataTransferPerson;
import com.hecla.heclaBackend.model.DocumentPerson;
import com.hecla.heclaBackend.repository.PersonRepo;
import com.hecla.heclaBackend.service.PersonService;
import com.hecla.heclaBackend.service.ValidationService;
import org.apache.coyote.BadRequestException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PersonServiceTests {

  @Mock
  PersonRepo repo;

  @Mock
  ValidationService validationService;

  @InjectMocks
  PersonService personService;

  @Test
  void createPersonAndFindByIdWithAllNullPerson() {
//    DataTransferPerson requestPerson = new DataTransferPerson(
//            null,
//            null,
//            null,
//            null,
//            null,
//            null,
//            null,
//            null,
//            null,
//            null,
//            null,
//            null
//    );
//
//    DocumentPerson expectedPerson = new DocumentPerson(requestPerson);
//    expectedPerson.setId(0);
//    when(repo.findById(0)).thenReturn(expectedPerson);
//
//    try {
//      personService.createPerson(requestPerson);
//    } catch (BadRequestException e) {
//      fail("Person to be created should be valid even if all given values are null");
//    }
//
//    DataTransferPerson resultPerson = personService.findById(0);
//
//    assertEquals(expectedPerson.toDataTransferPerson(), resultPerson);
  }
}
