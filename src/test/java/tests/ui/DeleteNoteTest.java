package tests.ui;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.NotesPage;

public class DeleteNoteTest extends BaseTest {

    @Test
    public void verifyDeleteNote() {

        setUp();

        LoginPage loginPage = new LoginPage();
        NotesPage notesPage = new NotesPage();

        String noteTitle = "Capgemini Note";

        loginPage.login(
                "pranaybendle18@gmail.com",
                "pass@123"
        );

        notesPage.deleteNote(noteTitle);

        Assert.assertTrue(
                notesPage.isNoteNotVisible(noteTitle),
                "Note should not be visible"
        );

        tearDown();
    }
}