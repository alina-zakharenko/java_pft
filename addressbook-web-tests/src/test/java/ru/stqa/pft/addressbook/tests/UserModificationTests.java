package ru.stqa.pft.addressbook.tests;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.TestBase;
import ru.stqa.pft.addressbook.model.UserData;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

public class UserModificationTests extends TestBase {
  WebDriver wd;

  @Test
  public void testUserModificationTest() throws Exception {
    applicationManager.getNavigationHelper().gotoHomePage();
    if (!applicationManager.getUserHelper().isThereAnUser()) {
      applicationManager.getUserHelper().createUser(new UserData("Ron", "Weasley", "ronWeasley@magic.com", "", "test1"));
    }
    applicationManager.getNavigationHelper().gotoHomePage();
    List<UserData> before = applicationManager.getUserHelper().getUserList();
    System.out.println("Количество до " + before.size());
    //applicationManager.getNavigationHelper().gotoHomePage();
    applicationManager.getUserHelper().selectUser(before.size() - 1);
    applicationManager.getUserHelper().initUserDataModificationLocator();
    UserData user = new UserData (before.get(before.size() - 1).getId(),"Ivi", "Ivanov", "", "", "");// сохраняем старый идентификатор
    applicationManager.getUserHelper().fillUserInfo(user, false);
    applicationManager.getUserHelper().acceptUserDataModificationLocator();
    applicationManager.getNavigationHelper().gotoHomePage();
    List<UserData> after = applicationManager.getUserHelper().getUserList();
    Assert.assertEquals(after.size(), before.size());
    System.out.println("Количество после " + after.size());

    before.remove(before.size() - 1);
    before.add(user);
    System.out.println("HashSet before" + new HashSet<Object>(before));
    System.out.println("HashSet after" + new HashSet<Object>(after));
    Comparator<? super UserData> byId = (u1, u2) -> Integer.compare(u1.getId(), u2.getId());
    before.sort(byId);
    after.sort(byId);
    Assert.assertEquals(before, after);

  }


}
