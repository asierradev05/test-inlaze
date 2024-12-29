package stepDefinitions;

import io.cucumber.java.After;
import utils.DriverManager;

public class Hooks {

    @After
    public void tearDown() {
        DriverManager.quitDriver();
    }
}
