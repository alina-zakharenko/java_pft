package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.*;

import java.io.File;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class UserAddToGroup extends TestBase {

  @BeforeMethod
  public void ensurePreconditionsUsers() {
    if (app.db().users().size() == 0) {
      app.goTo().homePage();
      app.user().create(new UserData()
              .withFirstname("Hermine").withLastname("Weasley").withEmail("ronWeasley@magic.com").withCompany("").withPhoto(new File("src/test/resources/pft.png")));
      //.withGroup("test1"));
    }
    app.goTo().homePage();
  }

  @BeforeMethod
  public void ensurePreconditionsGroups() {
    if (app.db().groups().size() == 0) {
      app.goTo().groupPage();
      app.group().create(new GroupData().withName("test1"));
    }
  }

  @Test //(enabled = false)
  public void testAddUserToGroup() throws Exception {
    Users usersBefore = app.db().users();
    System.out.println("Контакты до " + usersBefore);
    app.goTo().homePage();
    UserData user = new UserData();
    app.user().addToGroup(user);
    Users usersAfter = app.db().users();
    System.out.println("Контакты после " + usersAfter);
    assertThat(usersBefore.size(), equalTo(usersAfter.size()));
    app.goTo().homePage();
  }


  @Test //(enabled = false)
  public void testDeleteUserFromGroup() throws Exception {
    Users usersBefore = app.db().users();
    System.out.println("Контакты до " + usersBefore);
    app.goTo().homePage();
    app.user().deleteFromGroup();
    Users usersAfter = app.db().users();
    System.out.println("Контакты после " + usersAfter);
    app.goTo().homePage();
  }
}
