package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.stqa.pft.addressbook.model.UserData;

import java.util.ArrayList;
import java.util.List;

public class UserHelper extends HelperBase {

  public UserHelper(WebDriver wd) {
    super(wd);
  }

  /**
   * initialization of user
   */


  public void modifyUser(int index, UserData user) {
    editUser(index);
    fillUserInfo(user, false);
    acceptUserDataModificationLocator();
  }

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


  public void acceptUserDataModificationLocator() {
    click(By.name("update"));

  }

  public void editUser(int index) {
    wd.findElements(By.xpath("//img[@alt='Edit']")).get(index).click();
  }


  /**
   * delete user
   */
  public void deleteUser() {
    clickDeleteUserButton();
    getAccept();
  }

  public void selectUser(int index) {
    wd.findElements(By.name("selected[]")).get(index).click();
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

  public List<UserData> getUserList() {
    List<UserData> users = new ArrayList<>();  //создаем список, который будет заполняться
    List<WebElement> elements = wd.findElements(By.name("entry")); // список объкетов типа WebElement - найти все элементы с именем entry
    for (WebElement element : elements) {
      element.findElements(By.tagName("td")); //переменная element пробегает по всем cells
      String firstname = element.findElement(By.xpath(".//td[3]")).getText();
      String lastname = element.findElement(By.xpath(".//td[2]")).getText();
      //int id = Integer.parseInt(element.findElement(By.cssSelector("[name='entry']>.center>input")).getAttribute("value"));// передается в конструктор и используется при сравнении

      int id = Integer.parseInt(element.findElement(By.xpath(".//td/input")).getAttribute("value"));
      UserData user = new UserData(id, firstname, lastname, null, null, null);
      users.add(user);
    }
    return users;
  }


//    public void changeUserInfo(UserData userData) {
//    getCompany(userData);
//    updateData();
//  }
}

