package ru.stqa.pft.addressbook.tests;

import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.TestBase;
import ru.stqa.pft.addressbook.model.UserData;
import ru.stqa.pft.addressbook.model.Users;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertEquals;

public class UserDeletionTests extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() {
    app.goTo().homePage();
    if (app.user().list().size() == 0) {
      app.user().create(new UserData()
              .withFirstname("Ron").withLastname("Weasley").withEmail("ronWeasley@magic.com").withCompany("").withGroup("test1"));
    }
  }

  @Test //(enabled = false)
  public void testUserDeletionTest() throws Exception {
    Users before = app.user().all(); //список before
    UserData deletedUser = before.iterator().next();// next() - вернет первый элемент множества
    app.user().delete(deletedUser);
    app.goTo().homePage();
    Users after = app.user().all(); //список after
    assertEquals(after.size(), before.size() - 1); // сравниватся размеры списков
    assertThat(after, CoreMatchers.equalTo(before.without(deletedUser)));
  }


}


