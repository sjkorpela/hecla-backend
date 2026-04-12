package com.hecla.heclaBackend.model;

import org.springframework.web.bind.annotation.PathVariable;

public record PersonsFilter(
  Boolean deceased,
  DocumentPerson.Gender gender,
  Integer bornAfter,
  Integer bornBefore,
  Integer diedAfter,
  Integer diedBefore
) {
  public static PersonsFilter unfiltered() {
    return new PersonsFilter(
            null,
            null,
            null,
            null,
            null,
            null
    );
  }
}
