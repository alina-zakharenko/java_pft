package ru.stqa.pft.addressbook.tests;


import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.TestBase;

public class GroupCreationTests extends TestBase {

  @Test
  public void testGroupCreation() throws Exception {
    applicationManager.getNavigationHelper().gotoGroupPage();
    applicationManager.getGroupHelper().createGroup(new GroupData("test1", "test2", "test3"));
  }

}
