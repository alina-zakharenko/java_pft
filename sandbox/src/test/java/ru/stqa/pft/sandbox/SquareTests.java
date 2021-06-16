package ru.stqa.pft.sandbox;

import org.testng.Assert;
import org.testng.annotations.Test;

public class SquareTests {

  @Test
  public void TestArea() {
    Square s = new Square(5);
    Assert.assertEquals(s.area(), 20);//assert s.area() == 25;

  }
}
