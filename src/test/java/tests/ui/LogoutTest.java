package tests.ui;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.NotesPage;

public class LogoutTest extends BaseTest {

    @Test
    public void verifyLogout() {

        setUp();

        LoginPage loginPage = new LoginPage();
        NotesPage notesPage = new NotesPage();

        loginPage.login(
                "pranaybendle18@gmail.com",
                "pass@123"
        );

        notesPage.clickLogout();

        Assert.assertTrue(
                driver.getCurrentUrl().contains("/notes/app"),
                "User should be redirected to login page after logout"
        );

        tearDown();
    }
}