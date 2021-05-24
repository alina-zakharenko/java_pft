package ru.stqa.pft.addressbook.tests;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.TestBase;
import ru.stqa.pft.addressbook.model.UserData;
import ru.stqa.pft.addressbook.model.Users;

import java.io.File;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class UserModificationTests extends TestBase {
  WebDriver wd;

  @BeforeMethod
  public void ensurePreconditions() {
    if (app.db().users().size() == 0) {
      app.goTo().homePage();
      app.user().create(new UserData()
              .withFirstname("Ron").withLastname("Weasley").withEmail("ronWeasley@magic.com").withCompany("").withGroup("test1"));
    }
    app.goTo().homePage();
  }

  @Test //(enabled = false)
  public void testUserModificationTest() throws Exception {
    Users before = app.db().users();
    UserData modifiedUser = before.iterator().next();// next() - вернет первый элемент множества
    UserData user = new UserData()
            .withId(modifiedUser.getId()).withFirstname("Hermine").withLastname("Granger").withEmail("herminegranger@magic.com").withCompany("").withGroup("test1").withWorkPhone("6").withMobilePhone("5").withHomePhone("4").withPhoto(new File("src/test/resources/pft.png"));
    app.goTo().homePage();
    app.user().modify(user);
    app.goTo().homePage();

    assertThat(app.user().count(), equalTo(before.size()));
    Users after = app.db().users();
    assertThat(after, equalTo(before.without(modifiedUser).withAdded(user)));
    verifyUserListFromUi();
  }
}
