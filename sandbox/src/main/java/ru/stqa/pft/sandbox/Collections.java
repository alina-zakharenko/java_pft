package ru.stqa.pft.sandbox;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Collections {
  public static void main(String[] args) {
    String[] langs = {"Java", "C#", "Python", "PHP"}; //new String[4]; массив

    List<String> languages = Arrays.asList("Java", "C#", "Python", "PHP");//список привели к массиву
//    languages.add("Java");
//    languages.add("C#");
//    languages.add("Python");

    for (String l: languages){
      System.out.println("Я хочу выучить " + l);
    }

//    for (int i = 0; i < languages.size(); i++) {
//      System.out.println("Я хочу выучить " + languages.get(i));
//    }
  }
}
