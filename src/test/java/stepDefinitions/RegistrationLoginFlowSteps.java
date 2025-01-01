package stepDefinitions;

import io.cucumber.java8.En;
import org.openqa.selenium.WebDriver;
import pageObjects.RegistrationLoginPage;
import pageObjects.LoginPage;
import utils.DriverManager;
import utils.TestUtils;
import org.junit.Assert;

public class RegistrationLoginFlowSteps implements En {
    private final WebDriver driver;
    private final RegistrationLoginPage registrationLoginPage;
    private final LoginPage loginPage;
    private final TestUtils testUtils;

    private String generatedName;
    private String generatedEmail;
    private String generatedPassword;

    public RegistrationLoginFlowSteps() {
        this.driver = DriverManager.getDriver();
        this.loginPage = new LoginPage();
        this.registrationLoginPage = new RegistrationLoginPage();

        this.testUtils = new TestUtils(driver);

        Given("^I am on the registration page for new account$", () -> {
            registrationLoginPage.navigateToRegistrationPage();
        });

        When("^I complete the registration form with generated data$", () -> {
            generatedName = testUtils.generateRandomName();
            generatedEmail = testUtils.generateRandomEmail();
            generatedPassword = testUtils.generateRandomPassword();

            registrationLoginPage.completeRegistrationForm(
                generatedName,
                generatedEmail,
                generatedPassword
            );
        });

        Then("^I should see a successful registration message$", () -> {
            Assert.assertTrue("Registration success message was not displayed",
                    registrationLoginPage.isRegistrationSuccessful());
        });

        When("^I navigate to the login page$", () -> {
            registrationLoginPage.navigateToLoginPage();
        });

        And("^I login with my registered credentials$", () -> {
            registrationLoginPage.loginWithCredentials(generatedEmail, generatedPassword);
        });

        Then("^I should see my dashboard with correct user information$", () -> {
            String displayedName = registrationLoginPage.getDisplayedUserName(generatedName);
            Assert.assertEquals("Displayed user name does not match registered name",
                    generatedName, displayedName);
        });

        And("^I should be able to logout successfully$", () -> {
            loginPage.logOut();
            Assert.assertTrue("Not redirected to login page after logout",
                    registrationLoginPage.isLoginPageDisplayed());
        });
    }
}