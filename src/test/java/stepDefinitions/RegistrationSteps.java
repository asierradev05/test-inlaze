package stepDefinitions;

import io.cucumber.java8.En;
import io.cucumber.datatable.DataTable;
import org.openqa.selenium.WebDriver;
import pageObjects.RegistrationPage;
import utils.DriverManager;
import utils.TestUtils;
import org.junit.Assert;
import java.util.List;
import java.util.Map;

public class RegistrationSteps implements En {
    private final WebDriver driver;
    private final RegistrationPage registrationPage;
    private final TestUtils testUtils;

    public RegistrationSteps() {
        this.driver = DriverManager.getDriver();
        this.registrationPage = new RegistrationPage();
        this.testUtils = new TestUtils(driver);

        Given("^The user is on the registration page$", () -> {
            registrationPage.navigateToRegistrationPage();
        });

        When("^The user enters valid data \\(name, email, password\\)$", () -> {
            registrationPage.fillRegistrationFormWithRandomData();
            registrationPage.clickSubmitButton();
        });

        Then("^The user registers successfully$", () -> {
            Assert.assertTrue("Registration success message was not displayed",
                    registrationPage.isSuccessMessageDisplayed());
        });

        When("^The user enters invalid data:$", (DataTable dataTable) -> {
            List<Map<String, String>> data = dataTable.asMaps(String.class, String.class);
            
            for (Map<String, String> row : data) {
                String field = row.get("field");
                String value = row.get("value");

                switch (field) {
                    case "name":
                        registrationPage.validateNameField(value);
                        break;
                    case "email":
                        registrationPage.validateEmailField(value);
                        break;
                    case "password":
                        boolean isPasswordValid = registrationPage.validatePasswordFields(value, value);
                        Assert.assertTrue(
                                "Password validation failed: Passwords must match and meet the complexity requirements.",
                                isPasswordValid
                        );
                        break;
                    case "confirm-password":
                        boolean isConfirmPasswordValid = registrationPage.validatePasswordFields("Password123!", value);
                        if (!isConfirmPasswordValid) {
                            boolean isErrorDisplayed = registrationPage.isErrorMessageDisplayed("Passwords do not match.");
                        }
                        break;
                }
            }
        });

        Then("^The system shows appropriate validation messages$", () -> {
            Assert.assertTrue("Password mismatch message not displayed",
                    registrationPage.isErrorMessageDisplayed("Passwords do not match"));
        });
    }
}