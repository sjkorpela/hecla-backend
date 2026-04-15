package com.hecla.heclaBackend;

import com.hecla.heclaBackend.model.DataTransferPerson;
import com.hecla.heclaBackend.model.DocumentPerson;
import com.hecla.heclaBackend.model.PersonsFilter;
import com.hecla.heclaBackend.repository.PersonRepo;
import com.hecla.heclaBackend.service.PersonService;
import com.hecla.heclaBackend.service.ValidationService;
import org.apache.coyote.BadRequestException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import javax.print.Doc;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

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
  void createAndFindAllNullPerson() {

    when(repo.getNextId()).thenReturn(0);
    int expectedId = repo.getNextId();

    when(repo.createPerson(PersonFixtures.allNullDto))
            .thenReturn(new DocumentPerson(expectedId, PersonFixtures.allNullDto));

    DataTransferPerson createdPerson = personService.createPerson(PersonFixtures.allNullDto);

    DocumentPerson expectedPerson = new DocumentPerson(expectedId, PersonFixtures.allNullDto);
    when(repo.findById(expectedId)).thenReturn(expectedPerson);

    DataTransferPerson resultPerson = personService.findById(expectedId);

    assertEquals(expectedPerson.toDataTransferPerson(), createdPerson);
    assertEquals(expectedPerson.toDataTransferPerson(), resultPerson);
  }

  @Test
  void findByIdThrowsNotFound() {
    ResponseStatusException exception = assertThrows(
            ResponseStatusException.class,
            () -> personService.findById(-1)
    );
    assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
  }

  @Test
  void createAndFindAllPersonWithData() {

    when(repo.getNextId()).thenReturn(0);
    int expectedId = repo.getNextId();

    when(repo.createPerson(PersonFixtures.erkkiJokinenDto))
            .thenReturn(new DocumentPerson(expectedId, PersonFixtures.erkkiJokinenDto));
    DataTransferPerson createdPerson = personService.createPerson(PersonFixtures.erkkiJokinenDto);

    DocumentPerson expectedPerson = new DocumentPerson(expectedId, PersonFixtures.erkkiJokinenDto);
    when(repo.findById(expectedId)).thenReturn(expectedPerson);

    DataTransferPerson resultPerson = personService.findById(expectedId);

    assertEquals(expectedPerson.toDataTransferPerson(), createdPerson);
    assertEquals(expectedPerson.toDataTransferPerson(), resultPerson);
  }

  @Test
  void createPersonWithNullParameters() {
    ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> personService.createPerson(null));
    assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
  }

  @Test
  void createSeveralPersonsWithDataAndFindAll() {
    when(repo.createPerson(PersonFixtures.erkkiJokinenDto))
            .thenReturn(new DocumentPerson(0, PersonFixtures.erkkiJokinenDto));
    DataTransferPerson dtoPerson1 = personService.createPerson(PersonFixtures.erkkiJokinenDto);
    when(repo.createPerson(PersonFixtures.maijaKallioDto))
            .thenReturn(new DocumentPerson(1, PersonFixtures.maijaKallioDto));
    DataTransferPerson dtoPerson2 = personService.createPerson(PersonFixtures.maijaKallioDto);
    when(repo.createPerson(PersonFixtures.jussiLindstromDto))
            .thenReturn(new DocumentPerson(2, PersonFixtures.jussiLindstromDto));
    DataTransferPerson dtoPerson3 = personService.createPerson(PersonFixtures.jussiLindstromDto);

    when(repo.findAll()).thenReturn(
            List.of(
                    new DocumentPerson(0, PersonFixtures.erkkiJokinenDto),
                    new DocumentPerson(1, PersonFixtures.maijaKallioDto),
                    new DocumentPerson(2, PersonFixtures.jussiLindstromDto)
            )
    );

    List<DataTransferPerson> persons = personService.findAll();

    assertEquals(3, persons.size());
  }

  @Test
  void createSeveralPersonsWithDataAndFindAllPagedAndSorted() {
    when(repo.createPerson(PersonFixtures.erkkiJokinenDto))
            .thenReturn(new DocumentPerson(0, PersonFixtures.erkkiJokinenDto));
    DataTransferPerson dtoPerson1 = personService.createPerson(PersonFixtures.erkkiJokinenDto);
    when(repo.createPerson(PersonFixtures.maijaKallioDto))
            .thenReturn(new DocumentPerson(1, PersonFixtures.maijaKallioDto));
    DataTransferPerson dtoPerson2 = personService.createPerson(PersonFixtures.maijaKallioDto);
    when(repo.createPerson(PersonFixtures.jussiLindstromDto))
            .thenReturn(new DocumentPerson(2, PersonFixtures.jussiLindstromDto));
    DataTransferPerson dtoPerson3 = personService.createPerson(PersonFixtures.jussiLindstromDto);

    List<DataTransferPerson> inputPersons = Arrays.asList(dtoPerson1, dtoPerson2, dtoPerson3);
    inputPersons.sort(Comparator.comparing(DataTransferPerson::birthYear));

    Pageable pageable = PageRequest.of(1, 1);

    when(repo.findAll(pageable, PersonsFilter.unfiltered(), null)).thenReturn(
            new PageImpl<DocumentPerson>(
                    List.of(
                            new DocumentPerson(1, PersonFixtures.jussiLindstromDto)
                    ),
                    pageable,
                    3
            )
    );

    Page<DataTransferPerson> persons = personService.findAll(
            pageable,
            PersonsFilter.unfiltered(),
            null
    );

    assertEquals(1, persons.getContent().size());
    assertEquals(inputPersons.get(1).birthYear(), persons.getContent().get(0).birthYear());
  }

  @Test
  void createAndUpdatePersonWithData() {
    when(repo.getNextId()).thenReturn(0);
    int expectedId = repo.getNextId();

    when(repo.createPerson(PersonFixtures.erkkiJokinenDto))
            .thenReturn(new DocumentPerson(expectedId, PersonFixtures.erkkiJokinenDto));
    DataTransferPerson createdPerson = null;

    createdPerson = personService.createPerson(PersonFixtures.erkkiJokinenDto);
    when(repo.existsById(expectedId)).thenReturn(true);
    personService.updateById(expectedId, PersonFixtures.maijaKallioDto);

    DocumentPerson expectedPerson = new DocumentPerson(expectedId, PersonFixtures.maijaKallioDto);
    when(repo.findById(expectedId)).thenReturn(expectedPerson);

    DataTransferPerson resultPerson = personService.findById(expectedId);

    assertNotEquals(expectedPerson.toDataTransferPerson(), createdPerson);
    assertEquals(expectedPerson.toDataTransferPerson(), resultPerson);
  }

  @Test
  void updateByIdThrowsNotFound() {
    when(repo.existsById(-1)).thenReturn(false);

    ResponseStatusException exception = assertThrows(
            ResponseStatusException.class,
            () -> personService.updateById(-1, PersonFixtures.erkkiJokinenDto)
    );
    assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
  }

  @Test
  void updateByIdThrowsBadRequest() {
    ResponseStatusException exception = assertThrows(
            ResponseStatusException.class,
            () -> personService.updateById(0, null)
    );
    assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
  }

  @Test
  void createAndDeletePersonWithData() {
    when(repo.getNextId()).thenReturn(0);
    int expectedId = repo.getNextId();

    when(repo.createPerson(PersonFixtures.erkkiJokinenDto))
            .thenReturn(new DocumentPerson(expectedId, PersonFixtures.erkkiJokinenDto));
    DataTransferPerson createdPerson = personService.createPerson(PersonFixtures.erkkiJokinenDto);
    personService.deleteById(expectedId);
    personService.deleteById(expectedId);
    personService.deleteById(expectedId);
    personService.deleteById(expectedId);
    personService.deleteById(expectedId);

    when(repo.findById(expectedId)).thenReturn(null);

    ResponseStatusException exception = assertThrows(
            ResponseStatusException.class,
            () -> personService.findById(expectedId)
    );
    assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
  }
}
