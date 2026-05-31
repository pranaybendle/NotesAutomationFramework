package tests.api;

import api.clients.AuthClient;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginApiTest {

    @Test
    public void verifyLoginApi() {

        Response response =
                AuthClient.login(
                        "pranaybendle18@gmail.com",
                        "pass@123"
                );

        Assert.assertEquals(
                response.statusCode(),
                200
        );

        Assert.assertTrue(
                response.time() < 2000,
                "API response took more than 2 sec"
        );

        String token =
                response.jsonPath()
                        .getString("data.token");

        Assert.assertNotNull(token);

        System.out.println(
                "Login API Passed. Token = " +
                        token
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