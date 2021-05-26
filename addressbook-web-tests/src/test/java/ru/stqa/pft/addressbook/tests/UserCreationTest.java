package ru.stqa.pft.addressbook.tests;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.reflect.TypeToken;
import com.thoughtworks.xstream.XStream;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.TestBase;
import ru.stqa.pft.addressbook.model.UserData;
import ru.stqa.pft.addressbook.model.Users;
import sun.util.locale.provider.SPILocaleProviderAdapter;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class UserCreationTest extends TestBase {

  @DataProvider
  public Iterator<Object[]> validUsersFromCsv() throws IOException {
    List<Object[]> list = new ArrayList<>();
    BufferedReader reader = new BufferedReader(new FileReader(new File("src/test/resources/users.csv")));
    String line = reader.readLine();
    while (line != null) {
      String [] split = line.split(";");
      list.add(new Object[] {new UserData()
              .withFirstname(split[0]).withLastname(split[1])
              .withEmail(split[2]).withCompany(split[3])
              //.withGroup(split[4])
              .inGroup(new GroupData().withFooter("").withHeader("").withName("test3"))
              .withHomePhone(split[5])
              .withMobilePhone(split[6]).withWorkPhone(split[7])
              .withPhoto(new File("src/test/resources/pft.png"))});
      line = reader.readLine();
    }
    return list.iterator();
  }


  @DataProvider
  public Iterator<Object[]> validUsersFromXml() throws IOException {
    try (BufferedReader reader = new BufferedReader(new FileReader(new File("src/test/resources/users.xml")))) {
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
  }

  @DataProvider
  public Iterator<Object[]> validUsersFromJson() throws IOException {
    JsonDeserializer<File> deserializer = (json, typeOfT, context) -> new File(json.getAsJsonPrimitive().getAsString());
    Gson gson = new GsonBuilder().registerTypeAdapter(File.class, deserializer).create();
    try (BufferedReader reader = new BufferedReader(new FileReader(new File("src/test/resources/users.json")))) {
      String json = "";
      String line = reader.readLine();
      while (line != null) {
        json += line;
        line = reader.readLine();
      }
      //Gson gson = new Gson();
      List<UserData> users = gson.fromJson(json, new TypeToken<List<UserData>>() {
      }.getType());
      return users.stream().map((u) -> new Object[]{u}).collect(Collectors.toList()).iterator();
    }
  }



  @Test(dataProvider = "validUsersFromJson")
  public void testNewUserCreation(UserData user) throws Exception {
    app.goTo().homePage();
    Users before = app.db().users();
    //UserData user = new UserData("Harry", "Potter", "harrypotter@magic.com", "", "test2");
    //UserData user = new UserData("Hermine", "Granger", "herminegranger@magic.com", "", "test2");
    //File photo = new File("src/test/resources/pft.png");
    ///UserData user = new UserData().withFirstname("Ron").withLastname("Weasley").withEmail("RonWeasley@magic.com").withCompany("").withGroup("test3").withHomePhone("9").withMobilePhone("8").withWorkPhone("7").withPhoto(photo);
    app.user().create(user);
    app.goTo().homePage();
    assertThat(app.user().count(), equalTo(before.size() + 1));
    Users after = app.db().users();
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
