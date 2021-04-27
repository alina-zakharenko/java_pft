package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.TestBase;

public class GroupModificationTests extends TestBase {
  @Test
  public void testGroupModification() {
    applicationManager.getNavigationHelper().gotoGroupPage();
    if (!applicationManager.getGroupHelper().isThereAGroup()) {
      applicationManager.getGroupHelper().createGroup(new GroupData("test1", null, null));
    }
    applicationManager.getGroupHelper().selectGroup();
    applicationManager.getGroupHelper().initGroupModification();
    applicationManager.getGroupHelper().fillGroupForm(new GroupData("test1", "test2", "test3"));
    applicationManager.getGroupHelper().submitGroupModification();
    applicationManager.getGroupHelper().returnToGroupPage();

  }

}
