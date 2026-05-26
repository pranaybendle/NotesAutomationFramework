package tests.e2e;

import api.clients.AuthClient;
import api.clients.NotesClient;
import base.BaseTest;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.NotesPage;

public class NotesDeleteE2ETest extends BaseTest {

    @Test
    public void verifyDeleteE2E() {

        String title =
                "Delete Note " + System.currentTimeMillis();

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

        NotesClient.deleteNote(noteId);

        notesPage.searchNote(title);

        Assert.assertTrue(
                notesPage.isNoteNotVisible(title)
        );

        tearDown();
    }
}