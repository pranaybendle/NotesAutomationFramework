package tests.ui;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;

public class LoginTest extends BaseTest {

    @Test
    public void verifyValidLogin() {

        setUp();

        LoginPage loginPage = new LoginPage();

        loginPage.login(
                "pranaybendle18@gmail.com",
                "pass@123"
        );

        Assert.assertTrue(
                driver.getCurrentUrl().contains("/notes/app"),
                "User should login successfully"
        );

        tearDown();
    }
}