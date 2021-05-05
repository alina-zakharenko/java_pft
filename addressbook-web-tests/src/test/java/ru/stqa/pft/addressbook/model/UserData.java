package ru.stqa.pft.addressbook.model;

import java.util.Objects;

public class UserData {

  private final String firstname;
  private final String lastname;
  private final String email;
  private final String company;
  private String group;

  public UserData(String firstname, String lastname, String email, String company, String group) {
    this.firstname = firstname;
    this.lastname = lastname;
    this.email = email;
    this.company = company;
    this.group = group;
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
            "firstname='" + firstname + '\'' +
            "lastname='" + lastname + '\'' +
            '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    UserData userData = (UserData) o;
    return Objects.equals(firstname, userData.firstname) &&
            Objects.equals(lastname, userData.lastname);
  }

  @Override
  public int hashCode() {
    return Objects.hash(firstname, lastname);
  }
}
