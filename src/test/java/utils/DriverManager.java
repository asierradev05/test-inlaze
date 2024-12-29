package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import io.github.bonigarcia.wdm.WebDriverManager;

public class DriverManager {

    private static WebDriver driver;

    // Método para inicializar el WebDriver (si no está ya inicializado)
    public static WebDriver getDriver() {
        if (driver == null) {
            WebDriverManager.edgedriver().setup();
            EdgeOptions options = new EdgeOptions();
            driver = new EdgeDriver(options);
            driver.manage().window().maximize();
        }
        return driver;
    }

    // Método para cerrar y limpiar el WebDriver
    public static void quitDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}