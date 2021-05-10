package ru.stqa.pft.addressbook.tests;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.TestBase;
import ru.stqa.pft.addressbook.model.UserData;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

public class UserModificationTests extends TestBase {
  WebDriver wd;

  @BeforeMethod
  public void ensurePreconditions(){
    app.goTo().homePage();
    if (app.user().list().size()==0) {
      app.user().create(new UserData()
              .withFirstname("Ron").withLastname("Weasley").withEmail("ronWeasley@magic.com").withCompany("").withGroup("test1"));
    }
  }

  @Test //(enabled = false)
  public void testUserModificationTest() throws Exception {
    List<UserData> before = app.user().list();
    int index = before.size() - 1;
    UserData user = new UserData()
            .withId(before.get(index).getId()).withFirstname("Ivan").withLastname("Ivanov").withEmail("Ii@magic.com").withCompany("").withGroup("test1");// сохраняем старый идентификатор
    app.goTo().homePage();
    app.user().modify(index, user);
    app.goTo().homePage();
    List<UserData> after = app.user().list();
    Assert.assertEquals(after.size(), before.size());
    System.out.println("Количество после " + after.size());

    before.remove(index);
    before.add(user);
    System.out.println("HashSet before" + new HashSet<Object>(before));
    System.out.println("HashSet after" + new HashSet<Object>(after));
    Comparator<? super UserData> byId = (u1, u2) -> Integer.compare(u1.getId(), u2.getId());
    before.sort(byId);
    after.sort(byId);
    Assert.assertEquals(before, after);

  }



}
