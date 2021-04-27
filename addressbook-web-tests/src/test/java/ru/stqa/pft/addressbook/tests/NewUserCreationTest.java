package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.TestBase;
import ru.stqa.pft.addressbook.model.UserData;

public class NewUserCreationTest extends TestBase {

  @Test
  public void testNewUserCreation() throws Exception {
    applicationManager.getNavigationHelper().gotoCreateUserPage();
    applicationManager.getUserHelper().fillUserInfo(new UserData("Harry", "Potter", "harrypotter@magic.com", "", "test1"), true);
    applicationManager.submitInfo();
    applicationManager.getNavigationHelper().gotoCreateUserPage();
    applicationManager.getUserHelper().fillUserInfo(new UserData("Hermine", "Granger", "herminegranger@magic.com", "", "test2"), true);
    applicationManager.submitInfo();
    applicationManager.getNavigationHelper().gotoCreateUserPage();
    applicationManager.getUserHelper().fillUserInfo(new UserData("Ron", "Weasley", "ronWeasley@magic.com", "", "test3"), true);
    applicationManager.submitInfo();
    applicationManager.gotoHomePage();
  }

}
