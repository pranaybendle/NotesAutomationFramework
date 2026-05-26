package api.clients;

import api.utils.ApiConfig;
import api.utils.TokenManager;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;

public class NotesClient {

    public static Response getAllNotes() {

        return given()
                .baseUri(ApiConfig.BASE_URI)
                .header("x-auth-token", TokenManager.getToken())
                .when()
                .get("/notes");
    }

    public static Response deleteNote(String noteId) {

        return given()
                .baseUri(ApiConfig.BASE_URI)
                .header("x-auth-token", TokenManager.getToken())
                .when()
                .delete("/notes/" + noteId);
    }


    public static Response createNote(
            String title,
            String description,
            String category
    ) {
        return given()
                .baseUri(ApiConfig.BASE_URI)
                .header("x-auth-token", TokenManager.getToken())
                .contentType("application/json")
                .body("""
                    {
                      "title": "%s",
                      "description": "%s",
                      "category": "%s"
                    }
                    """.formatted(
                        title,
                        description,
                        category
                ))
                .when()
                .post("/notes");
    }

    public static Response updateNote(
            String noteId,
            String title,
            String description,
            String category,
            boolean completed
    ) {

        String body =
                "{\n" +
                        "\"title\":\"" + title + "\",\n" +
                        "\"description\":\"" + description + "\",\n" +
                        "\"category\":\"" + category + "\",\n" +
                        "\"completed\":" + completed + "\n" +
                        "}";

        return given()
                .baseUri(ApiConfig.BASE_URI)
                .header("x-auth-token", TokenManager.getToken())
                .contentType("application/json")
                .body(body)
                .when()
                .put("/notes/" + noteId);
    }
}