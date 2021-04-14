package ru.stqa.pft.sandbox;

import org.testng.Assert;
import org.testng.annotations.Test;

public class PointTest {

  @Test
  public void TestDistance() {
    Point p = new Point(4, 2);
    Point p2 = new Point(8, 10);
    Assert.assertEquals(p.distance(p, p2), 8.94427190999916);
  }

}
