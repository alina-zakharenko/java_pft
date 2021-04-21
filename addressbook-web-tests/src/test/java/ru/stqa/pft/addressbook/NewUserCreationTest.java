package ru.stqa.pft.addressbook;

import org.testng.annotations.Test;

public class NewUserCreationTest extends TestBase {

  @Test
  public void testNewUserCreation() throws Exception {
    gotoCreateUserPage();
    fillUserInfo(new UserData("Harry", "Potter", "harrypotter@magic.com"));
    submitInfo();
    gotoHomePage();
  }

}