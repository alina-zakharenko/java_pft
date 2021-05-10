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
    app.goTo().homePage();
    if (app.user().list().size()==0) {
      app.user().create(new UserData("Ron", "Weasley", "ronWeasley@magic.com", "", "test1"));
    }
  }

  @Test //(enabled = false)
  public void testUserDeletionTest() throws Exception {
    List<UserData> before = app.user().list(); //список before
    int index = before.size() - 1;
    app.user().delete(index);
    app.goTo().homePage();
    List<UserData> after = app.user().list(); //список after
    Assert.assertEquals(after.size(), before.size() - 1); // сравниватся размеры списков

    before.remove(index);
    Assert.assertEquals(before, after);  // сравниватся списки целиком до и после удаления
  }


}


