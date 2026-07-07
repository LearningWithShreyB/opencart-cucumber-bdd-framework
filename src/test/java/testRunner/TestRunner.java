package testRunner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "src/test/resources/features/Registration.feature",
        glue = "stepDefinitions",
        plugin = {
                "pretty",
                "html:reports/cucumber-report.html",
                "rerun:target/rerun.txt",
                "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"
        },
        monochrome = true,
        dryRun = false,
        publish = true
)

public class TestRunner extends AbstractTestNGCucumberTests {

}