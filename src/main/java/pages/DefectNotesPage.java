package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import utils.WaitUtils;

public class DefectNotesPage extends NotesPage {

    public boolean isNoteVisibleWithoutRefresh(String noteTitle) {

        try {

            WebElement note =
                    WaitUtils.waitForVisible(
                            By.xpath(
                                    "//*[contains(text(),'"
                                            + noteTitle +
                                            "')]"
                            )
                    );

            return note.isDisplayed();

        } catch (Exception e) {

            return false;
        }
    }
}