package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.firefox.FirefoxDriver;
import ru.stqa.pft.addressbook.model.UserData;

public class UserHelper extends HelperBase {

  public UserHelper(FirefoxDriver wd) {
    super(wd);
  }

  public void fillUserInfo(UserData userData) {
    wd.findElement(By.name("firstname")).click();
    wd.findElement(By.name("firstname")).clear();
    wd.findElement(By.name("firstname")).sendKeys(userData.getFirstname());
    wd.findElement(By.name("lastname")).click();
    wd.findElement(By.name("lastname")).clear();
    wd.findElement(By.name("lastname")).sendKeys(userData.getLastname());
    wd.findElement(By.name("email")).click();
    wd.findElement(By.name("email")).clear();
    wd.findElement(By.name("email")).sendKeys(userData.getEmail());
  }

  public void gotoCreateUserPage() {
    wd.findElement(By.linkText("add new")).click();
  }

  public void editUser() {
    click(By.xpath("//img[@alt='Edit']"));
  }

  public void deleteUser() {
    click(By.name("selected[]"));
    click(By.cssSelector(".left:nth-child(8) > input"));
    wd.switchTo().alert().accept();
  }

  public void modificateUser(UserData userData) {
    type(By.name("company"), userData.getCompany());
    click(By.xpath("(//input[@name='update'])[2]"));
  }


}
