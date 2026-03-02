package com.hecla.heclaBackend.model;

import java.util.ArrayList;

public record DataTransferPerson(
  Integer fatherId,
  Integer motherId,
  Person.Gender gender,
  Integer birthYear,
  String birthPlace,
  Boolean alive,
  Integer deathYear,
  String deathPlace,
  ArrayList<FirstName>firstNames,
  ArrayList<LastName> lastNames,
  ArrayList<AdditionalInfo> additionalInfos
) {

}
