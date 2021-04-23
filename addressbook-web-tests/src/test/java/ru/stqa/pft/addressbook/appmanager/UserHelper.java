package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.firefox.FirefoxDriver;
import ru.stqa.pft.addressbook.model.UserData;

public class UserHelper extends HelperBase {

  public UserHelper(FirefoxDriver wd) {
    super(wd);
  }

  public void fillUserInfo(UserData userData) {
    typeUserData(By.name("firstname"), userData.getFirstname());
    typeUserData(By.name("lastname"), userData.getLastname());
    typeUserData(By.name("email"), userData.getEmail());
  }

  private void typeUserData(By locator, String text) {
    wd.findElement(locator).click();
    wd.findElement(locator).clear();
    wd.findElement(locator).sendKeys(text);
  }

  public void gotoCreateUserPage() {
    wd.findElement(By.linkText("add new")).click();
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

  public void changeUserInfo(UserData userData) {
    getCompany(userData);
    updateData();
  }

  private void getCompany(UserData userData) {
    type(By.name("company"), userData.getCompany());
  }

  private void updateData() {
    click(By.xpath("(//input[@name='update'])[2]"));
  }


}
