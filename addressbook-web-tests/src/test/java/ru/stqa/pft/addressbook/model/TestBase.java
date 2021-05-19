package ru.stqa.pft.addressbook.model;

import org.openqa.selenium.remote.BrowserType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import ru.stqa.pft.addressbook.appmanager.ApplicationManager;
import ru.stqa.pft.addressbook.tests.GroupCreationTests;

import java.lang.reflect.Method;
import java.util.Arrays;

public class TestBase {


  Logger logger = LoggerFactory.getLogger(TestBase.class);


  protected static final ApplicationManager app
          = new ApplicationManager(System.getProperty("browser", BrowserType.CHROME)); //глобальная переменная

  //BeforeMethod(alwaysRun = true) //метод инициализации фикстуры
  @BeforeSuite
  public void setUp() throws Exception {
    app.init();
  }

  //@AfterMethod(alwaysRun = true) //метод завершения фикстуры
  @AfterSuite(alwaysRun = true)
  public void tearDown() throws Exception {
    app.stop();
  }

  @BeforeMethod
  public void logTestStart(Method m, Object[] p) {
    logger.info("Start test " + m.getName() + "with parameters " + Arrays.asList(p));

  }

  @AfterMethod(alwaysRun = true)
  public void logTestStop(Method m) {
    logger.info("Stop test " + m.getName());
  }

}
