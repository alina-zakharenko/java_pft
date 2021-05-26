package ru.stqa.pft.addressbook.generators;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializer;
import com.thoughtworks.xstream.XStream;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.UserData;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class UserDataGenerator {

  @Parameter(names = "-c", description = "User count")
  public int count;

  @Parameter(names = "-f", description = "Target file")
  public String file;

  @Parameter(names = "-d", description = "Data format")
  public String format;

  public static void main(String[] args) throws IOException {
    UserDataGenerator generator = new UserDataGenerator();
    JCommander jCommander = new JCommander(generator);
    try {
      jCommander.parse(args);
    } catch (ParameterException ex) {
      jCommander.usage();
      return;
    }
    generator.run();
  }

  private void run() throws IOException {
    List<UserData> users = generateUsers(count);
    if (format.equals("csv")) {
      saveAsCsv(users, new File(file));
    } else if (format.equals("xml")) {
      saveAsXml(users, new File(file));
    } else if (format.equals("json")) {
      saveAsJson(users, new File(file));
    } else
      System.out.println("Unrecognized format " + format);
  }

  private List<UserData> generateUsers(int count) {
    System.out.println(new File(".").getAbsolutePath());
    List<UserData> users = new ArrayList<>();
    for (int i = 0; i < count; i++) {
      users.add(new UserData()
              .withFirstname(String.format("Ron", i)).withLastname(String.format("Weasley", i))
              .withEmail(String.format("RonWeasley@magic.com", i)).withCompany(String.format("", i))
              //.withGroup(String.format("test3", i))
              .withHomePhone(String.format("9", i))
              .withPhoto(new File("src/test/resources/pft.png"))
      );
    }
    return users;
  }

  private void saveAsJson(List<UserData> users, File file) throws IOException {
    JsonSerializer<File> serializer = (src, typeOfSrc, context) -> new JsonPrimitive(src.getPath());
    Gson gson = new GsonBuilder()
            .registerTypeAdapter(File.class, serializer)
            .setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();
    String json = gson.toJson(users);
    try (Writer writer = new FileWriter(file)) {
      writer.write(json);
    }
  }

  private void saveAsCsv(List<UserData> users, File file) throws IOException {
    try (Writer writer = new FileWriter(file)) {
      for (UserData user : users) {
        writer.write(String.format("%s;%s;%s;%s;%s;%s;%s;%s;%s \n",
                user.getFirstname(), user.getLastname(),
                user.getEmail(), user.getCompany(),
                user.getGroups(),
                user.getHomePhone(),
                user.getMobilePhone(), user.getWorkPhone(), user.getPhoto()));
      }
    }
  }

  private void saveAsXml(List<UserData> users, File file) throws IOException {
    XStream xstream = new XStream();
    xstream.processAnnotations(UserData.class);
    String xml = xstream.toXML(users);
    try (Writer writer = new FileWriter(file)) {
      writer.write(xml);
    }
  }
}