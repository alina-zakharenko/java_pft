package ru.stqa.pft.addressbook.tests;

import org.omg.CORBA.PUBLIC_MEMBER;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.TestBase;
import ru.stqa.pft.addressbook.model.UserData;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class UserPhoneTest extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() {
    app.goTo().homePage();
    if (app.user().all().size() == 0) {
      app.user().create(new UserData()
              .withFirstname("Ron").withLastname("Weasley").withEmail("ronWeasley@magic.com").withCompany("").withGroup("test1").withHomePhone("111").withMobilePhone("222").withWorkPhone("333"));

    }
  }

  @Test
  public void testUserPhones() {
    app.goTo().homePage();
    UserData user = app.user().all().iterator().next(); //загружаем множество контактов + выбираем любой случайным образом
    UserData userInfoFromEditForm = app.user().infoFromEditForm(user); // метод, загружающий инфу о контактах из формы релактирования

    assertThat(user.getHomePhone(), equalTo(cleaned(userInfoFromEditForm.getHomePhone())));
    assertThat(user.getMobilePhone(), equalTo(cleaned(userInfoFromEditForm.getMobilePhone())));
    assertThat(user.getWorkPhone(), equalTo(cleaned(userInfoFromEditForm.getWorkPhone())));
  }

  public String cleaned(String phone) {
    return phone.replaceAll("\\s", "").replaceAll("[-()]", "");
  }
}
