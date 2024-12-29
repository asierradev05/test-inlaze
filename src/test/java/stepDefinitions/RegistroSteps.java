package stepDefinitions;

import io.cucumber.java8.En;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pageObjects.RegistrationPage;
import utils.DriverManager;
import utils.TestUtils;
import java.io.IOException;

public class RegistroSteps implements En {

    WebDriver driver = DriverManager.getDriver();
    TestUtils testUtils = new TestUtils(driver);
    RegistrationPage registrationPage;

    public RegistroSteps (){
        registrationPage = new RegistrationPage();

        Given("^The user is on the registration page$", () -> {
        registrationPage.theUserIsOnTheRegistrationPage();
        });

        When("The user completes the fields {string}, {string}, {string}", (String nombre1,String nombre2, String nombre3) -> {
            registrationPage.theUserCompletesTheFields(nombre2,nombre1,nombre3);
        });

        And("Clicks on the register button", () -> {
        ;
            registrationPage.clicksOnTheRegisterButton();
        });
        Then("You should see the message {string}", (String nombre1, String nombre2,String nombre3) -> {

            registrationPage.theUserCompletesTheFields(nombre1,nombre2,nombre3);
        });

    }
}
