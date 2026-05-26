package tests.api;

import api.clients.AuthClient;
import api.clients.NotesClient;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class NotesApiTest {

    @Test
    public void verifyGetAllNotesApi() {

        AuthClient.login(
                "pranaybendle18@gmail.com",
                "pass@123"
        );

        Response response =
                NotesClient.getAllNotes();

        // status validation
        Assert.assertEquals(
                response.statusCode(),
                200
        );

        // response time validation
        Assert.assertTrue(
                response.time() < 2000,
                "API response took more than 2 sec"
        );

        // notes data validation
        Assert.assertNotNull(
                response.jsonPath().getList("data")
        );

        Assert.assertTrue(
                response.jsonPath()
                        .getList("data")
                        .size() >= 0
        );

        System.out.println(
                "Status Code: " +
                        response.statusCode()
        );

        System.out.println(
                "Response Time: " +
                        response.time() + " ms"
        );

        System.out.println(
                "Response Body:"
        );

        System.out.println(
                response.asPrettyString()
        );

        System.out.println(
                "GET Notes API Passed"
        );
    }
}