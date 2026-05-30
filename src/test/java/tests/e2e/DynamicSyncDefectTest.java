package tests.e2e;

import api.clients.AuthClient;
import api.clients.NotesClient;
import base.BaseTest;
import drivers.DriverFactory;
import io.restassured.response.Response;
import listeners.TestListener;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.NotesPage;

@Listeners(TestListener.class)
public class DynamicSyncDefectTest extends BaseTest {

    private static final String EMAIL    = "pranaybendle18@gmail.com";
    private static final String PASSWORD = "pass@123";

    @Test
    public void tcE2E02_apiDeletion_uiShouldNotShowGhostData() throws InterruptedException {

        String title       = "E2E-02 Note " + System.currentTimeMillis();
        String description = "Created for ghost-data sync check";
        String category    = "Work";

        // ---- Step 1: API Login ----
        Response loginResponse = AuthClient.login(EMAIL, PASSWORD);
        Assert.assertEquals(loginResponse.getStatusCode(), 200,
                "TC-E2E-02 Setup Failure: API login was rejected.");


        Response createResp = NotesClient.createNote(title, description, category);
        Assert.assertEquals(createResp.getStatusCode(), 200,
                "TC-E2E-02 Setup Failure: Pre-requisite note could not be created via API.");
        String noteId = createResp.jsonPath().getString("data.id");


        setUp();
        LoginPage loginPage = new LoginPage();
        NotesPage notesPage = new NotesPage();

        loginPage.login(EMAIL, PASSWORD);
        Assert.assertTrue(driver.getCurrentUrl().contains("/notes/app"),
                "TC-E2E-02 Setup Failure: UI login did not reach the dashboard.");

        Thread.sleep(3000);


        notesPage.searchNote(title);
        Assert.assertTrue(notesPage.isNoteVisible(title),
                "TC-E2E-02 Setup Failure: Pre-requisite note was not found on the dashboard.");


        Response deleteResp = NotesClient.deleteNote(noteId);
        Assert.assertEquals(deleteResp.getStatusCode(), 200,
                "TC-E2E-02: API Delete was rejected — cannot proceed with sync check.");


        Thread.sleep(3000);


        By noteTitleLocator = By.xpath(
                "//div[contains(@class,'card')]//h5[contains(text(),'" + title + "')]"
        );
        boolean isGhostDataPresent = !DriverFactory.getDriver()
                .findElements(noteTitleLocator).isEmpty();

        Assert.assertFalse(isGhostDataPresent,
                "BUG-002: Note deleted via API is still visible on the UI dashboard " +
                        "without a page refresh (Dynamic Sync / Ghost Data Failure).");

        tearDown();
    }


    @Test
    public void tcE2E05_apiCreation_noteShouldAppearInstantlyOnUi() throws InterruptedException {

        String title       = "E2E-05 Note " + System.currentTimeMillis();
        String description = "Created for instant-visibility sync check";
        String category    = "Home";


        Response loginResponse = AuthClient.login(EMAIL, PASSWORD);
        Assert.assertEquals(loginResponse.getStatusCode(), 200,
                "TC-E2E-05 Setup Failure: API login was rejected.");


        setUp();
        LoginPage loginPage = new LoginPage();

        loginPage.login(EMAIL, PASSWORD);
        Assert.assertTrue(driver.getCurrentUrl().contains("/notes/app"),
                "TC-E2E-05 Setup Failure: UI login did not reach the dashboard.");

        Thread.sleep(3000);


        Response postResp = NotesClient.createNote(title, description, category);
        Assert.assertEquals(postResp.getStatusCode(), 200,
                "TC-E2E-05 Setup Failure: API note creation failed.");
        String noteId = postResp.jsonPath().getString("data.id");


        Thread.sleep(3000);


        By noteTitleLocator = By.xpath(
                "//div[contains(@class,'card')]//h5[contains(text(),'" + title + "')]"
        );
        boolean isNoteInstantlyVisible = !DriverFactory.getDriver()
                .findElements(noteTitleLocator).isEmpty();

        Assert.assertTrue(isNoteInstantlyVisible,
                "BUG-005: Note created via API POST does not appear instantly on the UI " +
                        "dashboard grid without a page refresh (Dynamic Sync Failure).");

        NotesClient.deleteNote(noteId);
        tearDown();
    }
}