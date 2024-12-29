package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.DriverManager;
import utils.TestUtils;
import java.io.IOException;


public class RegistrationPage {
    WebDriver driver = DriverManager.getDriver();
    TestUtils testUtils = new TestUtils(driver);


    public void theUserCompletesTheFields(String name, String email, String password) throws IOException {
        WebElement nameField = driver.findElement(By.id("name"));
        WebElement emailField = driver.findElement(By.id("email"));
        WebElement passwordField = driver.findElement(By.id("password"));

        nameField.sendKeys(name);
        emailField.sendKeys(email);
        passwordField.sendKeys(password);


        testUtils.highlightAndScreenshot(nameField, "name_field_filled");
        testUtils.highlightAndScreenshot(emailField, "email_field_filled");
        testUtils.highlightAndScreenshot(passwordField, "password_field_filled");
    }
    public void theUserIsOnTheRegistrationPage() throws IOException {
        driver.get("https://test-qa.inlaze.com/auth/sign-in");

        WebElement registerButton = driver.findElement(By.id("registerSubmit"));
        testUtils.highlightAndScreenshot(registerButton, "registration_page");
    }
    public void clicksOnTheRegisterButton() throws IOException {
        WebElement registerButton = driver.findElement(By.id("registerSubmit"));
        testUtils.highlightAndScreenshot(registerButton, "register_button_clicked");
        registerButton.click();
    }
    public void youShouldSeeTheMessage(String expectedMessage) throws IOException {
        WebElement message = driver.findElement(By.id("welcomeMessage"));
        testUtils.highlightAndScreenshot(message, "welcome_message");

        assert message.getText().contains(expectedMessage);
    }

}

