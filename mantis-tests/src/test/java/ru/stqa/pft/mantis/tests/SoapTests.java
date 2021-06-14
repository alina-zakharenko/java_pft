package ru.stqa.pft.mantis.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.mantis.model.Issue;
import ru.stqa.pft.mantis.model.Project;

import javax.xml.rpc.ServiceException;
import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.util.Set;

import static org.testng.Assert.assertEquals;

public class SoapTests extends TestBase {

  @Test
  public void testGetProjects() throws MalformedURLException, ServiceException, RemoteException {
    Set<Project> projects = app.soap().getProjects();

    System.out.println("Количество проектов: " + projects.size());
    for (Project project : projects) {
      System.out.println("Названия проектов: " + project.getName());
    }
  }

  @Test
  public void testCreateIssue() throws MalformedURLException, ServiceException, RemoteException {
    Set<Project> projects = app.soap().getProjects(); //получаем множество projects

    Issue issue = new Issue().withSummary("Test issue")
            .withDescription("Test issue description")
            .withProject(projects.iterator().next());// выбираем проект из мн-ва

    Issue created = app.soap().addIssue(issue); //создаем issue, который можно сравнивать с существующим

    assertEquals(issue.getSummary(), created.getSummary());

  }

  @Test
  public void testIsIssueOpen() throws Exception {
    //skipIfNotFixed(issue.getId());
    skipIfNotFixed(0000001);

    Set<Project> projects = app.soap().getProjects();

    Issue issue = new Issue().withSummary("Test issue")
            .withDescription("Test issue description")
            .withProject(projects.iterator().next());

    Issue created = app.soap().addIssue(issue);
    
    assertEquals(issue.getSummary(), created.getSummary());

  }

}
