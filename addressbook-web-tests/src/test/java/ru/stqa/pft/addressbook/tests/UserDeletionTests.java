package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.TestBase;
import ru.stqa.pft.addressbook.model.UserData;

import java.util.List;
import java.util.Set;

public class UserDeletionTests extends TestBase {

  @BeforeMethod
  public void ensurePreconditions(){
    app.goTo().homePage();
    if (app.user().list().size()==0) {
      app.user().create(new UserData()
              .withFirstname("Ron").withLastname("Weasley").withEmail("ronWeasley@magic.com").withCompany("").withGroup("test1"));
    }
  }

  @Test //(enabled = false)
  public void testUserDeletionTest() throws Exception {
    Set<UserData> before = app.user().all(); //список before
    UserData deletedUser = before.iterator().next();// next() - вернет первый элемент множества
    app.user().delete(deletedUser);
    app.goTo().homePage();
    Set<UserData> after = app.user().all(); //список after
    Assert.assertEquals(after.size(), before.size() - 1); // сравниватся размеры списков

    before.remove(deletedUser);
    Assert.assertEquals(before, after);  // сравниватся списки целиком до и после удаления
  }


}


