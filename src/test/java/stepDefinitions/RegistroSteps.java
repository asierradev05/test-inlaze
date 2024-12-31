package stepDefinitions;

import io.cucumber.java8.En;
import org.openqa.selenium.WebDriver;
import pageObjects.RegistrationPage;
import pageObjects.RegistrationValidation;
import utils.DriverManager;
import utils.TestUtils;

public class RegistroSteps implements En {

    WebDriver driver = DriverManager.getDriver();
    TestUtils testUtils = new TestUtils(driver);
    RegistrationPage registrationPage;
    RegistrationValidation registrationValidation;

    public RegistroSteps() {
        registrationPage = new RegistrationPage();
        registrationValidation = new RegistrationValidation();

        Given("^The user is on the registration page$", () -> {
            registrationPage.theUserIsOnTheRegistrationPage();
        });

        When("The user enters valid data \\(name, email, password)", () -> {
            registrationPage.theUserEntersValidData();
        });

        Then("The user registers successfully", () -> {
            registrationPage.theUserRegistersSuccessfully();
        });

        When("The user enters invalid data or leaves mandatory fields empty", () -> {
            registrationValidation.validateInvalidData();
        });

        Then("The user sees validation messages for the name, email, and password fields", () -> {
//            registrationValidation.validateErrorMessage("name", "Minimum 2 words");
//            registrationValidation.validateErrorMessage("email", "Standard format");
//            registrationValidation.validateErrorMessage("password", "Length and character requirements");
        });

        Then("The user sees a password mismatch message", () -> {
            registrationValidation.validateErrorMessage("password", "Passwords do not match");
        });
    }
}
