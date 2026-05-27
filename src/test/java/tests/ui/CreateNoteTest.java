package tests.ui;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.NotesPage;

public class CreateNoteTest extends BaseTest {

    @Test
    public void verifyCreateNote() {

        setUp();

        LoginPage loginPage = new LoginPage();
        NotesPage notesPage = new NotesPage();

        loginPage.login(
                "pranaybendle18@gmail.com",
                "pass@123"
        );

        notesPage.createNote(
                "Capgemini Note",
                "BDD Automation Test",
                "Home"
        );

        Assert.assertTrue(
                notesPage.isNoteVisible("Capgemini Note"),
                "Note should be visible"
        );

        tearDown();
    }
}