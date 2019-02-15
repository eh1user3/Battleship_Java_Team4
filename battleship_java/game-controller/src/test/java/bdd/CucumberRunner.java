package bdd;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"src/test/java/bdd"},
        plugin = {"pretty", "html:build/bdd/cucumber-html-report", "junit:build/bdd/cucumber-junit-report/allcukes.xml"},
        tags = {"@Runme"})
public class CucumberRunner {
}
