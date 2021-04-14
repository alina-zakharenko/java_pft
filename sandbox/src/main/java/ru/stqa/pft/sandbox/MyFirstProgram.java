package ru.stqa.pft.sandbox;

public class MyFirstProgram {

  public static void main(String[] args) {
    hello(" world");
    hello(" Alina");
    hello(" Mama");


    Square s = new Square(5);
    //s.l = 5.0;
    System.out.println("Площадь квадрата со стороной " + s.l + " = " + area(s));

    Rectangle r = new Rectangle(4, 6);
    //r.a = 4;
   // r.b = 6;
    System.out.println("Площадь прямоугольника со сторонами " + r.a + " и " + r.b + " = " + area(r));
  }

  public static void hello(String text) {
    //String text = "world";
    System.out.println("Hello, " + text + "!");

  }

  public static double area(Square square) {
    return square.l * square.l;
  }

  public static double area(Rectangle rectangle) {
    return rectangle.a * rectangle.b;
  }
}
