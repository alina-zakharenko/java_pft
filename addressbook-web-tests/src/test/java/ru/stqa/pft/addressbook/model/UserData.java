package ru.stqa.pft.addressbook.model;

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
}
