package ru.stqa.pft.addressbook.model;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import ru.stqa.pft.addressbook.appmanager.ApplicationManager;

public class TestBase {

  protected final ApplicationManager applicationManager = new ApplicationManager();

  @BeforeMethod(alwaysRun = true) //метод инициализации фикстуры
  public void setUp() throws Exception {
    applicationManager.init();
  }

  @AfterMethod(alwaysRun = true) //метод завершения фикстуры
  public void tearDown() throws Exception {
    applicationManager.stop();
  }

}
