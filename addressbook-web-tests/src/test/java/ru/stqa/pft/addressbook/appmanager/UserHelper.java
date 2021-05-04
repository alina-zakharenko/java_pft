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

  /**
   * initialization of user
   */

  public void createUser(UserData user) {
    gotoCreateUserPage();
    fillUserInfo(user, true);
    submitUserCreation();
  }

  public void gotoCreateUserPage() {
    click(By.linkText("add new"));
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

  public void submitUserCreation() {
    wd.findElement(By.name("submit")).click();
  }

  /**
   * initialization of user modification
   */
  public void initUserDataModificationLocator() {
    editUserButton();
  }

  public void acceptUserDataModificationLocator() {
    click(By.name("update"));

  }

  public void editUserButton() {
    click(By.xpath("//img[@alt='Edit']"));
  }

  /**
   * delete user
   */
  public void deleteUser() {
    selectUser();
    clickDeleteUserButton();
    getAccept();
  }

  public void selectUser() {
    click(By.name("selected[]"));
  }

  public void getAccept() {
    wd.switchTo().alert().accept();
  }

  public void clickDeleteUserButton() {
    click(By.cssSelector(".left:nth-child(8) > input"));
  }

  public void getCompany(UserData userData) {
    type(By.name("company"), userData.getCompany());
  }

  public boolean isThereAnUser() {
    return isElementPresent(By.name("selected[]"));
  }

  public int getUserCount() {
    return wd.findElements(By.name("selected[]")).size();
  }

//    public void changeUserInfo(UserData userData) {
//    getCompany(userData);
//    updateData();
//  }
}

