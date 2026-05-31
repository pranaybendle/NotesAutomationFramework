package drivers;

import config.ConfigReader;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;

public class DriverFactory {

    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public static void initializeDriver() {

        if (driver.get() != null) {
            try { driver.get().quit(); } catch (Exception ignored) {}
            driver.remove();
        }

        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-popup-blocking");


        WebDriver webDriver = new ChromeDriver(options);
        webDriver.manage().window().maximize();
        webDriver.manage().timeouts()
                .implicitlyWait(Duration.ofSeconds(
                        Long.parseLong(ConfigReader.getProperty("implicitWait"))
                ));

        driver.set(webDriver);
    }

    public static WebDriver getDriver() {
        return driver.get();
    }

    public static void quitDriver() {
        if (driver.get() != null) {
            try { driver.get().quit(); } catch (Exception ignored) {}
            driver.remove();
        }
    }
}