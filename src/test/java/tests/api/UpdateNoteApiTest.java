package tests.api;

import api.clients.AuthClient;
import api.clients.NotesClient;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class UpdateNoteApiTest {

    @Test
    public void verifyUpdateNoteApi() {

        String title =
                "API Update Note " + System.currentTimeMillis();

        String updatedTitle =
                "Updated API Note";

        AuthClient.login(
                "pranaybendle18@gmail.com",
                "pass@123"
        );

        Response createResponse =
                NotesClient.createNote(
                        title,
                        "Created for update test",
                        "Home"
                );

        Assert.assertEquals(
                createResponse.statusCode(),
                200
        );

        String noteId =
                createResponse.jsonPath()
                        .getString("data.id");

        Response updateResponse =
                NotesClient.updateNote(
                        noteId,
                        updatedTitle,
                        "Updated from API",
                        "Work",
                        false
                );

        Assert.assertEquals(
                updateResponse.statusCode(),
                200
        );

        Assert.assertTrue(
                updateResponse.time() < 2000,
                "API response took more than 2 sec"
        );

        Assert.assertEquals(
                updateResponse.jsonPath()
                        .getString("data.title"),
                updatedTitle
        );

        Assert.assertEquals(
                updateResponse.jsonPath()
                        .getString("data.category"),
                "Work"
        );

        Response notesResponse =
                NotesClient.getAllNotes();

        String verifiedTitle =
                notesResponse.jsonPath()
                        .getString(
                                "data.find { it.id == '"
                                        + noteId
                                        + "' }.title"
                        );

        Assert.assertEquals(
                verifiedTitle,
                updatedTitle
        );

        // ================= CLEANUP =================
        NotesClient.deleteNote(noteId);

        System.out.println(
                "Updated Note ID: " + noteId
        );

        System.out.println(
                "Status Code: "
                        + updateResponse.statusCode()
        );

        System.out.println(
                "Response Time: "
                        + updateResponse.time()
                        + " ms"
        );

        System.out.println(
                updateResponse.asPrettyString()
        );

        System.out.println(
                "Update Note API Passed"
        );
    }
}