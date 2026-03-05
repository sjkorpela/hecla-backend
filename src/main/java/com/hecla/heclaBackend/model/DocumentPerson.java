package com.hecla.heclaBackend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "Person")
public class DocumentPerson {

  public enum Gender {
    MALE,
    FEMALE
  }

  @Id
  private int id;

  private Integer fatherId;
  private Integer motherId;

  private Gender gender;

  private Integer birthYear;
  private String birthPlace;
  private Boolean deceased;
  private Integer deathYear;
  private String deathPlace;

  private ArrayList<FirstName> firstNames;
  private ArrayList<LastName> lastNames;
  private ArrayList<AdditionalInfo> additionalInfos;

  public DocumentPerson(DataTransferPerson dto) {
    setId(dto.id());
    setFatherId(dto.fatherId());
    setMotherId(dto.motherId());
    setGender(dto.gender());
    setBirthYear(dto.birthYear());
    setBirthPlace(dto.birthPlace());
    setDeceased(dto.deceased());
    setDeathYear(dto.deathYear());
    setDeathPlace(dto.deathPlace());
    setFirstNames(dto.firstNames());
    setLastNames(dto.lastNames());
    setAdditionalInfos(dto.additionalInfos());
  }

  public DocumentPerson(DataTransferPerson dto, int id) {
    setId(id);
    setFatherId(dto.fatherId());
    setMotherId(dto.motherId());
    setGender(dto.gender());
    setBirthYear(dto.birthYear());
    setBirthPlace(dto.birthPlace());
    setDeceased(dto.deceased());
    setDeathYear(dto.deathYear());
    setDeathPlace(dto.deathPlace());
    setFirstNames(dto.firstNames());
    setLastNames(dto.lastNames());
    setAdditionalInfos(dto.additionalInfos());
  }

  public DataTransferPerson toDataTransferPerson() {
    return new DataTransferPerson(
            id,
            fatherId,
            motherId,
            gender,
            birthYear,
            birthPlace,
            deceased,
            deathYear,
            deathPlace,
            firstNames,
            lastNames,
            additionalInfos
    );
  }
}