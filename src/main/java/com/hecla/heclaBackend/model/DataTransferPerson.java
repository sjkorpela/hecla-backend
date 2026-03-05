package com.hecla.heclaBackend.model;

import java.util.ArrayList;
import java.util.List;

public record DataTransferPerson(
  Integer id,
  Integer fatherId,
  Integer motherId,
  DocumentPerson.Gender gender,
  Integer birthYear,
  String birthPlace,
  Boolean deceased,
  Integer deathYear,
  String deathPlace,
  List<FirstName>firstNames,
  List<LastName> lastNames,
  List<AdditionalInfo> additionalInfos
) {

}
