package com.hecla.heclaBackend;


import com.hecla.heclaBackend.model.*;
import com.hecla.heclaBackend.repository.PersonRepo;
import com.hecla.heclaBackend.service.PersonService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@Disabled
public class PersonControllerTests {

  @Autowired
  MockMvcTester mockMvcTester;

  @MockitoBean
  PersonService personService;

  @Test
  void getPersonsReturnsOkAndEmptyList() {
    given(personService.findAll()).willReturn(List.of());

    assertThat(mockMvcTester.get()
            .uri("/api/persons"))
            .hasStatusOk()
            .bodyJson()
            .isEqualTo("[]");
  }

  @Test
  void postPersonReturnOkAndReturnPostedPersonAndGetPersonReturnOkAndPostedPerson() {
    given(personService.createPerson(PersonFixtures.erkkiJokinenDto))
            .willReturn(new DocumentPerson(
                    0,
                    PersonFixtures.erkkiJokinenDto
            ).toDataTransferPerson());

    assertThat(mockMvcTester.post()
            .uri("/api/persons")
            .contentType(MediaType.APPLICATION_JSON)
            .content(PersonFixtures.erkkiJokinenJsonString))
            .hasStatus(HttpStatus.CREATED)
            .bodyJson()
            .extractingPath("$.id")
            .isEqualTo(0);

    given(personService.findById(0))
            .willReturn(new DocumentPerson(
                    0,
                    PersonFixtures.erkkiJokinenDto
            ).toDataTransferPerson());

    assertThat(mockMvcTester.get()
            .uri("/api/persons/0"))
            .hasStatusOk()
            .bodyJson()
            .convertTo(DataTransferPerson.class)
            .satisfies(person -> {
              assertThat(person.id()).isEqualTo(0);
              assertThat(person.fatherId()).isNull();
              assertThat(person.motherId()).isNull();
              assertThat(person.birthYear()).isEqualTo(1900);
              assertThat(person.birthPlace()).isEqualTo("Turku");
              assertThat(person.deceased()).isEqualTo(true);
              assertThat(person.deathYear()).isEqualTo(2000);
              assertThat(person.deathPlace()).isEqualTo("Helsinki");
              assertThat(person.firstNames()).isEqualTo(List.of(
                      new FirstName("Erkki", true)
              ));
              assertThat(person.lastNames()).isEqualTo(List.of(
                      new LastName("Jokinen", true)
              ));
              assertThat(person.additionalInfos()).isEqualTo(List.of(
                      new AdditionalInfo("Kengänkoko", "40")
              ));
            });
  }

  @Test
  void putPersonReturnsOk() {
    given(personService.createPerson(PersonFixtures.erkkiJokinenDto))
            .willReturn(new DocumentPerson(
                    0,
                    PersonFixtures.erkkiJokinenDto
            ).toDataTransferPerson());

    assertThat(mockMvcTester.post()
            .uri("/api/persons")
            .contentType(MediaType.APPLICATION_JSON)
            .content(PersonFixtures.erkkiJokinenJsonString))
            .hasStatus(HttpStatus.CREATED)
            .bodyJson()
            .extractingPath("$.id")
            .isEqualTo(0);

    assertThat(mockMvcTester.put()
            .uri("/api/persons/0")
            .contentType(MediaType.APPLICATION_JSON)
            .content(PersonFixtures.erkkiJokinenJsonString))
            .hasStatus(HttpStatus.OK);
  }

  @Test
  void deletePersonReturnsNoContent() {
    assertThat(mockMvcTester.delete()
            .uri("/api/persons/0"))
            .hasStatus(HttpStatus.NO_CONTENT);
  }
}
