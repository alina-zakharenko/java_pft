package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.*;

import java.io.File;
import java.util.Iterator;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class RemoveUserFromGroupTest extends TestBase {

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
    app.goTo().homePage();
  }

  @Test //(enabled = false)  Для второго теста реализуйте поиск такого контакта, который добавлен в группу.
  // А если в приложении нет контактов, добавленных в группы, то в таком случае предварительно добавляйте любой контакт в любую группу.
  public void testRemoveUserFromGroup() throws Exception {
    Users allUsersBefore = app.db().users();// список контактов
    Groups allGroups = app.db().groups();// список групп

    UserData userWithGroup = null;
    //UserData userBefore = allUsers.iterator().next();
    GroupData groupData;

    for (Iterator<GroupData> iterator = allGroups.iterator(); iterator.hasNext(); ) {
      groupData = iterator.next();
      //UserData userBefore = allUsers.iterator().next();
      //поиск такого контакта, который добавлен в группу и его удаление
      userWithGroup = app.user().findUserWithGroup(allUsersBefore, groupData);
      if (userWithGroup != null) {
        app.user().deleteFromGroup(userWithGroup, groupData);
        Users allUsersAfter = app.db().users();
        UserData userAfter = allUsersAfter.iterator().next();
//        assertThat(allUsersAfter, equalTo(
//                allUsersBefore.withAdded(userWithGroup.withId(allUsersAfter.stream().mapToInt((u) -> u.getId()).max().getAsInt()))));

        assertThat(userAfter.getGroups().size(), equalTo(userWithGroup.getGroups().size() - 1));
        break;

      }
    }
    //если в приложении нет контактов, добавленных в группы, то в таком случае предварительно добавляйте любой контакт в любую группу.
    if (userWithGroup == null) {
      Users allUsers = app.db().users();// список контактов
      UserData randomUser = allUsers.iterator().next(); // поиск любого контакта
      GroupData randomGroup = allGroups.iterator().next(); // поиск любой группы
      app.goTo().homePage();
      app.user().addToGroup(randomUser, randomGroup);
      Users allUsersAfter = app.db().users();
      UserData userAfter = allUsersAfter.iterator().next();
      assertThat(userAfter.getGroups().size(), equalTo(randomUser.getGroups().size() + 1));
    }

  }
}
