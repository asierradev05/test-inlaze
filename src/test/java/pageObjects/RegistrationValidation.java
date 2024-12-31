package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.DriverManager;
import utils.TestUtils;

public class RegistrationValidation {

    WebDriver driver = DriverManager.getDriver();
    TestUtils testUtils = new TestUtils(driver);

    public void validateInvalidData() throws Exception {
        // Validación para nombre
        WebElement nameField = driver.findElement(By.id("full-name"));
        nameField.clear();
        testUtils.highlightElement(nameField);
        nameField.sendKeys("A"); // Ingresamos un nombre con menos de 2 palabras
        submitForm();
//        validateErrorMessage("name", "Minimum 2 words");

        // Validación para correo
        WebElement emailField = driver.findElement(By.id("email"));
        emailField.clear();
        testUtils.highlightElement(emailField);
        emailField.sendKeys("invalidemail"); // Correo con formato incorrecto
        submitForm();
//        validateErrorMessage("email", "Standard format");

        // Validación para contraseña
        WebElement passwordField = driver.findElement(By.xpath("//app-password[@id='password']//input[@type='password']"));
        passwordField.clear();
        testUtils.highlightElement(passwordField);
        passwordField.sendKeys("short"); // Contraseña corta, sin cumplir los requisitos
        submitForm();
//        validateErrorMessage("password", "Length and character requirements");

        // Validación para contraseñas no coincidentes
        WebElement confirmPasswordField = driver.findElement(By.xpath("//app-password[@id='confirm-password']//input[@type='password']"));
        confirmPasswordField.clear();
        testUtils.highlightElement(confirmPasswordField);
        confirmPasswordField.sendKeys("differentPassword"); // Contraseña que no coincide
        submitForm();
//       validateErrorMessage("password", "Passwords do not match");
    }

    private void submitForm() {
        WebElement submitButton = driver.findElement(By.xpath("//button[@type='submit']"));
        if (submitButton.getAttribute("class").contains("ng-valid")) {
            testUtils.highlightElement(submitButton);
            submitButton.click(); // Si el formulario es válido, clickeamos el botón
        } else {
            System.out.println("Form is invalid, submit button is disabled."); // No se envía el formulario, seguimos con la prueba
        }
    }

    public void validateErrorMessage(String fieldId, String expectedMessage) throws Exception {
        By errorLocator = By.xpath("//span[contains(@class, 'label-text-alt') and contains(@class, 'text-error') and contains(text(), '" + expectedMessage + "')]");
        testUtils.waitForElement(errorLocator, 5);
        WebElement errorMessage = driver.findElement(errorLocator);
        testUtils.highlightElement(errorMessage);
        assert errorMessage.isDisplayed() : "Error message not displayed for field: " + fieldId;
    }
}
