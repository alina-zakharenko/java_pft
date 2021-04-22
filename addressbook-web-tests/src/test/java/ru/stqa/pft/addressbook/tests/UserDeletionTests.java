package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.TestBase;

public class UserDeletionTests extends TestBase {
  @Test
  public void testUserDeletionTest() throws Exception {
    applicationManager.gotoHomePage();
    applicationManager.getUserHelper().deleteUser();
    applicationManager.gotoHomePage();
  }

}
