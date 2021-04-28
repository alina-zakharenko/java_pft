package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.TestBase;
import ru.stqa.pft.addressbook.model.UserData;

public class UserCreationTest extends TestBase {

  @Test
  public void testNewUserCreation() throws Exception {
    applicationManager.getUserHelper().createUser(new UserData("Harry", "Potter", "harrypotter@magic.com", "", "test1"));
    applicationManager.getUserHelper().createUser(new UserData("Hermine", "Granger", "herminegranger@magic.com", "", "test2"));
    applicationManager.getUserHelper().createUser(new UserData("Ron", "Weasley", "ronWeasley@magic.com", "", "test3"));
  }
}
