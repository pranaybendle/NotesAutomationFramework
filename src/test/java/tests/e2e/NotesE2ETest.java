package tests.e2e;

import api.clients.AuthClient;
import api.clients.NotesClient;
import base.BaseTest;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.NotesPage;
import listeners.TestListener;
import org.testng.annotations.Listeners;

@Listeners(TestListener.class)
public class NotesE2ETest extends BaseTest {

    @Test
    public void verifyNotesE2EFlow() {

        String title =
                "E2E Note " + System.currentTimeMillis();

        String updatedTitle =
                "Updated " + title;

        // ================= API LOGIN =================
        Response loginResponse =
                AuthClient.login(
                        "pranaybendle18@gmail.com",
                        "pass@123"
                );

        Assert.assertEquals(
                loginResponse.statusCode(),
                200
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

        Assert.assertTrue(
                createResponse.time() < 2000
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

        Assert.assertTrue(
                driver.getCurrentUrl()
                        .contains("/notes/app")
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

        // ================= API VERIFY EDIT =================
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

        // ================= UI FILTER =================
        notesPage.clickCategory("Work");

        Assert.assertTrue(
                notesPage.isNoteVisible(updatedTitle)
        );

        // ================= API DELETE =================
        Response deleteResponse =
                NotesClient.deleteNote(noteId);

        Assert.assertEquals(
                deleteResponse.statusCode(),
                200
        );

        // ================= UI LOGOUT =================
        notesPage.clickLogout();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Assert.assertTrue(
                driver.getCurrentUrl()
                        .contains("/notes/app")
        );

        //test intentionally fail krne k liye
        //Assert.assertEquals(1, 2);

        tearDown();


        System.out.println(
                "E2E Flow Passed"
        );
    }
}