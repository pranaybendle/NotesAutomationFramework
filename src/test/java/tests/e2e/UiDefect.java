package tests.e2e;

import api.clients.AuthClient;
import api.clients.NotesClient;
import base.BaseTest;
import io.restassured.response.Response;
import listeners.TestListener;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.DefectNotesPage;
import pages.LoginPage;

@Listeners(TestListener.class)
public class UiDefect extends BaseTest {

    @Test
    public void verifyUiSynchronizationDefect() {

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

        String noteId =
                createResponse.jsonPath()
                        .getString("data.id");

        // ================= UI LOGIN =================
        setUp();

        LoginPage loginPage =
                new LoginPage();

        // Using defect simulation page
        DefectNotesPage notesPage =
                new DefectNotesPage();

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
                notesPage.isNoteVisible(title),
                "Created note not visible"
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

        // ===================================================
        // KNOWN UI SYNCHRONIZATION DEFECT SIMULATION
        // No page refresh added intentionally
        // Updated note may not appear immediately
        // ===================================================

        notesPage.clickCategory("Work");

        notesPage.searchNote(updatedTitle);

        Assert.assertTrue(
                notesPage.isNoteVisibleWithoutRefresh(updatedTitle),
                "Known Defect: Updated note not visible without refresh"
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
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Assert.assertTrue(
                driver.getCurrentUrl()
                        .contains("/notes/app"),
                "Logout failed"
        );

        tearDown();

        System.out.println(
                "UI Defect Simulation Completed"
        );
    }
}