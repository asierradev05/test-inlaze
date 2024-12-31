package pageObjects;

import com.github.javafaker.Faker;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.DriverManager;
import utils.TestUtils;

import java.time.Duration;
import java.util.Map;

public class RegistrationPage {

    WebDriver driver = DriverManager.getDriver();
    Faker faker = new Faker();
    TestUtils testUtils = new TestUtils(driver);

    public void theUserIsOnTheRegistrationPage() {
        driver.get("https://test-qa.inlaze.com/auth/sign-up");
    }

    public void theUserEntersValidData() {

        WebElement nameField = driver.findElement(By.id("full-name"));
        WebElement emailField = driver.findElement(By.id("email"));

        WebElement passwordField = driver.findElement(By.xpath("//app-password[@id='password']//input[@type='password']"));
        WebElement passwordFieldCheck = driver.findElement(By.xpath("//app-password[@id='confirm-password']//input[@type='password']"));
        WebElement sumbitButton = driver.findElement(By.xpath("//button[@type='submit' and contains(@class, 'btn-primary') and text()=' Sign up ']"));

        String validName = faker.name().fullName();
        String validEmail = faker.internet().emailAddress();
        String validPassword = "Password123";

        testUtils.highlightElement(nameField);
        testUtils.highlightElement(emailField);
        testUtils.highlightElement(passwordField);
        testUtils.highlightElement(passwordFieldCheck);

        nameField.sendKeys(validName);
        emailField.sendKeys(validEmail);
        passwordField.sendKeys(validPassword);
        passwordFieldCheck.sendKeys(validPassword);

        testUtils.highlightElement(sumbitButton);
        sumbitButton.click();
    }

    public void theUserRegistersSuccessfully() {
        By validationToastLocator = By.xpath("//div[contains(@class, 'ml-3') and contains(@class, 'text-sm') and contains(@class, 'font-normal') and text()='Successful registration!']");

        WebElement validationToast = testUtils.waitForElement(validationToastLocator, 5);
        testUtils.highlightElement(validationToast);
        assert validationToast.isDisplayed() : "Validation toast was not displayed!";


    }
}
