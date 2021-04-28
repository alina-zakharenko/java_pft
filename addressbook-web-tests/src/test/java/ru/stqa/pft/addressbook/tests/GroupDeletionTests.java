package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.TestBase;

public class GroupDeletionTests extends TestBase {


  @Test
  public void testGroupDeletion() throws Exception {
    applicationManager.getNavigationHelper().gotoGroupPage();
    if (!applicationManager.getGroupHelper().isThereAGroup()) {
      applicationManager.getGroupHelper().createGroup(new GroupData("test1", null, null));
    }
    applicationManager.getGroupHelper().selectGroup();
    applicationManager.getGroupHelper().deleteSelectedGroups();
    applicationManager.getGroupHelper().returnToGroupPage();
  }

}
