package ru.stqa.pft.addressbook.model;

import java.util.Objects;

public class UserData {

  private int id = Integer.MAX_VALUE;
  private String firstname;
  private String lastname;
  private String email;
  private String company;
  private String group;


//  public UserData(String firstname, String lastname, String email, String company, String group) {
//    this.id = Integer.MAX_VALUE;
//    this.firstname = firstname;
//    this.lastname = lastname;
//    this.email = email;
//    this.company = company;
//    this.group = group;
//  }
//
//
//  public UserData(int id, String firstname, String lastname, String email, String company, String group) {
//    this.id = id;
//    this.firstname = firstname;
//    this.lastname = lastname;
//    this.email = email;
//    this.company = company;
//    this.group = group;
//  }


  public int getId() {
    return id;
  }

  public UserData withFirstname(String firstname) {
    this.firstname = firstname;
    return this;
  }

  public UserData withLastname(String lastname) {
    this.lastname = lastname;
    return this;
  }

  public UserData withEmail(String email) {
    this.email = email;
    return this;
  }

  public UserData withCompany(String company) {
    this.company = company;
    return this;
  }

  public UserData withGroup(String group) {
    this.group = group;
    return this;
  }

  public UserData withId(int id) {
    this.id = id;
    return this;
  }


  public String getFirstname() {
    return firstname;
  }

  public String getLastname() {
    return lastname;
  }

  public String getEmail() {
    return email;
  }

  public String getCompany() {
    return company;
  }

  public String getGroup() {
    return group;
  }

  @Override
  public String toString() {
    return "UserData{" +
            "id='" + id + '\'' +
            ", firstname='" + firstname + '\'' +
            ", lastname='" + lastname + '\'' +
            '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    UserData userData = (UserData) o;
    return id == userData.id &&
            Objects.equals(firstname, userData.firstname) &&
            Objects.equals(lastname, userData.lastname);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, firstname, lastname);
  }
}
