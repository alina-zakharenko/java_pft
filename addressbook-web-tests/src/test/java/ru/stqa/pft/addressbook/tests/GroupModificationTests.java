package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.TestBase;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

public class GroupModificationTests extends TestBase {
  @Test
  public void testGroupModification() {
    applicationManager.getNavigationHelper().gotoGroupPage();
    if (!applicationManager.getGroupHelper().isThereAGroup()) {
      applicationManager.getGroupHelper().createGroup(new GroupData("test1", null, null));
    }
    List<GroupData> before = applicationManager.getGroupHelper().getGroupList();
    applicationManager.getGroupHelper().selectGroup(before.size() - 1);
    System.out.println("Количество групп до " + before.size());
    applicationManager.getGroupHelper().initGroupModification();
    GroupData group = new GroupData(before.get(before.size() - 1).getId(), "test1", "test2", "test3");
    applicationManager.getGroupHelper().fillGroupForm(group);
    applicationManager.getGroupHelper().submitGroupModification();
    applicationManager.getGroupHelper().returnToGroupPage();
    List<GroupData> after = applicationManager.getGroupHelper().getGroupList();
    Assert.assertEquals(after.size(), before.size(), "Количество групп до и после не совпадает.");
    System.out.println("Количество групп до " + before.size() + " и после " + after.size());

    before.remove(before.size() - 1);
    before.add(group);
    Comparator<? super GroupData> byId = (g1, g2) -> Integer.compare(g1.getId(), g2.getId());
    before.sort(byId);
    after.sort(byId);
    Assert.assertEquals(before, after);
  }

}
