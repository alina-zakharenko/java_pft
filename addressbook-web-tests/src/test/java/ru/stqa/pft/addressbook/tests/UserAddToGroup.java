package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.*;

import java.io.File;
import java.util.Iterator;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class UserAddToGroup extends TestBase {

  private int maxId;


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
   * 3.
   * 4.
   * */
  public void testAddUserToGroup() throws Exception {
    Users allUsers = app.db().users();// список контактов
    Groups allGroups = app.db().groups();// список групп

    UserData randomUser = allUsers.iterator().next(); // поиск любого контакта
    GroupData randomGroup = allGroups.iterator().next(); // поиск любой группы
    UserData userWithoutGroup = null;
    GroupData groupData = null;
    //все контакты добавлены во все группы
    for (Iterator<GroupData> iterator = allGroups.iterator(); iterator.hasNext(); ) {
      groupData = iterator.next();
      //поиск такого конт{акта, который не добавлен в группу
      userWithoutGroup = app.user().findUserWithoutGroup(allUsers, groupData);
      if (userWithoutGroup != null) { //контакт без группы существует
        app.user().addToGroup(userWithoutGroup, groupData);
        break;
      }
    }
    if (userWithoutGroup == null) {
      //то в таком случае предварительно создавайте новую группу
      GroupData newGroup = new GroupData().withName("New Test Group");
      app.group().create(newGroup);
      app.goTo().homePage();
      app.user().addToGroup(randomUser, newGroup);
    }
  }




//    @Test //(enabled = false)  Для второго теста реализуйте поиск такого контакта, который добавлен в группу.
//    // А если в приложении нет контактов, добавленных в группы, то в таком случае предварительно добавляйте любой контакт в любую группу.
//    public void testDeleteUserFromGroup () throws Exception {
//      Users usersBefore = app.db().users();
//      //System.out.println("Контакты до " + usersBefore);
//      app.goTo().homePage();
//      UserData modifiedUser = usersBefore.iterator().next();
//      UserData user = new UserData().withId(modifiedUser.getId())
//              .withFirstname("Hermine").withLastname("Granger").withEmail("herminegranger@magic.com").withCompany("")
//              .inGroup(new GroupData().withFooter("").withHeader("").withName("test3"))
//              .withWorkPhone("6").withMobilePhone("5").withHomePhone("4").withPhoto(new File("src/test/resources/pft.png"));
//      System.out.println("Контакты до " + user.getGroups());
//      if (user.getGroups().size() != 0) {
//        app.user().deleteFromGroup();
//      } else {
//        app.goTo().groupPage();
//        app.group().create(new GroupData().withName("test4"));
//        app.goTo().homePage();
//        app.user().addToGroup(user);
//      }
//      app.user().deleteFromGroup();
//      //Users usersAfter = app.db().users();
//      //System.out.println("Контакты после " + usersAfter);
//      System.out.println("Контакты после " + user.getGroups());
//      app.goTo().homePage();
//    }
  }
