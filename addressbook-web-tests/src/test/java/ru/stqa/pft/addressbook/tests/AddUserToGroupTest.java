package ru.stqa.pft.addressbook.tests;

import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.*;

import java.io.File;

public class AddUserToGroupTest extends TestBase {

  int maxId;

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
  public void testAddUserToGroup() throws Exception {
    //группа, в которую добаляем контакт
    GroupData targetGroup = app.db().groups().iterator().next();

    // получаем список всех контактов
    Users allUsersBefore = app.db().users();
    int i = 0; //number of users

    // находим контакт, который не добавлен в группу
    UserData modifiedContact = new UserData();
    for (UserData userBefore : allUsersBefore) {
      if (userBefore.getGroups().size() == 0) {
        modifiedContact = userBefore;
        app.user().addToGroup(modifiedContact, targetGroup);
        break;
      }
      i += 1; //number of users

      // если в приложении уже все контакты добавлены во все группы, то в таком случае предварительно создаем новый контакт
      if (i == allUsersBefore.size()) {
        app.goTo().homePage();
        UserData newUser = new UserData().withFirstname("Test User");
        app.user().create(newUser);
        app.goTo().homePage();
        // добавление контакта в группу
        Users allUsersAfter = app.db().users();
        for (UserData userAfter : allUsersAfter) {
          if (userAfter.getId() > maxId) {
            modifiedContact = userAfter;
            app.goTo().homePage();
            app.user().addToGroup(modifiedContact, targetGroup);
          }
        }
      }
    }
    Groups groupBefore = modifiedContact.ActionsWithGroup(targetGroup, true).getGroups();

    // Проверяем группы именного того контакта, который мы добавляем в группу или удаляем из группы.
    Users allUsersAfter = app.db().users();
    int userId = modifiedContact.getId();
    Groups newGroupsList = allUsersAfter.stream().filter(u -> u.getId() == userId).findFirst().get().getGroups();
    MatcherAssert.assertThat(groupBefore, CoreMatchers.equalTo(newGroupsList));
  }
}
