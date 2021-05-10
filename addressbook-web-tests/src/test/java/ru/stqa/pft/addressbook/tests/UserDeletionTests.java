package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.TestBase;
import ru.stqa.pft.addressbook.model.UserData;

import java.util.List;

public class UserDeletionTests extends TestBase {

  @BeforeMethod
  public void ensurePreconditions(){
    app.goTo().gotoHomePage();
    if (!app.getUserHelper().isThereAnUser()) {
      app.getUserHelper().createUser(new UserData("Ron", "Weasley", "ronWeasley@magic.com", "", "test1"));
    }
  }

  @Test //(enabled = false)
  public void testUserDeletionTest() throws Exception {
    List<UserData> before = app.getUserHelper().getUserList(); //список before
    app.getUserHelper().selectUser(before.size() - 1);
    app.getUserHelper().deleteUser();
    app.goTo().gotoHomePage();
    List<UserData> after = app.getUserHelper().getUserList(); //список after
    Assert.assertEquals(after.size(), before.size() - 1); // сравниватся размеры списков

    before.remove(before.size() - 1);
    Assert.assertEquals(before, after);  // сравниватся списки целиком до и после удаления
  }
}


