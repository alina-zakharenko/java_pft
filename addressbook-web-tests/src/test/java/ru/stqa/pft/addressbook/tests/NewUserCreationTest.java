package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.TestBase;
import ru.stqa.pft.addressbook.model.UserData;

public class NewUserCreationTest extends TestBase {

  @Test
  public void testNewUserCreation() throws Exception {
    applicationManager.gotoCreateUserPage();
    applicationManager.fillUserInfo(new UserData("Harry", "Potter", "harrypotter@magic.com"));
    applicationManager.submitInfo();
    applicationManager.gotoHomePage();
  }

}
