package base;

import config.ConfigReader;
import drivers.DriverFactory;
import org.openqa.selenium.WebDriver;

public class BaseTest {

    protected WebDriver driver;

    public void setUp() {
        DriverFactory.initializeDriver();
        driver = DriverFactory.getDriver();

        // old browser state remove
        driver.manage().deleteAllCookies();

        driver.get(
                ConfigReader.getProperty("baseUrl")
        );
    }

    public void tearDown() {
        DriverFactory.quitDriver();
    }
}