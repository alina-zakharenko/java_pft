package ru.stqa.pft.addressbook.tests;

import net.bytebuddy.jar.asm.ClassTooLargeException;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.TestBase;
import ru.stqa.pft.addressbook.model.UserData;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class UserAddressesTest extends TestBase {

  @Test
  public void testUserAddresses() {
    app.goTo().homePage();
    UserData user = app.user().all().iterator().next(); //загружаем множество контактов + выбираем любой случайным образом
    UserData userInfoFromEditForm = app.user().infoFromEditForm(user); // метод, загружающий инфу о контактах из формы релактирования

    assertThat(user.getAllEmails(), equalTo(mergeEmails(userInfoFromEditForm)));
    assertThat(user.getAddress(), equalTo(userInfoFromEditForm.getAddress()));
  }

  private String mergeEmails(UserData user) {
    return Arrays.asList(user.getEmail(), user.getEmail2(), user.getEmail3())
            .stream().filter((s) -> !s.equals(""))
            //.map(UserAddressesTest::cleaned)
            .collect(Collectors.joining("\n"));

  }

  public static String cleaned(String email) {
    return email.replaceAll("\\s", "").replaceAll("[-()]", "");
  }
}
