package ru.stqa.pft.sandbox;

public class Point {
  public double x;
  public double y;

  public Point(double x, double y) {
    this.x = x;
    this.y = y;
  }

  public double distance(Point p1) {
    double d = Math.sqrt((this.x - p1.x) * (this.x - p1.x) + (this.y - p1.y) * (this.y - p1.y));
    return d;
  }


  public static void main(String[] args) {
    Point p1 = new Point(2, 1);
    Point p2 = new Point(6, 4);

    System.out.println(p1.distance(p2));
  }
}
