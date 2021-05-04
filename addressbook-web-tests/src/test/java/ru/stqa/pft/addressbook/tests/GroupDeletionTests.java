package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.TestBase;

import java.util.List;

public class GroupDeletionTests extends TestBase {


  @Test
  public void testGroupDeletion() throws Exception {
    applicationManager.getNavigationHelper().gotoGroupPage();
    if (!applicationManager.getGroupHelper().isThereAGroup()) {
      applicationManager.getGroupHelper().createGroup(new GroupData("test1", null, null));
    }
    List<GroupData> before = applicationManager.getGroupHelper().getGroupList();
    System.out.println("Количество групп до " + before.size());
    applicationManager.getGroupHelper().selectGroup(before.size() - 2);
    applicationManager.getGroupHelper().deleteSelectedGroups();
    applicationManager.getGroupHelper().returnToGroupPage();
    List<GroupData> after = applicationManager.getGroupHelper().getGroupList();
    Assert.assertEquals(after.size(), before.size() - 1, "Количество групп после не уменьшилось.");
    System.out.println("Количество групп после " + after.size());

    before.remove(before.size() - 1);
    Assert.assertEquals(before, after);
  }

}
