package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.TestBase;

public class GroupDeletionTests extends TestBase {


  @Test
  public void testGroupDeletion() throws Exception {
    applicationManager.gotoGroupPage();
    applicationManager.selectGroup();
    applicationManager.deleteSelectedGroups();
    applicationManager.returnToGroupPage();
  }

}
