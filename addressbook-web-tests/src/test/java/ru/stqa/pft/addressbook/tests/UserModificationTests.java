package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.TestBase;
import ru.stqa.pft.addressbook.model.UserData;

public class UserModificationTests extends TestBase {
  @Test
  public void testUserModificationTest() throws Exception {
    applicationManager.gotoHomePage();
    applicationManager.getUserHelper().editUser();
    applicationManager.getUserHelper().modificateUser(new UserData("", "", "", "Hogwards"));
    applicationManager.gotoHomePage();
  }


}
