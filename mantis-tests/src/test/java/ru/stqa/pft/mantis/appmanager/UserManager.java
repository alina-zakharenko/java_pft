package ru.stqa.pft.mantis.appmanager;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import ru.stqa.pft.mantis.model.UserData;

public class UserManager {

  private final ApplicationManager app;
  private WebDriver wd;

  public UserManager(ApplicationManager app) {
    this.app = app;
    wd = app.getDriver();
  }

  public void login(String username, String password) {
    wd.get(app.getProperty("web.baseUrl") + "/login_page.php");
    wd.findElement(By.id("username")).click();
    wd.findElement(By.id("username")).sendKeys(username);
    wd.findElement(By.cssSelector("input[type='submit']")).click();
    wd.findElement(By.id("password")).click();
    wd.findElement(By.id("password")).sendKeys(password);
    wd.findElement(By.cssSelector("input[type='submit']")).click();
  }


  public void goToManagePage() throws InterruptedException {
    wd.get(app.getProperty("web.baseUrl") + "/manage_user_page.php");
  }

  public void resetPwdToSelectedUser(UserData user) {
    wd.findElement(By.xpath("//a[@href='manage_user_edit_page.php?user_id=" + user.getId() + "']")).click();
    wd.findElement(By.cssSelector("input[value='Reset Password']")).click();
  }

  public void changePassword(String confirmationLink, String name, String password) {
    wd.get(confirmationLink);
    wd.findElement(By.name("realname")).sendKeys(name);
    wd.findElement(By.name("password")).sendKeys(password);
    wd.findElement(By.name("password_confirm")).sendKeys(password);
    wd.findElement(By.xpath("//button[@type='submit']")).click();
  }

}
