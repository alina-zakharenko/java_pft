package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.TestBase;
import ru.stqa.pft.addressbook.model.UserData;

import java.util.Comparator;
import java.util.List;

public class UserCreationTest extends TestBase {

  @Test //(enabled = false)
  public void testNewUserCreation() throws Exception {
    app.goTo().gotoHomePage();
    List<UserData> before = app.getUserHelper().getUserList();
    System.out.println("Количество до " + before.size());
    //UserData user = new UserData("Harry", "Potter", "harrypotter@magic.com", "", "test2");
    //UserData user = new UserData("Hermine", "Granger", "herminegranger@magic.com", "", "test2");
    UserData user = new UserData("Ron", "Weasley", "ronWeasley@magic.com", "", "test2");
    app.getUserHelper().createUser(user);
    app.goTo().gotoHomePage();
    List<UserData> after = app.getUserHelper().getUserList();
    System.out.println("Количество после " + after.size());
    Assert.assertEquals(after.size(), before.size() + 1);

    before.add(user);
    Comparator<? super UserData> byId = (u1, u2) -> Integer.compare(u1.getId(), u2.getId());
    before.sort(byId);
    after.sort(byId);
    Assert.assertEquals(before, after);

  }
}
