package ru.stqa.pft.sandbox;

public class Collections {
  public static void main(String[] args) {
    String[] langs = {"Java", "C#", "Python", "PHP"}; //new String[4];

    for (String l: langs){
      System.out.println("Я хочу выучить " + l);
    }
  }
}
