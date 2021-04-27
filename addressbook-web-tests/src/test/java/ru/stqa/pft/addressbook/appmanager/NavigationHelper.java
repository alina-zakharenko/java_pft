package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class NavigationHelper extends HelperBase {

  public NavigationHelper(WebDriver wd) {
    super(wd);
  }

  public void gotoGroupPage() {
    click(By.linkText("groups"));
  }

  public void gotoCreateUserPage() {
    wd.findElement(By.linkText("add new")).click();
  }

  public void gotoHomePage() {
    wd.findElement(By.xpath("//div[@id='header']/a")).click();
  }
}
