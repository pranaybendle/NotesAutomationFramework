package tests.ui;

import base.BaseTest;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class BrowserSmokeTest extends BaseTest {

    @BeforeMethod
    public void start() {
        setUp();
    }

    @Test
    public void openBrowser() {
        System.out.println("Browser launched successfully");
    }

    @AfterMethod
    public void close() {
        tearDown();
    }
}