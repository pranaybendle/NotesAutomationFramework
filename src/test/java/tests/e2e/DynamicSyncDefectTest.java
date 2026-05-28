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

/**
 * Dynamic Sync Defect Tests
 *
 * TC-E2E-02: Reverse Dynamic Sync — API Deletion -> UI Ghost Data Check
 *   DESIGNED TO FAIL — proves BUG-002:
 *   After a note is deleted via API, its card remains visible on the UI
 *   because the app has no live sync. The user sees stale/ghost data.
 *
 * TC-E2E-05: Forward Dynamic Sync — API Creation -> UI Instant Visibility
 *   DESIGNED TO FAIL — proves BUG-005:
 *   A note created via API does not appear on the UI dashboard without a
 *   manual page refresh because the app has no live sync.
 */
@Listeners(TestListener.class)
public class DynamicSyncDefectTest extends BaseTest {

    private static final String EMAIL    = "pranaybendle18@gmail.com";
    private static final String PASSWORD = "pass@123";

    // =========================================================================
    // TC-E2E-02 : Reverse Dynamic Sync — API Deletion -> UI Ghost Data Check
    // =========================================================================
    @Test
    public void tcE2E02_apiDeletion_uiShouldNotShowGhostData() throws InterruptedException {

        String title       = "E2E-02 Note " + System.currentTimeMillis();
        String description = "Created for ghost-data sync check";
        String category    = "Work";

        // ---- Step 1: API Login ----
        Response loginResponse = AuthClient.login(EMAIL, PASSWORD);
        Assert.assertEquals(loginResponse.getStatusCode(), 200,
                "TC-E2E-02 Setup Failure: API login was rejected.");

        // ---- Step 2: Create note via API ----
        Response createResp = NotesClient.createNote(title, description, category);
        Assert.assertEquals(createResp.getStatusCode(), 200,
                "TC-E2E-02 Setup Failure: Pre-requisite note could not be created via API.");
        String noteId = createResp.jsonPath().getString("data.id");

        // ---- Step 3: Open browser and login via UI ----
        setUp();
        LoginPage loginPage = new LoginPage();
        NotesPage notesPage = new NotesPage();

        loginPage.login(EMAIL, PASSWORD);
        Assert.assertTrue(driver.getCurrentUrl().contains("/notes/app"),
                "TC-E2E-02 Setup Failure: UI login did not reach the dashboard.");

        Thread.sleep(3000);

        // ---- Step 4: Search so the note card is actively rendered on screen ----
        // This simulates a real user who can see the note on their dashboard.
        notesPage.searchNote(title);
        Assert.assertTrue(notesPage.isNoteVisible(title),
                "TC-E2E-02 Setup Failure: Pre-requisite note was not found on the dashboard.");

        // ---- Step 5: While the card is still visible on screen, delete via API only ----
        // No browser interaction at all from this point forward.
        Response deleteResp = NotesClient.deleteNote(noteId);
        Assert.assertEquals(deleteResp.getStatusCode(), 200,
                "TC-E2E-02: API Delete was rejected — cannot proceed with sync check.");

        // ---- Step 6: Wait — give the UI time to auto-sync (it won't, because there is no live sync) ----
        Thread.sleep(3000);

        // ---- Step 7: Check if the card is STILL visible in the DOM without any browser action ----
        // The app has no WebSocket/live sync so the card remains rendered — ghost data.
        // Expected by bug: card IS still there  → isGhostDataPresent = true
        // This assert is DESIGNED TO FAIL to prove BUG-002.
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

    // =========================================================================
    // TC-E2E-05 : Forward Dynamic Sync — API Creation -> UI Instant Visibility
    // =========================================================================
    @Test
    public void tcE2E05_apiCreation_noteShouldAppearInstantlyOnUi() throws InterruptedException {

        String title       = "E2E-05 Note " + System.currentTimeMillis();
        String description = "Created for instant-visibility sync check";
        String category    = "Home";

        // ---- Step 1: API Login ----
        Response loginResponse = AuthClient.login(EMAIL, PASSWORD);
        Assert.assertEquals(loginResponse.getStatusCode(), 200,
                "TC-E2E-05 Setup Failure: API login was rejected.");

        // ---- Step 2: Open browser, login via UI, let dashboard fully load ----
        setUp();
        LoginPage loginPage = new LoginPage();

        loginPage.login(EMAIL, PASSWORD);
        Assert.assertTrue(driver.getCurrentUrl().contains("/notes/app"),
                "TC-E2E-05 Setup Failure: UI login did not reach the dashboard.");

        Thread.sleep(3000);

        // ---- Step 3: Create note via API while browser is sitting idle on dashboard ----
        Response postResp = NotesClient.createNote(title, description, category);
        Assert.assertEquals(postResp.getStatusCode(), 200,
                "TC-E2E-05 Setup Failure: API note creation failed.");
        String noteId = postResp.jsonPath().getString("data.id");

        // ---- Step 4: Wait — give the UI time to auto-sync (it won't, no live sync) ----
        Thread.sleep(3000);

        // ---- Step 5: Check DOM directly — no searchNote(), no refresh, no scroll ----
        // If the app had live sync the card would appear automatically.
        // It doesn't, so the card is absent → isNoteInstantlyVisible = false
        // This assert is DESIGNED TO FAIL to prove BUG-005.
        By noteTitleLocator = By.xpath(
                "//div[contains(@class,'card')]//h5[contains(text(),'" + title + "')]"
        );
        boolean isNoteInstantlyVisible = !DriverFactory.getDriver()
                .findElements(noteTitleLocator).isEmpty();

        Assert.assertTrue(isNoteInstantlyVisible,
                "BUG-005: Note created via API POST does not appear instantly on the UI " +
                        "dashboard grid without a page refresh (Dynamic Sync Failure).");

        // ---- Cleanup ----
        NotesClient.deleteNote(noteId);
        tearDown();
    }
}