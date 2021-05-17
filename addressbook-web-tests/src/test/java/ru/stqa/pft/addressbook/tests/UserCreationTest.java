package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.TestBase;
import ru.stqa.pft.addressbook.model.UserData;
import ru.stqa.pft.addressbook.model.Users;

import java.io.File;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class UserCreationTest extends TestBase {

  @Test
  public void testNewUserCreation() throws Exception {
    app.goTo().homePage();
    Users before = app.user().all();
    //UserData user = new UserData("Harry", "Potter", "harrypotter@magic.com", "", "test2");
    //UserData user = new UserData("Hermine", "Granger", "herminegranger@magic.com", "", "test2");
    File photo = new File("src/test/resources/pft.png");
    UserData user = new UserData().withFirstname("Ron").withLastname("Weasley").withEmail("RonWeasley@magic.com").withCompany("").withGroup("test3").withHomePhone("9").withMobilePhone("8").withWorkPhone("7").withPhoto(photo);
    app.user().create(user);
    app.goTo().homePage();
    assertThat(app.user().count(), equalTo(before.size() + 1));
    Users after = app.user().all();
    assertThat(after, equalTo(
            before.withAdded(user.withId(after.stream().mapToInt((u) -> u.getId()).max().getAsInt()))));

  }

  @Test (enabled = false)
  public void testCurrentDir() {
    File currentDir = new File(".");
    System.out.println(currentDir.getAbsolutePath());
    File photo = new File("src/test/resources/pft.png");
    System.out.println(photo.getAbsolutePath());
    System.out.println(photo.exists());

  }
}
