package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.stqa.pft.addressbook.model.UserData;
import ru.stqa.pft.addressbook.model.Users;

import java.util.List;

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
    //selectUserById(user.getId());
    //editUserButton();
    initUserModificationById(user.getId());
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
    typeUserData(By.name("home"), userData.getHomePhone());
    typeUserData(By.name("mobile"), userData.getMobilePhone());
    typeUserData(By.name("work"), userData.getWorkPhone());
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


  private Users userCash = null;


//  public Users all() {
//    if (userCash != null) {
//      return new Users(userCash);
//    }
//    userCash = new Users();  //создаем список, который будет заполняться
//    List<WebElement> rows = wd.findElements(By.name("entry")); // список объкетов типа WebElement - найти все элементы с именем entry
//    for (WebElement row : rows) {
//      List<WebElement> cells = row.findElements(By.tagName("td"));
//      int id = Integer.parseInt(cells.get(0).findElement(By.tagName("input")).getAttribute("value"));
//      String lastname = cells.get(1).getText();
//      String firstname = cells.get(2).getText();
//      String[] phones = cells.get(5).getText().split("\n");
//      userCash.add(new UserData().withId(id).withFirstname(firstname).withLastname(lastname)
//              .withHomePhone(phones[0]).withMobilePhone(phones[1]).withWorkPhone(phones[2]));
//    }
//    return userCash;
//  }

  public Users all() {
    if (userCash != null) {
      return new Users(userCash);
    }
    userCash = new Users();  //создаем список, который будет заполняться
    List<WebElement> rows = wd.findElements(By.name("entry")); // список объкетов типа WebElement - найти все элементы с именем entry
    for (WebElement row : rows) {
      List<WebElement> cells = row.findElements(By.tagName("td"));
      int id = Integer.parseInt(cells.get(0).findElement(By.tagName("input")).getAttribute("value"));
      String lastname = cells.get(1).getText();
      String firstname = cells.get(2).getText();
      String allPhones = cells.get(5).getText();
      String address = cells.get(3).getText();
      String allEmails = cells.get(4).getText();
      userCash.add(new UserData().withId(id).withFirstname(firstname).withLastname(lastname)
              .withAddress(address)
              .withAllEmails(allEmails)
              .withAllPhones(allPhones));
    }
    return userCash;
  }

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
    String email = wd.findElement(By.name("email")).getAttribute("value");
    String email2 = wd.findElement(By.name("email2")).getAttribute("value");
    String email3 = wd.findElement(By.name("email3")).getAttribute("value");
    String address = wd.findElement(By.name("address")).getAttribute("value");
    wd.navigate().back();
    return new UserData().withId(user.getId()).withFirstname(firstname).withLastname(lastname)
            .withHomePhone(home).withMobilePhone(mobile).withWorkPhone(work).withEmail(email).withEmail2(email2).withEmail3(email3).withAddress(address);
  }

  private void initUserModificationById(int id) {
    //метод последовательных приближений
    WebElement checkbox = wd.findElement(By.cssSelector(String.format("input[value='%s']", id)));//ищем чек бокс
    WebElement row = checkbox.findElement(By.xpath("./../..")); //ячейка -> cтрочка; . - поиск с текущего элемента
    List<WebElement> cells = row.findElements(By.tagName("td")); //ищем ячейку с карандашом -> полный список ячеек и ищем все элеметы с tagName("td")
    cells.get(7).findElement(By.tagName("a")).click(); // берем нужную ячейку -> находим ссылку и кликаем по ней

    //wd.findElement(By.xpath(String.format("//input[value='%s']/../../td[8]/a", id))).click(); //находим чекбокс - на 2 уровня вверх - ищем 8 ячейку -- внутри ссылку - click
    //wd.findElement(By.xpath(String.format("//tr[.//input[@value='%s']]/td[8]/a", id))).click(); //найти строку, внутри которой есть чекбокс с заданным айди
    //wd.findElement(By.cssSelector(String.format("a[href='edit.php?id=%s']", id))).click();
  }

//to be removed

  //  public void getCompany(UserData userData) {
//    type(By.name("company"), userData.getCompany());
//  }
//
//  public boolean isThereAnUser() {
//    return isElementPresent(By.name("selected[]"));
//  }
//

//    public void changeUserInfo(UserData userData) {
//    getCompany(userData);
//    updateData();
//  }


  //  public Users all() {
//    if (userCash != null) {
//      return new Users(userCash);
//    }
//    userCash = new Users();  //создаем список, который будет заполняться
//    List<WebElement> elements = wd.findElements(By.name("entry")); // список объкетов типа WebElement - найти все элементы с именем entry
//    for (WebElement element : elements) {
//      element.findElements(By.tagName("td")); //переменная element пробегает по всем cells
//      String firstname = element.findElement(By.xpath(".//td[3]")).getText();
//      String lastname = element.findElement(By.xpath(".//td[2]")).getText();
//      //int id = Integer.parseInt(element.findElement(By.cssSelector("[name='entry']>.center>input")).getAttribute("value"));
//      int id = Integer.parseInt(element.findElement(By.xpath(".//td/input")).getAttribute("value"));// передается в конструктор и используется при сравнении
//      userCash.add(new UserData().withId(id).withFirstname(firstname).withLastname(lastname));
//    }
//    return new Users(userCash);
//  }


//  public Set<UserData> all() {
//    Set<UserData> users = new HashSet<>();
//    List<WebElement> rows = wd.findElements(By.name("entry"));
//    for (WebElement row : rows) {
//      List<WebElement> cells = row.findElements(By.tagName("td"));
//      int id = Integer.parseInt(cells.get(0).findElement(By.tagName("input")).getAttribute("value"));
//      String lastname = cells.get(1).getText();
//      String firstname = cells.get(2).getText();
//      String[] phones = cells.get(5).getText().split("\n");
//      users.add(new UserData().withId(id).withFirstname(firstname).withLastname(lastname)
//              .withHomePhone(phones[0]).withMobilePhone(phones[1]).withWorkPhone(phones[2]));
//    }
//    return users;
//  }


}

