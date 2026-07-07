package testRunner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.AfterSuite;
import utilities.ReportOpener;

@CucumberOptions(features = { "src/test/resources/features/Registration.feature",
		"src/test/resources/features/Login.feature",
		"src/test/resources/features/LoginDDTExcel.feature" }, glue = "stepDefinitions", plugin = { "pretty",
				"html:reports/cucumber-report.html", "rerun:target/rerun.txt",
				"com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:" }, monochrome = true, dryRun = false, publish = false)

public class TestRunner extends AbstractTestNGCucumberTests {

	@AfterSuite
	public void openExtentReport() {

		ReportOpener.openExtentReport();

	}

}