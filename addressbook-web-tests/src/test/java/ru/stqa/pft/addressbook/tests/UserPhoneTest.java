package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.TestBase;
import ru.stqa.pft.addressbook.model.UserData;

public class UserPhoneTest extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() {
    app.goTo().homePage();
    if (app.user().all().size() == 0) {
      app.user().create(new UserData()
              .withFirstname("Ron").withLastname("Weasley").withEmail("ronWeasley@magic.com").withCompany("").withGroup("test1"));

    }
  }

  @Test
  public void testUserPhoneTest() {
    app.goTo().homePage();
    UserData user = app.user().all().iterator().next(); //загружаем множество контактов + выбираем любой случайным образом
    UserData userInfoFromEditForm = app.user().infoFromEditForm(user); // метод, загружающий инфу о контактах из формы релактирования

  }
}
