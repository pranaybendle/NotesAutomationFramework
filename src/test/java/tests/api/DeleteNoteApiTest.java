package tests.api;

import api.clients.AuthClient;
import api.clients.NotesClient;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class DeleteNoteApiTest {

    @Test
    public void verifyDeleteNoteApi() {

        String title =
                "Delete API Note " + System.currentTimeMillis();

        // ================= LOGIN =================
        AuthClient.login(
                "pranaybendle18@gmail.com",
                "pass@123"
        );

        // ================= CREATE NOTE =================
        Response createResponse =
                NotesClient.createNote(
                        title,
                        "Created for delete test",
                        "Work"
                );

        Assert.assertEquals(
                createResponse.statusCode(),
                200
        );

        String noteId =
                createResponse.jsonPath()
                        .getString("data.id");

        // ================= DELETE NOTE =================
        Response deleteResponse =
                NotesClient.deleteNote(noteId);

        // status validation
        Assert.assertEquals(
                deleteResponse.statusCode(),
                200
        );

        // response time validation
        Assert.assertTrue(
                deleteResponse.time() < 2000,
                "API response took more than 2 sec"
        );

        // noteId validation
        Assert.assertNotNull(noteId);

        // ================= VERIFY DELETED =================
        Response notesResponse =
                NotesClient.getAllNotes();

        String deletedTitle =
                notesResponse.jsonPath()
                        .getString(
                                "data.find { it.id == '"
                                        + noteId
                                        + "' }.title"
                        );

        Assert.assertNull(
                deletedTitle
        );

        System.out.println(
                "Deleted Note ID: " + noteId
        );

        System.out.println(
                "Response Time: "
                        + deleteResponse.time()
                        + " ms"
        );

        System.out.println(
                deleteResponse.asPrettyString()
        );
    }
}