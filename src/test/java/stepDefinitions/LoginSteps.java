package stepDefinitions;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;

import factory.BaseClass;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import utilities.DataReader;
import utilities.ExcelWriter;

public class LoginSteps {

	private WebDriver driver;
	private HomePage homePage;
	private LoginPage loginPage;
	private MyAccountPage myAccountPage;

	private Logger logger = BaseClass.getLogger();

	private List<HashMap<String, String>> dataMap;

	private static final String EXCEL_FILE_PATH = System.getProperty("user.dir")
			+ "\\src\\test\\resources\\testData\\Opencart_LoginData.xlsx";

	private static final String SHEET_NAME = "Sheet1";

	@Given("the user navigates to the Login page")
	public void the_user_navigates_to_the_login_page() {

		logger.info("Navigating to Login page.");

		homePage = new HomePage(BaseClass.getDriver());

		homePage.clickMyAccount();
		homePage.clickLogin();
	}

	@When("the user enters email as {string} and password as {string}")
	public void the_user_enters_email_as_and_password_as(String email, String password) {

		logger.info("Entering login credentials.");

		loginPage = new LoginPage(BaseClass.getDriver());

		loginPage.setEmail(email);
		loginPage.setPassword(password);
	}

	@When("the user clicks on the Login button")
	public void the_user_clicks_on_the_login_button() {

		logger.info("Clicking on Login button.");

		loginPage.clickLogin();
	}

	@Then("the user should be redirected to the My Account page")
	public void the_user_should_be_redirected_to_the_my_account_page() {

		logger.info("Verifying My Account page.");

		myAccountPage = new MyAccountPage(BaseClass.getDriver());

		Assert.assertTrue("User was not redirected to the My Account page.", myAccountPage.isMyAccountPageExists());

		logger.info("User successfully logged in.");
	}

	// **************** Login Data Driven Test ****************

	@Then("the user should be redirected to the My Account page by passing email and password with excel row {string}")
	public void the_user_should_be_redirected_to_the_my_account_page_by_passing_email_and_password_with_excel_row(
			String rowNumber) throws IOException {

		logger.info("Executing Login Data Driven Test.");

		dataMap = DataReader.data(EXCEL_FILE_PATH, SHEET_NAME);

		int index = Integer.parseInt(rowNumber) - 1;

		String email = dataMap.get(index).get("username");
		String password = dataMap.get(index).get("password");
		String expectedResult = dataMap.get(index).get("res");

		loginPage = new LoginPage(BaseClass.getDriver());

		loginPage.setEmail(email);
		loginPage.setPassword(password);
		loginPage.clickLogin();

		myAccountPage = new MyAccountPage(BaseClass.getDriver());

		try {

			boolean loginStatus = myAccountPage.isMyAccountPageExists();

			logger.info("Expected Result : {}", expectedResult);
			logger.info("Actual Login Status : {}", loginStatus);

			if (expectedResult.equalsIgnoreCase("Valid")) {

				if (loginStatus) {

					logger.info("Valid login verified successfully.");

					ExcelWriter.writeResult(EXCEL_FILE_PATH, SHEET_NAME, index + 1, "PASS");

					myAccountPage.clickLogout();

					Assert.assertTrue(true);

				} else {

					logger.error("Valid login failed.");

					ExcelWriter.writeResult(EXCEL_FILE_PATH, SHEET_NAME, index + 1, "ERROR");

					Assert.fail("Valid login was expected but failed.");
				}

			} else {

				if (loginStatus) {

					logger.error("Invalid login unexpectedly succeeded.");

					ExcelWriter.writeResult(EXCEL_FILE_PATH, SHEET_NAME, index + 1, "ERROR");

					myAccountPage.clickLogout();

					Assert.fail("Invalid login unexpectedly succeeded.");

				} else {

					logger.info("Invalid login verified successfully.");

					ExcelWriter.writeResult(EXCEL_FILE_PATH, SHEET_NAME, index + 1, "PASS");

					Assert.assertTrue(true);
				}
			}

		} catch (Exception e) {

			logger.error("Exception occurred during Login Data Driven Test.", e);

			ExcelWriter.writeResult(EXCEL_FILE_PATH, SHEET_NAME, index + 1, "ERROR");

			Assert.fail("Exception occurred while executing Login Data Driven Test.");
		}
	}
}