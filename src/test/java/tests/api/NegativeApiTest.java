package tests.api;

import api.clients.AuthClient;
import api.clients.NotesClient;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import api.utils.ApiConfig;

import static io.restassured.RestAssured.*;

public class NegativeApiTest {

    @Test
    public void verifyInvalidLoginApi() {

        Response response =
                AuthClient.login(
                        "wrong@gmail.com",
                        "wrong123"
                );

        Assert.assertEquals(
                response.statusCode(),
                401
        );

        Assert.assertTrue(
                response.time() < 2000
        );

        System.out.println("Invalid Login API Passed");
        System.out.println(response.asPrettyString());
    }

    @Test
    public void verifyInvalidTokenApi() {

        Response response =
                given()
                        .baseUri(ApiConfig.BASE_URI)
                        .header("x-auth-token", "wrong_token_123")
                        .when()
                        .get("/notes");

        Assert.assertEquals(
                response.statusCode(),
                401
        );

        Assert.assertTrue(
                response.time() < 2000
        );

        System.out.println("Invalid Token API Passed");
        System.out.println(response.asPrettyString());
    }

    @Test
    public void verifyDeleteInvalidNoteApi() {

        AuthClient.login(
                "pranaybendle18@gmail.com",
                "pass@123"
        );

        Response response =
                NotesClient.deleteNote(
                        "123456789abcdef"
                );

        Assert.assertTrue(
                response.statusCode() == 400
                        || response.statusCode() == 404
        );

        Assert.assertTrue(
                response.time() < 2000
        );

        System.out.println("Invalid Note Delete API Passed");
        System.out.println(response.asPrettyString());
    }
}