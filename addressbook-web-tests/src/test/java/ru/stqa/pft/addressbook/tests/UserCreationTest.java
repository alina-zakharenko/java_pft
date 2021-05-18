package ru.stqa.pft.addressbook.tests;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.thoughtworks.xstream.XStream;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.TestBase;
import ru.stqa.pft.addressbook.model.UserData;
import ru.stqa.pft.addressbook.model.Users;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class UserCreationTest extends TestBase {

  @DataProvider
  public Iterator<Object[]> validUsersFromXml() throws IOException {
    BufferedReader reader = new BufferedReader(new FileReader(new File("src/test/resources/users.xml")));
    String xml = "";
    String line = reader.readLine();
    while (line != null) {
      xml += line;
      line = reader.readLine();
    }
    XStream xstream = new XStream();
    xstream.processAnnotations(UserData.class);
    List<UserData> users = (List<UserData>) xstream.fromXML(xml);
    return users.stream().map((u) -> new Object[]{u}).collect(Collectors.toList()).iterator();
  }

  @DataProvider
  public Iterator<Object[]> validUsersFromJson() throws IOException {
    BufferedReader reader = new BufferedReader(new FileReader(new File("src/test/resources/users.json")));
    String json = "";
    String line = reader.readLine();
    while (line != null) {
      json += line;
      line = reader.readLine();
    }
    Gson gson = new Gson();
    List<UserData> users = gson.fromJson(json, new TypeToken<List<UserData>>(){}.getType());
    return users.stream().map((u) -> new Object[]{u}).collect(Collectors.toList()).iterator();
  }


  @Test(dataProvider = "validUsersFromJson")
  public void testNewUserCreation(UserData user) throws Exception {
    app.goTo().homePage();
    Users before = app.user().all();
    //UserData user = new UserData("Harry", "Potter", "harrypotter@magic.com", "", "test2");
    //UserData user = new UserData("Hermine", "Granger", "herminegranger@magic.com", "", "test2");
    //File photo = new File("src/test/resources/pft.png");
    ///UserData user = new UserData().withFirstname("Ron").withLastname("Weasley").withEmail("RonWeasley@magic.com").withCompany("").withGroup("test3").withHomePhone("9").withMobilePhone("8").withWorkPhone("7").withPhoto(photo);
    app.user().create(user);
    app.goTo().homePage();
    assertThat(app.user().count(), equalTo(before.size() + 1));
    Users after = app.user().all();
    assertThat(after, equalTo(
            before.withAdded(user.withId(after.stream().mapToInt((u) -> u.getId()).max().getAsInt()))));

  }

  @Test(enabled = false)
  public void testCurrentDir() {
    File currentDir = new File(".");
    System.out.println(currentDir.getAbsolutePath());
    File photo = new File("src/test/resources/pft.png");
    System.out.println(photo.getAbsolutePath());
    System.out.println(photo.exists());

  }
}
