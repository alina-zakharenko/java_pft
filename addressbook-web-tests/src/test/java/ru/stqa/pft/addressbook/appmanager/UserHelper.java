package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;
import ru.stqa.pft.addressbook.model.UserData;
import ru.stqa.pft.addressbook.model.Users;


import java.util.Iterator;
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

  public void addToExistingGroup(UserData user) {
    initAddUserToGroupById(user.getId());
    addTo();
    //userCash = null;
  }

  public void addToGroup(UserData user, GroupData group) {
    initAddUserToGroupById(user.getId());
    Select drpGroup = new Select(wd.findElement(By.name("to_group")));
    drpGroup.selectByVisibleText(group.getName());
    addTo();
    //userCash = null;
  }

  public UserData findUserWithoutGroup(Users users, GroupData group) {
    Iterator<UserData> iterator = users.iterator();
    while (iterator.hasNext()) {
      UserData nextUser = iterator.next();
      if (nextUser.getGroups().isEmpty()) {
        return nextUser;
      }
    }
    return null;
  }

  public UserData findUserWithGroup(Users users, GroupData group) {
    Iterator<UserData> iterator = users.iterator();
    while (iterator.hasNext()) {
      UserData nextUser = iterator.next();
      if (nextUser.getGroups().contains(group)) {
        return nextUser;
      }
    }
    return null;
  }

  public boolean areAllUsersInAllGroups(Users users, Groups allGroups) {
    Iterator<UserData> iterator = users.iterator();
    while (iterator.hasNext()) {
      UserData nextUser = iterator.next();
      if (nextUser.getGroups() != allGroups) {
        return false;
      }
    }
    return true;
  }


  private void addTo() {
    click(By.name("add"));
  }

  public void deleteFromGroup(UserData user) {
    selectUserById(user.getId());
    click(By.name("remove"));
  }

  public void selectGroup(GroupData groupsList) {
    new Select(wd.findElement(By.name("group"))).selectByVisibleText(groupsList.getName());
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
  public void delete(int index) throws InterruptedException {
    selectUser(index);
    deleteUser();
  }


  public void delete(UserData user) throws InterruptedException {
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

  public void deleteUser() throws InterruptedException {
    clickDeleteUserButton();
    getAccept();
    Thread.sleep(1000);
  }

  public void clickDeleteUserButton() {
    click(By.cssSelector(".left:nth-child(8) > input"));
  }

  public void getAccept() {
    wd.switchTo().alert().accept();
  }

  //data
  public void fillUserInfo(UserData userData, boolean creation) {
    type(By.name("firstname"), userData.getFirstname());
    type(By.name("lastname"), userData.getLastname());
    type(By.name("email"), userData.getEmail());
    type(By.name("company"), userData.getCompany());
    type(By.name("home"), userData.getHomePhone());
    type(By.name("mobile"), userData.getMobilePhone());
    type(By.name("work"), userData.getWorkPhone());
    // attach(By.name("photo"), userData.getPhoto());
    if (creation) {
      if (userData.getGroups().size() > 0) {
        Assert.assertTrue(userData.getGroups().size() == 1);
        new Select(wd.findElement(By.name("new_group"))).selectByVisibleText(userData.getGroups().iterator().next().getName());
      }
    } else {
      Assert.assertFalse(isElementPresent(By.name("new_group")));
    }
  }


  private Users userCash = null;

  public Users all() {
    if (userCash != null) {
      return new Users(userCash);
    }
    userCash = new Users();  //?????????????? ????????????, ?????????????? ?????????? ??????????????????????
    List<WebElement> rows = wd.findElements(By.name("entry")); // ???????????? ???????????????? ???????? WebElement - ?????????? ?????? ???????????????? ?? ???????????? entry
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
    initUserModificationById(user.getId()); //?????????? ?????????????? ???? ????????????????????????????
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
    //?????????? ???????????????????????????????? ??????????????????????
    WebElement checkbox = wd.findElement(By.cssSelector(String.format("input[value='%s']", id)));//???????? ?????? ????????
    WebElement row = checkbox.findElement(By.xpath("./../..")); //???????????? -> c????????????; . - ?????????? ?? ???????????????? ????????????????
    List<WebElement> cells = row.findElements(By.tagName("td")); //???????? ???????????? ?? ???????????????????? -> ???????????? ???????????? ?????????? ?? ???????? ?????? ?????????????? ?? tagName("td")
    cells.get(7).findElement(By.tagName("a")).click(); // ?????????? ???????????? ???????????? -> ?????????????? ???????????? ?? ?????????????? ???? ??????

    //wd.findElement(By.xpath(String.format("//input[value='%s']/../../td[8]/a", id))).click(); //?????????????? ?????????????? - ???? 2 ???????????? ?????????? - ???????? 8 ???????????? -- ???????????? ???????????? - click
    //wd.findElement(By.xpath(String.format("//tr[.//input[@value='%s']]/td[8]/a", id))).click(); //?????????? ????????????, ???????????? ?????????????? ???????? ?????????????? ?? ???????????????? ????????
    //wd.findElement(By.cssSelector(String.format("a[href='edit.php?id=%s']", id))).click();
  }


  private void initAddUserToGroupById(int id) {
//    WebElement checkbox = wd.findElement(By.name("selected[]"));//???????? ?????? ????????
//    checkbox.click();
    wd.findElement(By.cssSelector("input[value='" + id + "']")).click();
  }


}

