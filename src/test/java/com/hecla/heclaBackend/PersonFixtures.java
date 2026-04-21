package com.hecla.heclaBackend;

import com.hecla.heclaBackend.model.*;

import java.util.List;

public class PersonFixtures {
  public static final DataTransferPerson akuAnkkadDto = new DataTransferPerson(
          null,
          null,
          null,
          DocumentPerson.Gender.MALE,
          1900,
          "Turku",
          true,
          2000,
          "Helsinki",
          List.of(new FirstName("Aku", true)),
          List.of(new LastName("Ankka", true)),
          List.of(new AdditionalInfo("Rekkari", "313"))
  );

  public static final String akuAnkkaJsonString = """
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
                "name": "Aku",
                "nickname": true
              }
            ],
            "lastNames": [
              {
                "name": "Ankka",
                "current": true
              }
            ],
            "additionalInfos": [
              {
                "key": "Rekkari",
                "value": "313"
              }
            ]
          }
          """;

  public static final DataTransferPerson iinesAnkkaDto = new DataTransferPerson(
          null,
          null,
          null,
          DocumentPerson.Gender.FEMALE,
          2000,
          "Joensuu",
          false,
          null,
          null,
          List.of(new FirstName("Iines", true)),
          List.of(new LastName("Ankka", true), new LastName("Hanhi", false)),
          List.of(new AdditionalInfo("Lempiväri", "Pinkki"))
  );

  public static final DataTransferPerson hannuHanhiDto = new DataTransferPerson(
          null,
          null,
          null,
          DocumentPerson.Gender.MALE,
          1980,
          null,
          false,
          null,
          null,
          List.of(new FirstName("Hannu", true), new FirstName("Gladstone", false)),
          List.of(new LastName("Hanhi", true), new LastName("Gander", false)),
          List.of(new AdditionalInfo("Hiusväri", "Blondi"))
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
