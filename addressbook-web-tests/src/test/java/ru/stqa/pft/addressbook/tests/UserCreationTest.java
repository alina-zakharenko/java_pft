package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.TestBase;
import ru.stqa.pft.addressbook.model.UserData;

import java.util.List;

public class UserCreationTest extends TestBase {

  @Test
  public void testNewUserCreation() throws Exception {
    List<UserData> before = applicationManager.getUserHelper().getUserList();
    System.out.println("Количество до " + before.size());
    applicationManager.getUserHelper().createUser(new UserData("Harry", "Potter", "harrypotter@magic.com", "", "test1"));
    applicationManager.getUserHelper().createUser(new UserData("Hermine", "Granger", "herminegranger@magic.com", "", "test1"));
    applicationManager.getUserHelper().createUser(new UserData("Ron", "Weasley", "ronWeasley@magic.com", "", "test1"));
    applicationManager.getNavigationHelper().gotoHomePage();
    List<UserData> after = applicationManager.getUserHelper().getUserList();
    System.out.println("Количество после " + after.size());
    Assert.assertEquals(after.size(), before.size() + 3);

  }
}
