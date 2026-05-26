package tests.api;

import api.clients.AuthClient;
import api.clients.NotesClient;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class DeleteAllNotesApiTest {

    @Test
    public void deleteAllNotes() {

        AuthClient.login(
                "pranaybendle18@gmail.com",
                "pass@123"
        );

        Response response =
                NotesClient.getAllNotes();

        List<String> noteIds =
                response.jsonPath()
                        .getList("data.id");

        for (String noteId : noteIds) {

            Response deleteResponse =
                    NotesClient.deleteNote(noteId);

            Assert.assertEquals(
                    deleteResponse.statusCode(),
                    200
            );

            System.out.println(
                    "Deleted: " + noteId
            );
        }

        System.out.println(
                "All Notes Deleted"
        );
    }
}