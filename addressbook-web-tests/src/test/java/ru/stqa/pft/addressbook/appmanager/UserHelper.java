package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.stqa.pft.addressbook.model.Groups;
import ru.stqa.pft.addressbook.model.UserData;
import ru.stqa.pft.addressbook.model.Users;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UserHelper extends HelperBase {

  public UserHelper(WebDriver wd) {
    super(wd);
  }

  //create user
  public void create(UserData user) {
    gotoCreateUserPage();
    fillUserInfo(user, true);
    submitUserCreation();
    userCash = null;
  }

  public void gotoCreateUserPage() {
    click(By.linkText("add new"));
  }

  public void submitUserCreation() {
    wd.findElement(By.name("submit")).click();
  }


  //modify user
  public void modify(int index, UserData user) {
    editUser(index);
    fillUserInfo(user, false);
    acceptUserDataModificationLocator();
  }

  public void modify(UserData user) {
    selectUserById(user.getId());
    editUserButton();
    fillUserInfo(user, false);
    acceptUserDataModificationLocator();
    userCash = null;
  }

  public void editUser(int index) {
    wd.findElements(By.xpath("//img[@alt='Edit']")).get(index).click();
  }

  public void editUserButton() {
    click(By.xpath("//img[@alt='Edit']"));
  }

  public void acceptUserDataModificationLocator() {
    click(By.name("update"));
  }


  //delete user
  public void delete(int index) {
    selectUser(index);
    deleteUser();
  }


  public void delete(UserData user) {
    selectUserById(user.getId());
    deleteUser();
    userCash = null;
  }

  public void selectUser(int index) {
    wd.findElements(By.name("selected[]")).get(index).click();
  }

  public void selectUserById(int id) {
    wd.findElement(By.cssSelector("input[value='" + id + "']")).click();
  }

  public void deleteUser() {
    clickDeleteUserButton();
    getAccept();
  }

  public void clickDeleteUserButton() {
    click(By.cssSelector(".left:nth-child(8) > input"));
  }

  public void getAccept() {
    wd.switchTo().alert().accept();
  }

  //data
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


//  public List<UserData> list() {
//    List<UserData> users = new ArrayList<>();  //создаем список, который будет заполняться
//    List<WebElement> elements = wd.findElements(By.name("entry")); // список объкетов типа WebElement - найти все элементы с именем entry
//    for (WebElement element : elements) {
//      element.findElements(By.tagName("td")); //переменная element пробегает по всем cells
//      String firstname = element.findElement(By.xpath(".//td[3]")).getText();
//      String lastname = element.findElement(By.xpath(".//td[2]")).getText();
//      //int id = Integer.parseInt(element.findElement(By.cssSelector("[name='entry']>.center>input")).getAttribute("value"));// передается в конструктор и используется при сравнении
//
//      int id = Integer.parseInt(element.findElement(By.xpath(".//td/input")).getAttribute("value"));
//      users.add(new UserData().withId(id).withFirstname(firstname).withLastname(lastname));
//    }
//    return users;
//  }

  private Users userCash = null;

  public Users all() {
    if (userCash != null) {
      return new Users(userCash);
    }
    userCash = new Users();  //создаем список, который будет заполняться
    List<WebElement> elements = wd.findElements(By.name("entry")); // список объкетов типа WebElement - найти все элементы с именем entry
    for (WebElement element : elements) {
      element.findElements(By.tagName("td")); //переменная element пробегает по всем cells
      String firstname = element.findElement(By.xpath(".//td[3]")).getText();
      String lastname = element.findElement(By.xpath(".//td[2]")).getText();
      //int id = Integer.parseInt(element.findElement(By.cssSelector("[name='entry']>.center>input")).getAttribute("value"));
      int id = Integer.parseInt(element.findElement(By.xpath(".//td/input")).getAttribute("value"));// передается в конструктор и используется при сравнении
      userCash.add(new UserData().withId(id).withFirstname(firstname).withLastname(lastname));
    }
    return  new Users(userCash);
  }

  //  public void getCompany(UserData userData) {
//    type(By.name("company"), userData.getCompany());
//  }
//
//  public boolean isThereAnUser() {
//    return isElementPresent(By.name("selected[]"));
//  }
//
  public int count() {
    return wd.findElements(By.name("selected[]")).size();
  }

  public UserData infoFromEditForm(UserData user) {
    initUserModificationById(user.getId()); //выбор контакт по идентификатору
    String firstname = wd.findElement(By.name("firstname")).getAttribute("value");
    String lastname = wd.findElement(By.name("lastname")).getAttribute("value");
    String home = wd.findElement(By.name("home")).getAttribute("value");
    String mobile = wd.findElement(By.name("mobile")).getAttribute("value");
    String work = wd.findElement(By.name("work")).getAttribute("value");
    wd.navigate().back();
    return new UserData().withId(user.getId()).withFirstname(firstname).withLastname(lastname)
            .withHomePhone(home).withMobilePhone(mobile).withWorkPhone(work);
  }

  private void initUserModificationById(int id){
    WebElement checkbox = wd.findElement(By.cssSelector(String.format("input[value='%s']", id)));
    WebElement row = checkbox.findElement(By.xpath("./../..")); //ячейка -> cтрочка
    List<WebElement> cells = row.findElements(By.tagName("td")); //ищем ячейку с карандашом -> полный список ячеек и ищем все элеметы с tagName("td")
    cells.get(7).findElement(By.tagName("a")).click(); // берем нужную ячейку -> находим ссылку и кликаем по ней

    //wd.findElement(By.xpath(String.format("//input[value='%s']/../../td[8]/a", id))).click(); //находим чекбокс - на 2 уровня вверх - ищем 8 ячейку -- внутри ссылку - click
    //wd.findElement(By.xpath(String.format("//tr[.//input[@value='%s']]/td[8]/a", id))).click(); //найти строку, внутри которой есть чекбокс с заданным айди
    //wd.findElement(By.cssSelector(String.format("a[href='edit.php?id=%s']", id))).click();
  }
//    public void changeUserInfo(UserData userData) {
//    getCompany(userData);
//    updateData();
//  }
}

