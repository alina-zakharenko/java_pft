package ru.stqa.pft.addressbook.tests;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.TestBase;
import ru.stqa.pft.addressbook.model.UserData;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

public class UserModificationTests extends TestBase {
  WebDriver wd;

  @BeforeMethod
  public void ensurePreconditions(){
    app.goTo().gotoHomePage();
    if (!app.getUserHelper().isThereAnUser()) {
      app.getUserHelper().createUser(new UserData("Ron", "Weasley", "ronWeasley@magic.com", "", "test1"));
    }
  }

  @Test //(enabled = false)
  public void testUserModificationTest() throws Exception {
    List<UserData> before = app.getUserHelper().getUserList();
    int index = before.size() - 1;
    UserData user = new UserData(before.get(index).getId(), "Ivi", "Ivanov", "", "", "");// сохраняем старый идентификатор
    app.goTo().gotoHomePage();
    app.getUserHelper().modifyUser(index, user);
    app.goTo().gotoHomePage();
    List<UserData> after = app.getUserHelper().getUserList();
    Assert.assertEquals(after.size(), before.size());
    System.out.println("Количество после " + after.size());

    before.remove(index);
    before.add(user);
    System.out.println("HashSet before" + new HashSet<Object>(before));
    System.out.println("HashSet after" + new HashSet<Object>(after));
    Comparator<? super UserData> byId = (u1, u2) -> Integer.compare(u1.getId(), u2.getId());
    before.sort(byId);
    after.sort(byId);
    Assert.assertEquals(before, after);

  }



}
