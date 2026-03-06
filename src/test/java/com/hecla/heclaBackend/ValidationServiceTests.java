package com.hecla.heclaBackend;

import com.hecla.heclaBackend.model.DataTransferPerson;
import com.hecla.heclaBackend.repository.PersonRepo;
import com.hecla.heclaBackend.service.PersonService;
import com.hecla.heclaBackend.service.ValidationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ValidationServiceTests {

    @Mock
    PersonRepo repo;

    @InjectMocks
    ValidationService validationService;

    @Test
    void createPersonWithInvalidFatherId() {
        when(repo.existsById(-1)).thenReturn(false);
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> validationService.validateDataTransferPerson(new DataTransferPerson(
                null,
                -1,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null
        )));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
    }

    @Test
    void createPersonWithValidFatherId() {
        when(repo.existsById(1)).thenReturn(true);
        validationService.validateDataTransferPerson(new DataTransferPerson(
                null,
                1,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null
        ));
    }

    @Test
    void createPersonWithInvalidMotherId() {
        when(repo.existsById(-1)).thenReturn(false);
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> validationService.validateDataTransferPerson(new DataTransferPerson(
                null,
                null,
                -1,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null
        )));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
    }

    @Test
    void createPersonWithValidMotherId() {
        when(repo.existsById(1)).thenReturn(true);
        validationService.validateDataTransferPerson(new DataTransferPerson(
                null,
                null,
                1,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null
        ));
    }
}
