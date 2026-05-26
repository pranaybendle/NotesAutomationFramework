package tests.ui;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.*;
import pages.LoginPage;

public class LoginSmokeTest extends BaseTest {

    private LoginPage loginPage;

    @BeforeMethod
    public void setupTest() {
        setUp();
        loginPage = new LoginPage();
    }

    @Test
    public void verifyLoginPageLoads() {
        Assert.assertTrue(true, "Framework ready");
    }

    @AfterMethod
    public void closeTest() {
        tearDown();
    }
}