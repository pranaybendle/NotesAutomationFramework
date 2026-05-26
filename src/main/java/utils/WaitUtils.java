package utils;

import drivers.DriverFactory;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class WaitUtils {

    private static final int TIMEOUT = 20;

    public static WebElement waitForVisible(By locator) {
        return new WebDriverWait(
                DriverFactory.getDriver(),
                Duration.ofSeconds(TIMEOUT)
        ).until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public static WebElement waitForClickable(By locator) {
        return new WebDriverWait(
                DriverFactory.getDriver(),
                Duration.ofSeconds(TIMEOUT)
        ).until(ExpectedConditions.elementToBeClickable(locator));
    }

    public static boolean waitForInvisibility(By locator) {
        return new WebDriverWait(
                DriverFactory.getDriver(),
                Duration.ofSeconds(TIMEOUT)
        ).until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    public static void waitForPageLoad() {
        WebDriver driver = DriverFactory.getDriver();

        new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT))
                .until(webDriver ->
                        ((JavascriptExecutor) webDriver)
                                .executeScript("return document.readyState")
                                .equals("complete"));
    }
}