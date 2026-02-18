package com.hecla.heclaBackend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "Person")
public class Person {

  public enum Gender {
    MALE,
    FEMALE
  }

  @Id
  private int id;

  private int fatherId;
  private int motherId;

  private Gender gender;

  private int birthYear;
  private String birthPlace;
  private boolean alive;
  private int deathYear;
  private String deathPlace;

}