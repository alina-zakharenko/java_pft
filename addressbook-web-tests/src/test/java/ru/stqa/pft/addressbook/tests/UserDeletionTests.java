package ru.stqa.pft.addressbook.tests;

import org.hamcrest.CoreMatchers;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.TestBase;
import ru.stqa.pft.addressbook.model.UserData;
import ru.stqa.pft.addressbook.model.Users;

import java.io.File;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class UserDeletionTests extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() {
    if (app.db().users().size() == 0) {
      app.goTo().homePage();
      app.user().create(new UserData()
              .withFirstname("Ron").withLastname("Weasley").withEmail("ronWeasley@magic.com").withCompany("")
              .inGroup(new GroupData().withFooter("").withHeader("").withName("test3")).withPhoto(new File("src/test/resources/pft.png")));
              //.withGroup("test1"));
    }
    app.goTo().homePage();
  }

  @Test //(enabled = false)
  public void testUserDeletionTest() throws Exception {
    Users before = app.db().users();
    UserData deletedUser = before.iterator().next();// next() - вернет первый элемент множества
    app.user().delete(deletedUser);
    app.goTo().homePage();
    assertThat(app.user().count(), equalTo(before.size() - 1));
    Users after = app.db().users();
    assertThat(after, CoreMatchers.equalTo(before.without(deletedUser)));
  }


}


