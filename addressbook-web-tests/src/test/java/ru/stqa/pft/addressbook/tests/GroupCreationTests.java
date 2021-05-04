package ru.stqa.pft.addressbook.tests;


import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.TestBase;

import java.util.HashSet;
import java.util.List;

public class GroupCreationTests extends TestBase {

  @Test
  public void testGroupCreation() throws Exception {
    applicationManager.getNavigationHelper().gotoGroupPage();
    List<GroupData> before = applicationManager.getGroupHelper().getGroupList();
    //int before = applicationManager.getGroupHelper().getGroupCount();
    System.out.println("Количество групп до " + before.size());
    GroupData group = new GroupData("test1", "test2", "test3");
    applicationManager.getGroupHelper().createGroup(group);
    List<GroupData> after = applicationManager.getGroupHelper().getGroupList();
    //int after = applicationManager.getGroupHelper().getGroupCount();
    applicationManager.getNavigationHelper().gotoHomePage();
    Assert.assertEquals(after.size(), before.size() + 1, "Количество групп после не увеличилось.");
    System.out.println("Количество групп после " + after.size());
/**
 int max = 0;
 for (GroupData g : after) {
 if (g.getId() > max) {
 max = g.getId();
 }
 }
 */

    group.setId(after.stream().max((o1, o2) -> Integer.compare(o1.getId(), o2.getId())).get().getId());
    before.add(group);
    Assert.assertEquals(new HashSet<>(before), new HashSet<>(after));
  }

}
