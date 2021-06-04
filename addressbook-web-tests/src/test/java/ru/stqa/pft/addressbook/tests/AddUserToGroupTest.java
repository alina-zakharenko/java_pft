package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.*;

import java.io.File;
import java.util.Iterator;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;

public class AddUserToGroupTest extends TestBase {

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

  @Test //(enabled = false)  Для первого теста реализуйте поиск такого контакта, который не добавлен в группу.
  // А если в приложении уже все контакты добавлены во все группы, то в таком случае предварительно создавайте новую группу.

  /*
   * 1. найти контакт, который не добавлен в группу
   * 1а. если в приложении уже все контакты добавлены во все группы, то в таком случае предварительно создавайте новую группу
   * 2. добавить контакт в группу
   * */
  public void testAddUserToGroup() throws Exception {
    Users allUsers = app.db().users();// список контактов
    Groups allGroups = app.db().groups();// список групп

//    GroupData randomGroup = allGroups.iterator().next(); // поиск любой группы
    UserData userWithoutGroup = null;
    UserData userBefore = allUsers.iterator().next();
    GroupData groupData;

    //все контакты добавлены во все группы
    for (Iterator<GroupData> iterator = allGroups.iterator(); iterator.hasNext(); ) {
      groupData = iterator.next();
      //поиск такого контакта, который не добавлен в группу
      userWithoutGroup = app.user().findUserWithoutGroup(allUsers, groupData);
      if (userWithoutGroup != null) { //контакт без группы существует
        app.user().addToGroup(userWithoutGroup, groupData);
        break;
      }
      UserData userAfter = allUsers.iterator().next();

      //assertThat(userWithoutGroup, equalTo(userWithoutGroupAfter.getGroups().size() + 1));
      assertThat(userAfter.getGroups().withAdded(groupData).size(), greaterThan(userBefore.getGroups().without(groupData).size()));
    }
    if (userWithoutGroup == null) {
      //то в таком случае предварительно создавайте новую группу
      UserData randomUser = allUsers.iterator().next(); // поиск любого контакта
      GroupData newGroup = new GroupData().withName("New Test Group 2");
      app.goTo().groupPage();
      app.group().create(newGroup);
      app.goTo().homePage();
      app.user().addToGroup(randomUser, newGroup);
      UserData randomUserAfter = allUsers.iterator().next(); // поиск любого контакта

      //assertThat(randomUserAfter.getGroups().withAdded(newGroup).size() , equalTo(randomUser.getGroups().withAdded(newGroup).size()));
      assertThat(randomUserAfter.getGroups().withAdded(newGroup).size(), greaterThan(randomUser.getGroups().without(newGroup).size()));
    }
  }
}
