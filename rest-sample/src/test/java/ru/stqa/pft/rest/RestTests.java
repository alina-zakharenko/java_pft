package ru.stqa.pft.rest;

import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Set;

import static org.testng.Assert.assertEquals;

public class RestTests extends TestBase {

  @Test
  public void testCreateIssue() throws IOException {
    skipIfNotFixed(768);
    Set<Issue> oldIssues = getIssues();
    Issue newIssue = new Issue().withSubject("Test_123456 issue").withDescription("New test_123456 issue");
    int issueId = create(newIssue);
    Set<Issue> newIssues = getIssues();

    oldIssues.add(newIssue.withId(issueId));

    assertEquals(newIssues, oldIssues);
  }
}
