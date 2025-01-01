package utils;

import com.github.javafaker.Faker;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class TestUtils {
    private static WebDriver driver;
    private final WebDriverWait wait;
    private final Faker faker;

    public TestUtils(WebDriver driver) {
        TestUtils.driver = driver;
        this.faker = new Faker();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void highlightElement(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        String originalStyle = element.getAttribute("style");
        js.executeScript("arguments[0].style.border='3px solid blue';", element);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        js.executeScript("arguments[0].setAttribute('style', '" + originalStyle + "');", element);
    }

    public WebElement waitForElement(By locator, int timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public String generateRandomName() {
        return faker.name().fullName(); // Genera un nombre completo aleatorio
    }

    public String generateRandomEmail() {
        return faker.internet().emailAddress(); // Genera un correo electrónico aleatorio
    }

    public String generateRandomPassword() {
        return faker.internet().password(3, 13, true, true);
    }
}
