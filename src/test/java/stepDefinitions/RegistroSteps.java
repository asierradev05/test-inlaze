package stepDefinitions;

import io.cucumber.java.en.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.DriverManager;

public class RegistroSteps {

    WebDriver driver = DriverManager.getDriver();

    @Given("The user is on the registration page")
    public void theUserIsOnTheRegistrationPage() {
        driver.get("https://test-qa.inlaze.com/auth/sign-in"); // URL de prueba
    }

    @When("The user completes the fields {string}, {string}, {string}")
    public void theUserCompletesTheFields(String name, String email, String password) {
        driver.findElement(By.id("name")).sendKeys(name);
        driver.findElement(By.id("email")).sendKeys(email);
        driver.findElement(By.id("password")).sendKeys(password);
        driver.findElement(By.id("confirmPassword")).sendKeys(password);
    }

    @And("Clicks on the register button")
    public void clicksOnTheRegisterButton() {
        driver.findElement(By.id("registerSubmit")).click();
    }

    @Then("You should see the message {string}")
    public void youShouldSeeTheMessage(String expectedMessage) {
        WebElement message = driver.findElement(By.id("welcomeMessage"));
        assert message.getText().contains(expectedMessage);
        DriverManager.quitDriver();

    }
}
