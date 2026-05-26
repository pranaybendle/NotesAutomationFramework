package stepdefinitions;

import api.clients.AuthClient;
import api.clients.NotesClient;
import base.BaseTest;
import io.cucumber.java.en.*;
import io.restassured.response.Response;
import org.testng.Assert;
import pages.LoginPage;
import pages.NotesPage;

import java.util.List;

public class NoteSteps extends BaseTest {

    private static final String NOTE_TITLE    = "Capgemini Note";
    private static final String NOTE_DESC     = "BDD Automation Test";
    private static final String NOTE_CATEGORY = "Home";

    private static final String UPDATED_NOTE_TITLE = "Updated Capgemini Note V2";
    private static final String UPDATED_NOTE_DESC  = "Updated Automation Validation V2";

    LoginPage loginPage;
    NotesPage notesPage;

    // ---- Given ----

    @Given("user is on Notes application")
    public void user_is_on_notes_application() {
        setUp();                        // fresh browser every scenario
        loginPage = new LoginPage();
        notesPage = new NotesPage();
    }

    @Given("user is logged in")
    public void user_is_logged_in() {
        user_is_on_notes_application();
        user_logs_in_with_valid_credentials();
        Assert.assertTrue(
                driver.getCurrentUrl().contains("/notes/app"),
                "Should be on dashboard after login"
        );
    }

    // ---- When ----

    @When("user logs in with valid credentials")
    public void user_logs_in_with_valid_credentials() {
        loginPage.login("pranaybendle18@gmail.com", "pass@123");
    }

    @When("user logs in with invalid credentials")
    public void user_logs_in_with_invalid_credentials() {
        loginPage.login("wrong@gmail.com", "wrong123");
    }

    @When("user creates a note")
    public void user_creates_a_note() {
        notesPage.createNote(NOTE_TITLE, NOTE_DESC, NOTE_CATEGORY);
    }

    @When("user deletes a note")
    public void user_deletes_a_note() {
        notesPage.deleteNote(NOTE_TITLE);
    }

    @When("user deletes all notes")
    public void user_deletes_all_notes() {
        AuthClient.login("pranaybendle18@gmail.com", "pass@123");
        Response response = NotesClient.getAllNotes();
        List<String> noteIds = response.jsonPath().getList("data.id");
        for (String noteId : noteIds) {
            NotesClient.deleteNote(noteId);
            System.out.println("Deleted: " + noteId);
        }
        driver.navigate().refresh();
        System.out.println("All Notes Deleted");
    }

    @When("user edits a note")
    public void user_edits_a_note() {
        notesPage.editNote(NOTE_TITLE, UPDATED_NOTE_TITLE, UPDATED_NOTE_DESC);
    }

    @When("user logs out")
    public void user_logs_out() {
        notesPage.clickLogout();
    }

    @When("user searches for a note")
    public void user_searches_for_a_note() {
        notesPage.searchNote(NOTE_TITLE);
    }

    @When("user clicks category {string}")
    public void user_clicks_category(String category) {
        notesPage.clickCategory(category);
    }

    // ---- Then ----

    @Then("note should be visible")
    public void note_should_be_visible() {
        Assert.assertTrue(notesPage.isNoteVisible(NOTE_TITLE),
                "Note '" + NOTE_TITLE + "' should be visible after creation");
    }

    @Then("note should not be visible")
    public void note_should_not_be_visible() {
        Assert.assertTrue(notesPage.isNoteNotVisible(NOTE_TITLE),
                "Note '" + NOTE_TITLE + "' should NOT be visible after deletion");
    }

    @Then("updated note should be visible")
    public void updated_note_should_be_visible() {
        Assert.assertTrue(notesPage.isNoteVisible(UPDATED_NOTE_TITLE),
                "Updated note '" + UPDATED_NOTE_TITLE + "' should be visible");
    }

    @Then("user should be redirected to login page")
    public void user_should_be_redirected_to_login_page() {
        try { Thread.sleep(2000); } catch (InterruptedException ignored) {}
        Assert.assertTrue(
                driver.getCurrentUrl().contains("/notes/app"),
                "URL should still contain /notes/app after logout"
        );
        Assert.assertTrue(
                driver.getPageSource().contains("Login"),
                "Login button should be visible after logout"
        );
    }

    @Then("error message should be visible")
    public void error_message_should_be_visible() {
        Assert.assertTrue(loginPage.isLoginErrorVisible(),
                "Login error message should be displayed for invalid credentials");
    }

    @Then("searched note should be visible")
    public void searched_note_should_be_visible() {
        Assert.assertTrue(notesPage.isNoteVisible(NOTE_TITLE),
                "Note '" + NOTE_TITLE + "' should appear in search results");
    }

    @Then("note {string} should be visible")
    public void note_should_be_visible(String title) {
        Assert.assertTrue(notesPage.isNoteVisible(title),
                "Note '" + title + "' should be visible after category filter");
    }
}