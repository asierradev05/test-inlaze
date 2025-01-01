package pageObjects;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.DriverManager;
import utils.TestUtils;
import java.time.Duration;

public class LoginPage {
    private final WebDriver driver = DriverManager.getDriver();
    private final TestUtils testUtils = new TestUtils(driver);
    private final WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

    // Locators
    private final By emailFieldLocator = By.id("email");
    private final By passwordFieldLocator = By.xpath("//app-password[@id='password']//input[@type='password']");
    private final By submitButtonLocator = By.xpath("//button[@type='submit']");
    private final By dashboardLocator = By.xpath("/html/body/app-root/app-panel-root/main/section[1]");
    private final By welcomeMessageLocator = By.xpath("//h2[contains(text(),' Welcome to Lorem')]");
    private final By errorToastLocator = By.xpath("//div[contains(@class, 'flex') and contains(@class, 'items-center')]//div[contains(@class, 'text-sm') and contains(@class, 'font-normal')]");
    private final By avatarMenuLocator = By.xpath("//label[@class='btn btn-ghost btn-circle avatar']");
    private final By logoutOptionLocator = By.xpath("//a[text()='Logout']");
    private final By loginPageIndicatorLocator = By.xpath("//*[contains(text(),'Sign in')]");

    // Page state verification methods
    public boolean isLoginPageDisplayed() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(loginPageIndicatorLocator)).isDisplayed();
    }

    public boolean isDashboardDisplayed() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(dashboardLocator)).isDisplayed();
    }

    public boolean isWelcomeMessageDisplayed() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(welcomeMessageLocator)).isDisplayed();
    }

    public boolean isSubmitButtonEnabled() {
        return driver.findElement(submitButtonLocator).isEnabled();
    }

    public String getErrorToastMessage() {
        try {
            WebElement toastElement = wait.until(ExpectedConditions.visibilityOfElementLocated(errorToastLocator));
            return toastElement.getText().trim();
        } catch (Exception e) {
            return "";
        }
    }

    public boolean isErrorToastDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(errorToastLocator)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void validateLoginResponse() {
        String toastMessage = getErrorToastMessage();
        if (!toastMessage.isEmpty()) {
            System.out.println("Login Error Toast: " + toastMessage);
            throw new AssertionError("Login failed: " + toastMessage);
        }

        if (!isDashboardDisplayed()) {
            throw new AssertionError("Dashboard not displayed after login attempt");
        }
    }

    public void loginWithValidCredentials(String email, String password) {
        if (email == null || email.isEmpty()) {
            throw new RuntimeException("Email field cannot be empty");
        }
        if (password == null || password.isEmpty()) {
            throw new RuntimeException("Password field cannot be empty");
        }
        enterCredentials(email, password);
        if (isSubmitButtonEnabled()) {
            clickSubmitButton();
            handleLoginResponse();
        } else {
            throw new RuntimeException("Submit button is disabled with valid credentials");
        }
    }

    public void attemptLoginWithInvalidCredentials(String email, String password) {
        enterCredentials(email, password);
        if (isSubmitButtonEnabled()) {
            clickSubmitButton();
            validateLoginResponse();
            if (!isErrorToastDisplayed()) {
                throw new AssertionError("Error toast should be displayed for invalid credentials");
            }
        }
    }

    private void handleLoginResponse() {
        try {
            String errorMessage = getErrorToastMessage();
            if (!errorMessage.isEmpty()) {
                System.out.println("Login Error: " + errorMessage);
                throw new AssertionError("Login failed: " + errorMessage);
            }
            if (isDashboardDisplayed()) {
                System.out.println("Login successful - Dashboard displayed");
                validateDashboardContent();
            } else {
                throw new AssertionError("Dashboard not displayed after successful login");
            }
        } catch (Exception e) {
            throw new RuntimeException("Error during login response handling: " + e.getMessage());
        }
    }

    private void validateDashboardContent() {
        try {
            Assert.assertTrue("Welcome message should be visible", isWelcomeMessageDisplayed());
            System.out.println("Dashboard content validated successfully");
        } catch (Exception e) {
            throw new RuntimeException("Error validating dashboard content: " + e.getMessage());
        }
    }

    public void logOut() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(avatarMenuLocator)).click();
            wait.until(ExpectedConditions.elementToBeClickable(logoutOptionLocator)).click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(loginPageIndicatorLocator));
        } catch (Exception e) {
            throw new RuntimeException("Error during logout: " + e.getMessage());
        }
    }

    // Helper methods
    private void enterCredentials(String email, String password) {
        WebElement emailField = wait.until(ExpectedConditions.presenceOfElementLocated(emailFieldLocator));
        WebElement passwordField = wait.until(ExpectedConditions.presenceOfElementLocated(passwordFieldLocator));

        emailField.clear();
        emailField.sendKeys(email);
        testUtils.highlightElement(emailField);

        passwordField.clear();
        passwordField.sendKeys(password);
        testUtils.highlightElement(passwordField);
    }

    private void clickSubmitButton() {
        wait.until(ExpectedConditions.elementToBeClickable(submitButtonLocator)).click();
    }


}