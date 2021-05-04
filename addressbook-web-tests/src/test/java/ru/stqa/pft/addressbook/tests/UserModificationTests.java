package ru.stqa.pft.addressbook.tests;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.TestBase;
import ru.stqa.pft.addressbook.model.UserData;

public class UserModificationTests extends TestBase {
  WebDriver wd;

  @Test
  public void testUserModificationTest() throws Exception {
    applicationManager.getNavigationHelper().gotoHomePage();
    if (!applicationManager.getUserHelper().isThereAnUser()) {
      applicationManager.getUserHelper().createUser(new UserData("Ron", "Weasley", "ronWeasley@magic.com", "", "test1"));
    }
    applicationManager.getNavigationHelper().gotoHomePage();
    int before = applicationManager.getUserHelper().getUserCount();
    System.out.println("Количество до " + before);
    applicationManager.getNavigationHelper().gotoHomePage();
    applicationManager.getUserHelper().selectUser(before - 2);
    applicationManager.getUserHelper().initUserDataModificationLocator();
    applicationManager.getUserHelper().fillUserInfo(new UserData("Ivi", "Ivanov", "ii@gmail.com", "Hogwards", "test1"), false);
    applicationManager.getUserHelper().acceptUserDataModificationLocator();
    applicationManager.getNavigationHelper().gotoHomePage();
    int after = applicationManager.getUserHelper().getUserCount();
    System.out.println("Количество после " + before);
    Assert.assertEquals(after, before);
  }


}
