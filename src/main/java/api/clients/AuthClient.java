package api.clients;

import api.utils.ApiConfig;
import api.utils.TokenManager;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;

public class AuthClient {

    public static Response login(String email, String password) {

        Response response =
                given()
                        .baseUri(ApiConfig.BASE_URI)
                        .header("Content-Type", "application/json")
                        .body("{ \"email\": \"" + email +
                                "\", \"password\": \"" + password + "\" }")
                        .when()
                        .post("/users/login");

        if (response.statusCode() == 200) {
            String token = response.jsonPath().getString("data.token");
            TokenManager.setToken(token);
        }

        return response;
    }
}