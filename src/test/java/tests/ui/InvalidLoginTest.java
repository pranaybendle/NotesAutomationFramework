package tests.ui;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;

public class InvalidLoginTest extends BaseTest {

    @Test
    public void verifyInvalidLogin() {

        setUp();

        LoginPage loginPage = new LoginPage();

        loginPage.login(
                "pranaybendle18@gmail.com",
                "password"
        );

        Assert.assertTrue(
                loginPage.isLoginErrorVisible(),
                "Error message should be visible"
        );

        tearDown();
    }
}