package utils;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

public class TestUtils {
    private static WebDriver driver;
    private final WebDriverWait wait;

    public TestUtils(WebDriver driver) {
        TestUtils.driver = driver;
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


}