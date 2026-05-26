package tests.e2e;

import api.clients.AuthClient;
import api.clients.NotesClient;
import base.BaseTest;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.NotesPage;

public class NotesEditE2ETest extends BaseTest {

    @Test
    public void verifyEditE2E() {

        String title =
                "Edit Note " + System.currentTimeMillis();

        String updatedTitle =
                "Updated " + title;

        // ================= API LOGIN =================
        AuthClient.login(
                "pranaybendle18@gmail.com",
                "pass@123"
        );

        // ================= API CREATE =================
        Response createResponse =
                NotesClient.createNote(
                        title,
                        "Created from API",
                        "Work"
                );

        Assert.assertEquals(
                createResponse.statusCode(),
                200
        );

        String noteId =
                createResponse.jsonPath()
                        .getString("data.id");

        // ================= UI LOGIN =================
        setUp();

        LoginPage loginPage =
                new LoginPage();

        NotesPage notesPage =
                new NotesPage();

        loginPage.login(
                "pranaybendle18@gmail.com",
                "pass@123"
        );

        // ================= UI SEARCH =================
        notesPage.searchNote(title);

        Assert.assertTrue(
                notesPage.isNoteVisible(title)
        );

        // ================= UI EDIT =================
        notesPage.editNote(
                title,
                updatedTitle,
                "Updated from UI"
        );

        // ================= API VERIFY =================
        Response notesResponse =
                NotesClient.getAllNotes();

        String updatedTitleFromApi =
                notesResponse.jsonPath()
                        .getString(
                                "data.find { it.id == '"
                                        + noteId
                                        + "' }.title"
                        );

        Assert.assertEquals(
                updatedTitleFromApi,
                updatedTitle
        );

        // ================= CLEANUP =================
        NotesClient.deleteNote(noteId);

        tearDown();

        System.out.println(
                "Edit E2E Passed"
        );
    }
}