package utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.JavascriptExecutor;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

public class TestUtils {

    private static WebDriver driver;



    public TestUtils(WebDriver driver) {
        this.driver = driver;
    }


    public void highlightElement(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].style.border='3px solid blue';", element);
    }


    public String takeScreenshot(String screenshotName) throws IOException {
        TakesScreenshot screenshotDriver = (TakesScreenshot) driver;
        File srcFile = screenshotDriver.getScreenshotAs(OutputType.FILE);
        String destination = "target/screenshots/" + screenshotName + ".png";
        FileUtils.copyFile(srcFile, new File(destination));
        return destination;
    }


    public void attachScreenshotToReport(String screenshotPath) {
        String screenshotTag = "<img src='file://" + screenshotPath + "' width='600' height='400'/>";
    }


    public void highlightAndScreenshot(WebElement element, String screenshotName) throws IOException {
        highlightElement(element);
        String screenshotPath = takeScreenshot(screenshotName);
        attachScreenshotToReport(screenshotPath);
    }

    public WebElement waitForElement(By locator, int timeoutInSeconds) {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

}
