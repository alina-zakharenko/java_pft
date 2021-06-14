package ru.stqa.pft.mantis.tests;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.lanwen.verbalregex.VerbalExpression;
import ru.stqa.pft.mantis.model.MailMessage;
import ru.stqa.pft.mantis.model.UserData;
import ru.stqa.pft.mantis.model.Users;

import java.util.List;

import static org.testng.Assert.assertTrue;


public class ChangePasswordTests extends TestBase {
  private int maxId;

  @BeforeMethod
  public void startMailServer() {
    app.mail().start();
  }

  @Test
  public void testChangeUserPassword() throws Exception {
    long now = System.currentTimeMillis();
    String newPassword = "Password" + now;
    int i = 0;

    Users usersBefore = app.db().users();

    UserData selectedUser = new UserData();
    for (UserData user : usersBefore) {
      if (!(user.getUsername().equals("administrator"))) {
        selectedUser = user;
        break;
      }
      i += 1;
      if (i == usersBefore.size()) {
        String email = String.format("user%s@localhost.localdomain", now);
        String newUser = String.format("user%s", now);
        String password = "password";
        app.registration().start(newUser, email);
        List<MailMessage> mailRegisterMessages = app.mail().waitForMail(2, 10000);
        MailMessage mailMessage = mailRegisterMessages.stream().filter((message) -> message.to.equals(email)).findFirst().get();
        String confirmationLink = findConfirmationLink(mailMessage, email);
        app.registration().finish(confirmationLink, newUser, password);
        Users usersAfter = app.db().users();
        for (UserData userData : usersAfter) {
          if (userData.getId() > maxId) {
            selectedUser = userData;
          }
        }
      }
    }


    String admin = "administrator";
    String password = "root";
    app.manageUser().login(admin, password);
    app.manageUser().goToManagePage();
    app.manageUser().resetPwdToSelectedUser(selectedUser);

    List<MailMessage> mailMessages = app.mail().waitForMail(1, 10000);
    MailMessage mailMessage = mailMessages.get(mailMessages.size() - 1);
    String confirmationLink = findConfirmationLink(mailMessage, selectedUser.getEmail());

    app.manageUser().changePassword(confirmationLink, selectedUser.getUsername(), newPassword);
    assertTrue(app.newSession().login(selectedUser.getUsername(), newPassword));
  }

  private String findConfirmationLink(MailMessage mailMessage, String email) {
    VerbalExpression regex = VerbalExpression.regex().find("http://").nonSpace().oneOrMore().build();
    return regex.getText(mailMessage.text);
  }

  @AfterMethod(alwaysRun = true)
  public void stopMailServer() {
    app.mail().stop();
  }
}




