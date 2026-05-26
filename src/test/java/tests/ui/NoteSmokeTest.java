package tests.ui;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.*;
import pages.LoginPage;
import pages.NotesPage;

public class NoteSmokeTest extends BaseTest {

    private LoginPage loginPage;
    private NotesPage notesPage;

    @BeforeMethod
    public void setupTest() {
        setUp();
        loginPage = new LoginPage();
        notesPage = new NotesPage();
    }

    @Test
    public void createNoteSmokeTest() {

        // NOTE: put your real test account here
        loginPage.login(
                "pranaybendle18@gmail.com",
                "pass@123"
        );

        notesPage.createNote(
                "Capgemini Test Note",
                "Automation Validation",
                "Home"
        );

        Assert.assertTrue(
                notesPage.isNoteVisible("Capgemini Test Note")
        );
    }

    @AfterMethod
    public void closeTest() {
        tearDown();
    }
}