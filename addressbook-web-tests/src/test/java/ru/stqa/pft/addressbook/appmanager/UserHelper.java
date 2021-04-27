package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.stqa.pft.addressbook.model.UserData;

public class UserHelper extends HelperBase {

  public UserHelper(WebDriver wd) {
    super(wd);
  }

  public void fillUserInfo(UserData userData, boolean creation) {
    typeUserData(By.name("firstname"), userData.getFirstname());
    typeUserData(By.name("lastname"), userData.getLastname());
    typeUserData(By.name("email"), userData.getEmail());
    typeUserData(By.name("company"), userData.getCompany());
    if (creation) {
      new Select(wd.findElement(By.name("new_group"))).selectByVisibleText(userData.getGroup());
    } else {
      Assert.assertFalse(isElementPresent(By.name("new_group")));
    }
  }


  private void typeUserData(By locator, String text) {
    wd.findElement(locator).click();
    wd.findElement(locator).clear();
    wd.findElement(locator).sendKeys(text);
  }


  public void editUser() {
    click(By.xpath("//img[@alt='Edit']"));
  }

  public void deleteUser() {
    selectUser();
    clickDeleteUserButton();
    getAccept();
  }

  private void getAccept() {
    wd.switchTo().alert().accept();
  }

  private void clickDeleteUserButton() {
    click(By.cssSelector(".left:nth-child(8) > input"));
  }

  private void selectUser() {
    click(By.name("selected[]"));
  }

//  public void changeUserInfo(UserData userData) {
//    getCompany(userData);
//    updateData();
//  }

  private void getCompany(UserData userData) {
    type(By.name("company"), userData.getCompany());
  }

  private void updateData() {
    click(By.xpath("(//input[@name='update'])[2]"));
  }

}
