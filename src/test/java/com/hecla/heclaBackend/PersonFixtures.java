package com.hecla.heclaBackend;

import com.hecla.heclaBackend.model.*;

import java.util.List;

public class PersonFixtures {
  public static final DataTransferPerson erkkiJokinenDto = new DataTransferPerson(
          null,
          null,
          null,
          DocumentPerson.Gender.MALE,
          1900,
          "Turku",
          true,
          2000,
          "Helsinki",
          List.of(new FirstName("Erkki", true)),
          List.of(new LastName("Jokinen", true)),
          List.of(new AdditionalInfo("Kengänkoko", "40"))
  );

  public static final String erkkiJokinenJsonString = """
          {
            "fatherId": null,
            "motherId": null,
            "gender": "MALE",
            "birthYear": 1900,
            "birthPlace": "Turku",
            "deceased": true,
            "deathYear": 2000,
            "deathPlace": "Helsinki",
            "firstNames": [
              {
                "name": "Erkki",
                "nickname": true
              }
            ],
            "lastNames": [
              {
                "name": "Jokinen",
                "current": true
              }
            ],
            "additionalInfos": [
              {
                "key": "Kengänkoko",
                "value": "40"
              }
            ]
          }
          """;

  public static final DataTransferPerson maijaKallioDto = new DataTransferPerson(
          null,
          null,
          null,
          DocumentPerson.Gender.FEMALE,
          2000,
          "Joensuu",
          false,
          null,
          null,
          List.of(new FirstName("Maija", true)),
          List.of(new LastName("Kallio", true), new LastName("Kivi", false)),
          List.of(new AdditionalInfo("Lempiväri", "Keltainen"))
  );

  public static final DataTransferPerson jussiLindstromDto = new DataTransferPerson(
          null,
          null,
          null,
          DocumentPerson.Gender.MALE,
          1980,
          null,
          false,
          null,
          null,
          List.of(new FirstName("Jussi", true), new FirstName("Matti", false)),
          List.of(new LastName("Lindström", true)),
          List.of(new AdditionalInfo("Hiusväri", "Vaihtelee"))
  );

  public static final DataTransferPerson allNullDto = new DataTransferPerson(
          null,
          null,
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
  );
}
