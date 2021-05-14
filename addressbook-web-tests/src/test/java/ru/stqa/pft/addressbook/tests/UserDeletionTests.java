package ru.stqa.pft.addressbook.tests;

import org.hamcrest.CoreMatchers;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.TestBase;
import ru.stqa.pft.addressbook.model.UserData;
import ru.stqa.pft.addressbook.model.Users;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class UserDeletionTests extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() {
    app.goTo().homePage();
    if (app.user().all().size() == 0) {
      app.user().create(new UserData()
              .withFirstname("Ron").withLastname("Weasley").withEmail("ronWeasley@magic.com").withCompany("").withGroup("test1").withHomePhone("555").withMobilePhone("444").withWorkPhone("333"));

    }
  }

  @Test //(enabled = false)
  public void testUserDeletionTest() throws Exception {
    Users before = app.user().all(); //список before
    UserData deletedUser = before.iterator().next();// next() - вернет первый элемент множества
    app.user().delete(deletedUser);
    app.goTo().homePage();
    assertThat(app.user().count(), equalTo(before.size() - 1));
    Users after = app.user().all();
    assertThat(after, CoreMatchers.equalTo(before.without(deletedUser)));
  }


}


