package stepDefinitions;

import io.cucumber.java8.En;
import org.junit.After;
import utils.DriverManager;

public class Hooks {

    @After
    public void tearDown() {
        DriverManager.quitDriver();
    }
}
