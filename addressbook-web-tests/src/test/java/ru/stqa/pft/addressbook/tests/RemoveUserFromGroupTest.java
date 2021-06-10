package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.*;

import java.io.File;

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

  @Test //(enabled = false)
  public void testRemoveUserFromGroup() throws Exception {
    GroupData modifiedGroup = app.db().groups().iterator().next();
    // создать изменяемый контакт
    UserData modifiedContact = new UserData();
    // получить список всех контактов
    Users usersBefore = app.db().users();

    int i = 0; //number of users
    //поиск котакта с группой
    for (UserData user : usersBefore) {
      if (user.getGroups().size() != 0) {
        Groups groupsList;
        groupsList = user.getGroups();
        for (GroupData group : groupsList) {
          if (group.getId() == modifiedGroup.getId()) {
            modifiedContact = user;
          }
          break;
        }
        break;
      }
      i += 1; //number of users
    }

    if (i == usersBefore.size()) {
      //поиск котакта без группы и добавление в группу
      for (UserData user : usersBefore) {
        if (user.getGroups().size() == 0) {
          modifiedContact = user;
          app.user().addToGroup(modifiedContact, modifiedGroup);
          break;
        }
      }
    }
    app.goTo().homePage();
    app.user().selectGroup(modifiedGroup);
    app.user().deleteFromGroup(modifiedContact);

    Groups groupBefore = modifiedContact.ActionsWithGroup(modifiedGroup, false).getGroups();

    // Проверяем группы именного того контакта, который мы удаляем из группы
    Users usersAfter = app.db().users();
    int userId = modifiedContact.getId();
    Groups newGroupsList = usersAfter.stream().filter(u -> u.getId() == userId).findFirst().get().getGroups();
    assertThat(groupBefore, equalTo(newGroupsList));
  }
}
