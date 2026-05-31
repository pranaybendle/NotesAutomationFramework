package tests.api;

import api.clients.AuthClient;
import api.clients.NotesClient;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CreateNoteApiTest {

    @Test
    public void verifyCreateNoteApi() {

        AuthClient.login(
                "pranaybendle18@gmail.com",
                "pass@123"
        );

        Response response =
                NotesClient.createNote(
                        "Api Note",
                        "Created from API",
                        "Work"
                );

        Assert.assertEquals(
                response.statusCode(),
                200
        );

        Assert.assertTrue(
                response.time() < 2000,
                "API response took more than 2 sec"
        );

        Assert.assertNotNull(
                response.jsonPath()
                        .getString("data.id")
        );

        System.out.println(
                "Create Note API Passed"
        );
        System.out.println(
                "Response Time: " +
                        response.time() + " ms"
        );
        System.out.println(
                response.asPrettyString()
        );
    }
}