package stepDefinitions;

import java.io.IOException;
import java.util.Properties;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import factory.BaseClass;
import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import utilities.ReportOpener;

public class Hooks {

	WebDriver driver;
	Properties p;
	Logger logger;

	@Before
	public void setup() throws IOException {

		driver = BaseClass.initilizeBrowser();

		logger = BaseClass.getLogger();
		logger.info("*************** Test Execution Started ***************");

		p = BaseClass.getProperties();

		driver.get(p.getProperty("appURL"));
		logger.info("Application URL launched.");

		driver.manage().window().maximize();
		logger.info("Browser window maximized.");

	}

	@After
	public void tearDown(Scenario scenario) {

		if (scenario.isFailed()) {
			logger.error("Scenario Failed : " + scenario.getName());
		} else {
			logger.info("Scenario Passed : " + scenario.getName());
		}

		driver.quit();
		ReportOpener.openExtentReport();
		logger.info("Browser closed successfully.");
		logger.info("*************** Test Execution Finished ***************");

	}

	@AfterStep
	public void addScreenshot(Scenario scenario) {

		// Screenshot will be attached only if scenario fails
		if (scenario.isFailed()) {

			TakesScreenshot ts = (TakesScreenshot) driver;
			byte[] screenshot = ts.getScreenshotAs(OutputType.BYTES);
			scenario.attach(screenshot, "image/png", scenario.getName());

			logger.error("Screenshot captured for failed scenario.");

		}

	}

}