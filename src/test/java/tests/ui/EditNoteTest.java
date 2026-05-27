package tests.ui;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.NotesPage;

public class EditNoteTest extends BaseTest {

    @Test
    public void verifyEditNote() {

        setUp();

        LoginPage loginPage = new LoginPage();
        NotesPage notesPage = new NotesPage();

        String oldTitle =
                "Capgemini Note " + System.currentTimeMillis();

        String updatedTitle =
                "Updated Capgemini Note " + System.currentTimeMillis();

        String updatedDesc =
                "Updated Automation Validation";

        loginPage.login(
                "pranaybendle18@gmail.com",
                "pass@123"
        );

        notesPage.createNote(
                oldTitle,
                "Original Description",
                "Home"
        );


        notesPage.editNote(
                oldTitle,
                updatedTitle,
                updatedDesc
        );


        Assert.assertTrue(
                notesPage.isNoteVisible(updatedTitle),
                "Updated note should be visible"
        );

        tearDown();
    }
}