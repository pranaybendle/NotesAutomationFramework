package pages;

import drivers.DriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.WaitUtils;

import java.time.Duration;

public class NotesPage {

    private final WebDriver driver;

    public NotesPage() {
        this.driver = DriverFactory.getDriver();
    }

    private final By openAddNoteButton =
            By.xpath("//button[contains(text(),'Add Note')]");

    private final By titleInput        = By.id("title");
    private final By descriptionInput  = By.id("description");
    private final By categoryDropdown  = By.id("category");

    private final By createButton =
            By.xpath("//button[contains(text(),'Create')]");

    private final By updateButton =
            By.cssSelector("button[data-testid='note-submit']");

    private final By confirmDeleteButton =
            By.xpath("//div[contains(@class,'modal')]//button[normalize-space()='Delete']");

    private final By editTitleField =
            By.xpath("//div[contains(@class,'modal')]//input[@id='title']");

    private final By editDescriptionField =
            By.cssSelector("textarea[data-testid='note-description']");

    private final By logoutButton =
            By.xpath("//div[contains(@class,'navbar')]//button[normalize-space()='Logout']");

    private final By searchInput  = By.id("search-input");
    private final By searchButton = By.cssSelector("button[data-testid='search-btn']");


    private By noteTitle(String title) {
        return By.xpath("//*[contains(text(),'" + title + "')]");
    }

    private By deleteButton(String title) {
        return By.xpath(
                "//div[contains(@class,'card')][.//*[contains(text(),'" + title + "')]]" +
                        "//button[@data-testid='note-delete']"
        );
    }

    private By categoryButton(String category) {
        return By.xpath(
                "//button[@data-testid='category-" + category.toLowerCase() + "']"
        );
    }

    private By editButton(String title) {
        return By.xpath(
                "//div[contains(@class,'card')][.//*[contains(text(),'" + title + "')]]" +
                        "//button[@data-testid='note-edit']"
        );
    }


    public void clickAddNote() {
        var addBtn = WaitUtils.waitForVisible(openAddNoteButton);
        jsScrollAndClick(addBtn);
        jsClick(By.xpath("//button[contains(text(),'Add Note')]"));
    }

    public void enterTitle(String title) {
        WaitUtils.waitForVisible(titleInput).sendKeys(title);
    }

    public void enterDescription(String description) {
        WaitUtils.waitForVisible(descriptionInput).sendKeys(description);
    }

    public void selectCategory(String category) {
        WaitUtils.waitForClickable(categoryDropdown).sendKeys(category);
    }

    public void clickSave() {
        jsScrollAndClick(WaitUtils.waitForVisible(createButton));
    }

    public void createNote(String title, String description, String category) {
        clickAddNote();
        enterTitle(title);
        enterDescription(description);
        selectCategory(category);
        clickSave();
    }


    public boolean isNoteVisible(String title) {
        return WaitUtils.waitForVisible(noteTitle(title)).isDisplayed();
    }


    public boolean isNoteNotVisible(String title) {
        try { Thread.sleep(2000); } catch (InterruptedException ignored) {}

        By noteTitleLocator = By.xpath(
                "//div[contains(@class,'card')]//h5[contains(text(),'" + title + "')]"
        );
        return driver.findElements(noteTitleLocator).isEmpty();
    }


    public void searchNote(String title) {
        var input = WaitUtils.waitForVisible(searchInput);
        input.clear();
        input.sendKeys(title);

        var btn = WaitUtils.waitForClickable(searchButton);
        jsScrollAndClick(btn);


        try { Thread.sleep(2000); } catch (InterruptedException ignored) {}
    }

    public void deleteNote(String title) {
        var deleteBtn = driver.findElement(deleteButton(title));
        jsScrollAndClick(deleteBtn);

        var confirmBtn = WaitUtils.waitForClickable(confirmDeleteButton);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", confirmBtn);
    }


    public void editNote(String oldTitle,
                         String newTitle,
                         String newDesc) {

        By editLocator = editButton(oldTitle);

        WebElement editBtn =
                WaitUtils.waitForClickable(editLocator);

        jsScrollAndClick(editBtn);

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        WebElement titleField =
                WaitUtils.waitForVisible(editTitleField);

        titleField.clear();
        titleField.sendKeys(newTitle);

        WebElement descField =
                WaitUtils.waitForVisible(editDescriptionField);

        descField.clear();
        descField.sendKeys(newDesc);

        WebElement updateBtn =
                WaitUtils.waitForClickable(updateButton);

        jsScrollAndClick(updateBtn);

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        driver.navigate().refresh();
    }



    public void clickCategory(String category) {
        var btn = WaitUtils.waitForVisible(categoryButton(category));
        jsScrollAndClick(btn);
        try { Thread.sleep(2000); } catch (InterruptedException ignored) {}
    }


    public void clickLogout() {
        var logoutBtn = WaitUtils.waitForVisible(logoutButton);
        jsScrollAndClick(logoutBtn);
    }


    private void jsScrollAndClick(org.openqa.selenium.WebElement el) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView({block:'center'});", el);
        js.executeScript("arguments[0].click();", el);
    }

    private void jsClick(By locator) {
        var els = driver.findElements(locator);
        if (!els.isEmpty()) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", els.get(0));
        }
    }
}