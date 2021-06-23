package ru.stqa.pft.addressbook.bdd;

import cucumber.api.CucumberOptions;
import cucumber.api.testng.AbstractTestNGCucumberTests;

/**
 * запускатель шагов
 */
@CucumberOptions(features = "classpath:bdd", plugin = {"pretty", "html:build/cucumber-report"}) //где-то в одной из директорий, вкл в classpath нужно найти поддиректории bdd и файлы там находятся
public class GroupTests extends AbstractTestNGCucumberTests {

}
