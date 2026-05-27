package tests.ui;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.NotesPage;

public class SearchNoteTest extends BaseTest {

    @Test
    public void verifySearchNote() {

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
                "Search Test Description",
                "Home"
        );


        notesPage.searchNote(noteTitle);


        Assert.assertTrue(
                notesPage.isNoteVisible(noteTitle),
                "Searched note should be visible"
        );

        tearDown();
    }
}