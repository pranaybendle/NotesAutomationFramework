package tests.e2e;

import api.clients.AuthClient;
import api.clients.NotesClient;
import base.BaseTest;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.NotesPage;

public class NotesSearchE2ETest extends BaseTest {

    @Test
    public void verifySearchE2E() {

        String title =
                "Search Note " + System.currentTimeMillis();

        AuthClient.login(
                "pranaybendle18@gmail.com",
                "pass@123"
        );

        Response createResponse =
                NotesClient.createNote(
                        title,
                        "Created from API",
                        "Work"
                );

        String noteId =
                createResponse.jsonPath()
                        .getString("data.id");

        setUp();

        LoginPage loginPage = new LoginPage();
        NotesPage notesPage = new NotesPage();

        loginPage.login(
                "pranaybendle18@gmail.com",
                "pass@123"
        );

        notesPage.searchNote(title);

        Assert.assertTrue(
                notesPage.isNoteVisible(title)
        );

        NotesClient.deleteNote(noteId);
        tearDown();
    }
}