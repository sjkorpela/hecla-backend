package com.hecla.heclaBackend.model;

import java.util.ArrayList;

public record DataTransferPerson(
  Integer fatherId,
  Integer motherId,
  DocumentPerson.Gender gender,
  Integer birthYear,
  String birthPlace,
  Boolean deceased,
  Integer deathYear,
  String deathPlace,
  ArrayList<FirstName>firstNames,
  ArrayList<LastName> lastNames,
  ArrayList<AdditionalInfo> additionalInfos
) {

}
