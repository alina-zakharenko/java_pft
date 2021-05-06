package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.TestBase;
import ru.stqa.pft.addressbook.model.UserData;

import java.util.List;

public class UserDeletionTests extends TestBase {
  @Test
  public void testUserDeletionTest() throws Exception {
    applicationManager.getNavigationHelper().gotoHomePage();
    if (!applicationManager.getUserHelper().isThereAnUser()) {
      applicationManager.getUserHelper().createUser(new UserData("Ron", "Weasley", "ronWeasley@magic.com", "", "test1"));
    }
    List<UserData> before = applicationManager.getUserHelper().getUserList(); //список before
    applicationManager.getUserHelper().selectUser(before.size() - 1);
    applicationManager.getUserHelper().deleteUser();
    applicationManager.getNavigationHelper().gotoHomePage();
    List<UserData> after = applicationManager.getUserHelper().getUserList(); //список after
    Assert.assertEquals(after.size(), before.size() - 1); // сравниватся размеры списков

    before.remove(before.size() - 1);
    Assert.assertEquals(before, after);  // сравниватся списки целиком до и после удаления
  }
}


