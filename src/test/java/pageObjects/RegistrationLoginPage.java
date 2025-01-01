package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.DriverManager;
import utils.TestUtils;

import java.time.Duration;

public class RegistrationLoginPage {
    private final WebDriver driver;
    private final WebDriverWait wait;


    // Registration Form Locators
    private static final By REGISTER_NAME_INPUT = By.id("full-name");
    private static final By REGISTER_EMAIL_INPUT = By.id("email");
    private static final By REGISTER_PASSWORD_INPUT = By.xpath("//app-password[@id='password']//input[@type='password']");
    private static final By REGISTER_PASSWORD_INPUTCONFIRM = By.xpath("//app-password[@id='confirm-password']//input[@type='password']");
    private static final By REGISTER_BUTTON = By.xpath("//button[@type='submit' and contains(@class, 'btn-primary') and text()=' Sign up ']");
    private static final By REGISTRATION_SUCCESS_MESSAGE = By.xpath("//div[contains(@class, 'ml-3') and text()='Successful registration!']");

    // Login Form Locators
    private static final By LOGIN_EMAIL_INPUT = By.id("email"); // Campo de correo en el formulario de login
    private static final By LOGIN_PASSWORD_INPUT = By.xpath("//app-password[@id='password']//input[@type='password']"); // Campo de contraseña en el formulario de login
    private static final By LOGIN_BUTTON = By.xpath("//button[@type='submit']"); // Botón de enviar (login)

    // Dashboard Locators
    private static final By USER_NAME_DISPLAY = By.xpath("//span[contains(@class, 'user-name')]"); // Nombre del usuario en el Dashboard
    private static final By DASHBOARD_CONTAINER = By.xpath("//div[contains(@class, 'dashboard-container')]"); // Contenedor del Dashboard
    private static final By LOGOUT_BUTTON = By.xpath("//button[contains(@class, 'logout-button')]"); // Botón de logout

    public By getUserNameLocator(String name) {
        return By.xpath("//h2[text()='" + name + "']");  // Cambia el xpath de acuerdo con el nombre
    }

    public RegistrationLoginPage() {
        this.driver = DriverManager.getDriver();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(42)); // 42 segundos de espera
    }

    // Registration Methods
    public void navigateToRegistrationPage() {
        driver.get("https://test-qa.inlaze.com/auth/sign-up");
    }

    public void completeRegistrationForm(String name, String email, String password) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(REGISTER_NAME_INPUT)).sendKeys(name);
        driver.findElement(REGISTER_EMAIL_INPUT).sendKeys(email);
        driver.findElement(REGISTER_PASSWORD_INPUT).sendKeys(password);
        driver.findElement(REGISTER_PASSWORD_INPUTCONFIRM).sendKeys(password);
        driver.findElement(REGISTER_BUTTON).click();
    }

    public boolean isRegistrationSuccessful() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(REGISTRATION_SUCCESS_MESSAGE))
                .isDisplayed();
    }

    // Login Methods
    public void navigateToLoginPage() {
        driver.get("https://test-qa.inlaze.com/auth/login");
    }

    public void loginWithCredentials(String email, String password) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(LOGIN_EMAIL_INPUT)).sendKeys(email);
        driver.findElement(LOGIN_PASSWORD_INPUT).sendKeys(password);
        driver.findElement(LOGIN_BUTTON).click();
    }

    public String getDisplayedUserName(String name) {
        By dynamicUserNameLocator = getUserNameLocator(name);

        WebElement userNameElement = wait.until(ExpectedConditions.visibilityOfElementLocated(dynamicUserNameLocator));

        highlightElement(userNameElement);

        return userNameElement.getText();
    }


    public void logout() {
        wait.until(ExpectedConditions.elementToBeClickable(LOGOUT_BUTTON)).click();
    }

    public boolean isLoginPageDisplayed() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(LOGIN_EMAIL_INPUT))
                .isDisplayed();
    }
    private void highlightElement(WebElement element) {
        TestUtils testUtils = new TestUtils(driver);
        testUtils.highlightElement(element);
    }

}