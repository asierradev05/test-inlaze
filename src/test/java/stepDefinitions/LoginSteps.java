package stepDefinitions;

import io.cucumber.java8.En;
import org.openqa.selenium.WebDriver;
import pageObjects.LoginPage;
import utils.DriverManager;
import org.junit.Assert;

public class LoginSteps implements En {
    private final WebDriver driver = DriverManager.getDriver();
    private final LoginPage loginPage = new LoginPage();

    // Constants
    private static final String BASE_URL = "https://test-qa.inlaze.com/auth/sign-in";
    private static final String VALID_EMAIL = "angel.david.sierra.g@gmail.com";
    private static final String VALID_PASSWORD = "As1234567";
    private static final String INVALID_EMAIL = "angel.david.sierra.g@gmail.com";
    private static final String INVALID_PASSWORD = "AS..2s";

    public LoginSteps() {
        Given("^The user is on the login page$", () -> {
            driver.get(BASE_URL);

            Assert.assertTrue("Login page failed to load",
                    loginPage.isLoginPageDisplayed());
        });

        When("^The user logs in with valid credentials$", () -> {

            loginPage.loginWithValidCredentials(VALID_EMAIL, VALID_PASSWORD);
        });

        Then("^The user is logged in and sees their welcome message on the dashboard$", () -> {

            Assert.assertTrue("User was not redirected to dashboard",
                    loginPage.isDashboardDisplayed());
        });

        When("^The user tries to log in with incomplete credentials$", () -> {
            loginPage.attemptLoginWithInvalidCredentials(INVALID_EMAIL, INVALID_PASSWORD);
        });

        Then("^The login form is not submitted$", () -> {

            Assert.assertTrue("The bottom not is displayed",
                    loginPage.isErrorToastDisplayed());
        });

        And("^The user logs out$", () -> {
            loginPage.logOut();
            Assert.assertTrue("User was not redirected to login page",
                    loginPage.isLoginPageDisplayed());
        });
    }
}