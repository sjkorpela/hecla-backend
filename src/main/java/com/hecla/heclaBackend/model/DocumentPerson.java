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
  private boolean alive;
  private Integer deathYear;
  private String deathPlace;

  private ArrayList<FirstName> firstNames;
  private ArrayList<LastName> lastNames;
  private ArrayList<AdditionalInfo> additionalInfos;

  public DocumentPerson(int id, DataTransferPerson dto) {
    setId(id);

    setFatherId(dto.fatherId());
    setMotherId(dto.motherId());
    setGender(dto.gender()); // Person must have gender? Discuss in weekly...
    setBirthYear(dto.birthYear());
    setBirthPlace(dto.birthPlace());

    if (dto.alive() == null) {
      setAlive(dto.deathYear() == null && dto.deathPlace() == null);
    } else {
      setAlive(dto.alive());
    }

    setDeathYear(dto.deathYear());
    setDeathPlace(dto.deathPlace());
    setFirstNames(dto.firstNames()); // Person must have at least 1 name? Discuss in weekly...
    setLastNames(dto.lastNames());
    setAdditionalInfos(dto.additionalInfos());
  }

  public DataTransferPerson toDataTransferPerson() {
    return new DataTransferPerson(
            fatherId,
            motherId,
            gender,
            birthYear,
            birthPlace,
            alive,
            deathYear,
            deathPlace,
            firstNames,
            lastNames,
            additionalInfos
    );
  }
}