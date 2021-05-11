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
import java.util.Set;

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
    Set<UserData> before = app.user().all();
    UserData modifiedUser = before.iterator().next();// next() - вернет первый элемент множества
    UserData user = new UserData()
            .withId(modifiedUser.getId()).withFirstname("Vasja").withLastname("Petrov").withEmail("Ii@magic.com").withCompany("").withGroup("test1");// сохраняем старый идентификатор
    app.goTo().homePage();
    app.user().modify(user);
    app.goTo().homePage();
    Set<UserData> after = app.user().all();
    Assert.assertEquals(after.size(), before.size());
    System.out.println("Количество после " + after.size());

    before.remove(modifiedUser);
    before.add(user);
    System.out.println("HashSet before" + new HashSet<Object>(before));
    System.out.println("HashSet after" + new HashSet<Object>(after));
    Assert.assertEquals(before, after);

  }



}
