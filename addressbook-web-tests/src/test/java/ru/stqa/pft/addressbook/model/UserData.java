package ru.stqa.pft.addressbook.model;

public class UserData {
  private final String firstname;
  private final String lastname;
  private final String email;
  private final String company;

  public UserData(String firstname, String lastname, String email, String company) {
    this.firstname = firstname;
    this.lastname = lastname;
    this.email = email;
    this.company = company;
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
}
