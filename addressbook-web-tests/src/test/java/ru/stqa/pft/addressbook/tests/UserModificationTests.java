package ru.stqa.pft.addressbook.tests;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.TestBase;
import ru.stqa.pft.addressbook.model.UserData;

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
    applicationManager.getNavigationHelper().gotoHomePage();
    applicationManager.getUserHelper().selectUser(before.size() - 1);
    applicationManager.getUserHelper().initUserDataModificationLocator();
    applicationManager.getUserHelper().fillUserInfo(new UserData("Ivi", "Ivanov", "ii@gmail.com", "Hogwards", "test1"), false);
    applicationManager.getUserHelper().acceptUserDataModificationLocator();
    applicationManager.getNavigationHelper().gotoHomePage();
    List<UserData> after = applicationManager.getUserHelper().getUserList();
    System.out.println("Количество после " + after.size());
    Assert.assertEquals(after.size(), before.size());
  }


}
