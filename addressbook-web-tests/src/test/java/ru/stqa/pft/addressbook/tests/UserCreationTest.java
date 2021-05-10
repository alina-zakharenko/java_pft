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
    app.goTo().homePage();
    List<UserData> before = app.user().list();
    //UserData user = new UserData("Harry", "Potter", "harrypotter@magic.com", "", "test2");
    //UserData user = new UserData("Hermine", "Granger", "herminegranger@magic.com", "", "test2");
    UserData user = new UserData().withFirstname("Ron").withLastname("Weasley").withEmail("ronWeasley@magic.com").withCompany("").withGroup("test2");
    app.user().create(user);
    app.goTo().homePage();
    List<UserData> after = app.user().list();
    System.out.println("Количество после " + after.size());
    Assert.assertEquals(after.size(), before.size() + 1);

    before.add(user);
    Comparator<? super UserData> byId = (u1, u2) -> Integer.compare(u1.getId(), u2.getId());
    before.sort(byId);
    after.sort(byId);
    Assert.assertEquals(before, after);

  }
}
