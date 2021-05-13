package ru.stqa.pft.addressbook.tests;

import org.hamcrest.CoreMatchers;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.TestBase;
import ru.stqa.pft.addressbook.model.UserData;
import ru.stqa.pft.addressbook.model.Users;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertEquals;

public class UserModificationTests extends TestBase {
  WebDriver wd;

  @BeforeMethod
  public void ensurePreconditions() {
    app.goTo().homePage();
    if (app.user().all().size() == 0) {
      app.user().create(new UserData()
              .withFirstname("Ron").withLastname("Weasley").withEmail("ronWeasley@magic.com").withCompany("").withGroup("test1"));
    }
    app.goTo().homePage();
  }

  @Test //(enabled = false)
  public void testUserModificationTest() throws Exception {
    Users before = app.user().all();
    UserData modifiedUser = before.iterator().next();// next() - вернет первый элемент множества
    UserData user = new UserData()
            .withId(modifiedUser.getId()).withFirstname("Vasja").withLastname("Petrov").withEmail("Ii@magic.com").withCompany("").withGroup("test1");// сохраняем старый идентификатор
    app.goTo().homePage();
    app.user().modify(user);
    app.goTo().homePage();

    assertThat(app.user().count(), equalTo(before.size()));
    Users after = app.user().all();
    assertThat(after, equalTo(before.without(modifiedUser).withAdded(user)));

  }
}
