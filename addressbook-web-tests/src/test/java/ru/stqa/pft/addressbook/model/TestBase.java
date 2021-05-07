package ru.stqa.pft.addressbook.model;

import org.openqa.selenium.remote.BrowserType;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import ru.stqa.pft.addressbook.appmanager.ApplicationManager;

public class TestBase {

  protected static final ApplicationManager applicationManager = new ApplicationManager(BrowserType.CHROME); //глобальная переменная

  //BeforeMethod(alwaysRun = true) //метод инициализации фикстуры
  @BeforeSuite
  public void setUp() throws Exception {
    applicationManager.init();
  }

  //@AfterMethod(alwaysRun = true) //метод завершения фикстуры
  @AfterSuite
  public void tearDown() throws Exception {
    applicationManager.stop();
  }

}
