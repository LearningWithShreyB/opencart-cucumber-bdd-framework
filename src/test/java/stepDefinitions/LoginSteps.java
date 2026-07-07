package stepDefinitions;

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

public class LoginSteps {

	WebDriver driver;
	HomePage hp;
	LoginPage lp;
	MyAccountPage macc;
	Logger logger = BaseClass.getLogger();

	List<HashMap<String, String>> datamap;

	@Given("the user navigates to login page")
	public void user_navigate_to_login_page() {

		logger.info("Navigating to Login page.");

		hp = new HomePage(BaseClass.getDriver());

		hp.clickMyAccount();
		hp.clickLogin();

	}

	@When("user enters email as {string} and password as {string}")
	public void user_enters_email_as_and_password_as(String email, String pwd) {

		logger.info("Entering login credentials.");

		lp = new LoginPage(BaseClass.getDriver());

		lp.setEmail(email);
		lp.setPassword(pwd);

	}

	@When("the user clicks on the Login button")
	public void click_on_login_button() {

		lp.clickLogin();

		logger.info("Clicked on Login button.");

	}

	@Then("the user should be redirected to the MyAccount Page")
	public void user_navigates_to_my_account_page() {

		logger.info("Verifying My Account page.");

		macc = new MyAccountPage(BaseClass.getDriver());

		boolean targetPage = macc.isMyAccountPageExists();

		Assert.assertTrue(targetPage);

		logger.info("User successfully logged in.");

	}

	// *************** Data Driven Test ****************

	@Then("the user should be redirected to the MyAccount Page by passing email and password with excel row {string}")
	public void check_user_navigates_to_my_account_page_by_passing_email_and_password_with_excel_data(
			String rows) {

		logger.info("Executing Login Data Driven Test.");

		datamap = DataReader.data(
			    System.getProperty("user.dir")
			        + "\\src\\test\\resources\\testData\\Opencart_LoginData.xlsx",
			    "Sheet1");

		int index = Integer.parseInt(rows) - 1;

		String email = datamap.get(index).get("username");
		String pwd = datamap.get(index).get("password");
		String expRes = datamap.get(index).get("res");

		lp = new LoginPage(BaseClass.getDriver());

		lp.setEmail(email);
		lp.setPassword(pwd);

		lp.clickLogin();

		macc = new MyAccountPage(BaseClass.getDriver());

		try {

			boolean targetPage = macc.isMyAccountPageExists();

			logger.info("Expected Result : " + expRes);
			logger.info("Actual Login Status : " + targetPage);

			if (expRes.equalsIgnoreCase("Valid")) {

				if (targetPage) {

					macc.clickLogout();

					logger.info("Valid login successful.");

					Assert.assertTrue(true);

				} else {

					logger.error("Valid login failed.");

					Assert.fail();

				}

			} else if (expRes.equalsIgnoreCase("Invalid")) {

				if (targetPage) {

					macc.clickLogout();

					logger.error("Invalid login unexpectedly succeeded.");

					Assert.fail();

				} else {

					logger.info("Invalid login verified successfully.");

					Assert.assertTrue(true);

				}

			}

		} catch (Exception e) {

			logger.error("Exception occurred during Login DDT.", e);

			Assert.fail();

		}

	}

}