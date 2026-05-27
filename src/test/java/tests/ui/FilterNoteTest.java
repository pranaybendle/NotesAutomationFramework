package tests.ui;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.NotesPage;

public class FilterNoteTest extends BaseTest {

    @Test
    public void verifyFilterNote() {

        setUp();

        LoginPage loginPage = new LoginPage();
        NotesPage notesPage = new NotesPage();

        String noteTitle =
                "Capgemini Note " + System.currentTimeMillis();

        loginPage.login(
                "pranaybendle18@gmail.com",
                "pass@123"
        );


        notesPage.createNote(
                noteTitle,
                "Filter Test Description",
                "Home"
        );


        notesPage.clickCategory("Home");


        Assert.assertTrue(
                notesPage.isNoteVisible(noteTitle),
                "Filtered note should be visible"
        );

        tearDown();
    }
}