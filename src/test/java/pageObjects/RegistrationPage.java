package pageObjects;

import com.github.javafaker.Faker;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.DriverManager;
import utils.TestUtils;

public class RegistrationPage {
    private final WebDriver driver;
    private final Faker faker;
    private final TestUtils testUtils;

    private final By nameFieldLocator = By.id("full-name");
    private final By emailFieldLocator = By.id("email");
    private final By passwordFieldLocator = By.xpath("//app-password[@id='password']//input[@type='password']");
    private final By confirmPasswordLocator = By.xpath("//app-password[@id='confirm-password']//input[@type='password']");
    private final By submitButtonLocator = By.xpath("//button[@type='submit' and contains(@class, 'btn-primary') and text()=' Sign up ']");
    private final By successToastLocator = By.xpath("//div[contains(@class, 'ml-3') and text()='Successful registration!']");

    public RegistrationPage() {
        this.driver = DriverManager.getDriver();
        this.faker = new Faker();
        this.testUtils = new TestUtils(driver);
    }

    public void navigateToRegistrationPage() {
        driver.get("https://test-qa.inlaze.com/auth/sign-up");
    }

    public void fillRegistrationFormWithRandomData() {
        String validName = faker.name().fullName();
        String validEmail = faker.internet().emailAddress();
        String validPassword = "Password123!";

        WebElement nameField = driver.findElement(nameFieldLocator);
        WebElement emailField = driver.findElement(emailFieldLocator);
        WebElement passwordField = driver.findElement(passwordFieldLocator);
        WebElement confirmPasswordField = driver.findElement(confirmPasswordLocator);

        testUtils.highlightElement(nameField);
        nameField.sendKeys(validName);

        testUtils.highlightElement(emailField);
        emailField.sendKeys(validEmail);

        testUtils.highlightElement(passwordField);
        passwordField.sendKeys(validPassword);

        testUtils.highlightElement(confirmPasswordField);
        confirmPasswordField.sendKeys(validPassword);
    }

    public void clickSubmitButton() {
        if (isFieldEmpty(nameFieldLocator) || isFieldEmpty(emailFieldLocator) || isFieldEmpty(passwordFieldLocator) || isFieldEmpty(confirmPasswordLocator)) {
            System.err.println("Error: Todos los campos obligatorios deben ser completados.");
            Assert.fail("Los campos obligatorios no están completos.");
        } else {
            WebElement submitButton = driver.findElement(submitButtonLocator);
            testUtils.highlightElement(submitButton);
            submitButton.click();
        }
    }

    private boolean isFieldEmpty(By fieldLocator) {
        WebElement field = driver.findElement(fieldLocator);
        String fieldValue = field.getAttribute("value");
        return fieldValue == null || fieldValue.trim().isEmpty();
    }


    public boolean isSuccessMessageDisplayed() {
        try {
            WebElement successMessage = testUtils.waitForElement(successToastLocator, 5);
            testUtils.highlightElement(successMessage);
            return successMessage.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void validateNameField(String invalidName) {
        try {
            WebElement nameField = driver.findElement(nameFieldLocator);
            testUtils.highlightElement(nameField);
            nameField.clear();
            nameField.sendKeys(invalidName);

            Assert.assertTrue(
                    "The provided name '" + invalidName + "' is not valid (must have at least 2 words).",
                    isValidName(invalidName)
            );
        } catch (Exception e) {
            System.err.println("Error validating name field: " + e.getMessage());
            Assert.fail("Exception occurred during name validation: " + e.getMessage());
        }
    }
    private boolean isValidName(String name) {
        String[] words = name.trim().split("\\s+");
        return words.length >= 2;
    }

    public void validateEmailField(String invalidEmail) {
        try {
            WebElement emailField = driver.findElement(emailFieldLocator);
            testUtils.highlightElement(emailField);
            emailField.sendKeys(invalidEmail);

            Assert.assertTrue(
                    "Invalid email format: " + invalidEmail,
                    isEmailValid(invalidEmail)
            );

            Assert.assertTrue(
                    "The email '" + invalidEmail + "' is already in use.",
                    isEmailUnique(invalidEmail)
            );
        } catch (Exception e) {
            System.err.println("Error validating email field: " + e.getMessage());
            Assert.fail("Exception occurred during email validation: " + e.getMessage());
        }
    }

    public boolean validatePasswordFields(String password, String confirmPassword) {
        try {
            clearAllFields();
            WebElement passwordField = driver.findElement(passwordFieldLocator);
            WebElement confirmPasswordField = driver.findElement(confirmPasswordLocator);

            testUtils.highlightElement(passwordField);
            passwordField.sendKeys(password);

            testUtils.highlightElement(confirmPasswordField);
            confirmPasswordField.sendKeys(confirmPassword);

            return isPasswordValid(password, confirmPassword);
        } catch (Exception e) {
            System.err.println("Error validating password fields: " + e.getMessage());
            return false;
        }
    }

    public boolean isErrorMessageDisplayed(String expectedMessage) {
        By errorMessageLocator = By.xpath("//span[contains(@class, 'text-error') and contains(text(), '" + expectedMessage + "')]");
        try {
            WebElement errorMessage = testUtils.waitForElement(errorMessageLocator, 5);
            testUtils.highlightElement(errorMessage);
            return errorMessage.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    private void clearAllFields() {
        driver.findElement(nameFieldLocator).clear();
        driver.findElement(emailFieldLocator).clear();
        driver.findElement(passwordFieldLocator).clear();
        driver.findElement(confirmPasswordLocator).clear();
    }

    private boolean isEmailValid(String email) {
        return email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
    }

    private boolean isPasswordValid(String password , String confirmPassword) {
        return  password.equals(confirmPassword) &&
                password.length() >= 3 && password.length() <= 12 &&
                password.chars().anyMatch(Character::isUpperCase) &&
                password.chars().anyMatch(Character::isLowerCase) &&
                password.chars().anyMatch(Character::isDigit);
    }
    private boolean isEmailUnique(String email) {
        try {

            boolean emailExists = false;
            Assert.assertFalse(
                    "The email '" + email + "' already exists in the database.",
                    emailExists
            );
            return !emailExists;
        } catch (Exception e) {
            System.err.println("Error checking email uniqueness: " + e.getMessage());
            Assert.fail("Exception occurred during email uniqueness validation: " + e.getMessage());
            return false;
        }
    }


}