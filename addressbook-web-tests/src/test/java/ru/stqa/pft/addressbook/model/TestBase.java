package ru.stqa.pft.addressbook.model;

import org.openqa.selenium.remote.BrowserType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestContext;
import org.testng.annotations.*;
import ru.stqa.pft.addressbook.appmanager.ApplicationManager;
import ru.stqa.pft.addressbook.tests.GroupCreationTests;
import ru.stqa.pft.addressbook.tests.MyTestListener;

import java.io.File;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

@Listeners(MyTestListener.class)
public class TestBase {


  Logger logger = LoggerFactory.getLogger(TestBase.class);


  protected static final ApplicationManager app
          = new ApplicationManager(System.getProperty("browser", BrowserType.FIREFOX)); //глобальная переменная

  //BeforeMethod(alwaysRun = true) //метод инициализации фикстуры
  @BeforeSuite
  public void setUp(ITestContext context) throws Exception {
    app.init();
    context.setAttribute("app", app);
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


  public void verifyGroupListFromUi() {
    if (Boolean.getBoolean("verifyUI")) {
      Groups dbGroups = app.db().groups();
      Groups uiGroups = app.group().all();
      assertThat(uiGroups, equalTo(dbGroups.stream()
              .map((g) -> new GroupData().withId(g.getId()).withName(g.getName()))
              .collect(Collectors.toSet())));
    }
  }

  public void verifyUserListFromUi() {
    if (Boolean.getBoolean("verifyUI")) {
      Users dbUsers = app.db().users();
      Users uiUsers = app.user().all();
      assertThat(uiUsers, equalTo(dbUsers.stream()
              .map((u) -> new UserData()
                      .withId(u.getId()).withFirstname(u.getFirstname()).withLastname(u.getLastname()))
              .collect(Collectors.toSet())));
    }
  }
}
