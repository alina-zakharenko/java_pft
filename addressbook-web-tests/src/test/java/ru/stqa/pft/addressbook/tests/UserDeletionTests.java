package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.TestBase;
import ru.stqa.pft.addressbook.model.UserData;

public class UserDeletionTests extends TestBase {
  @Test
  public void testUserDeletionTest() throws Exception {
    applicationManager.getNavigationHelper().gotoHomePage();
    if (!applicationManager.getUserHelper().isThereAnUser()) {
      applicationManager.getUserHelper().createUser(new UserData("Ron", "Weasley", "ronWeasley@magic.com", "", "test1"));
    }
    applicationManager.getNavigationHelper().gotoHomePage();
    applicationManager.getUserHelper().deleteUser();
  }

}
