package ru.stqa.pft.addressbook.tests;

import org.hamcrest.MatcherAssert;
import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.TestBase;
import ru.stqa.pft.addressbook.model.UserData;
import ru.stqa.pft.addressbook.model.Users;

import java.util.Comparator;
import java.util.List;
import java.util.Set;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.*;

public class UserCreationTest extends TestBase {

  @Test //(enabled = false)
  public void testNewUserCreation() throws Exception {
    app.goTo().homePage();
    Users before = app.user().all();
    //UserData user = new UserData("Harry", "Potter", "harrypotter@magic.com", "", "test2");
    //UserData user = new UserData("Hermine", "Granger", "herminegranger@magic.com", "", "test2");
    UserData user = new UserData().withFirstname("Hermine").withLastname("Granger").withEmail("herminegranger@magic.com").withCompany("").withGroup("test3").withHomePhone("9").withMobilePhone("8").withWorkPhone("7");
    app.user().create(user);
    app.goTo().homePage();
    assertThat(app.user().count(), equalTo(before.size() + 1));
    Users after = app.user().all();
    assertThat(after, equalTo(
            before.withAdded(user.withId(after.stream().mapToInt((u) -> u.getId()).max().getAsInt()))));

  }
}
