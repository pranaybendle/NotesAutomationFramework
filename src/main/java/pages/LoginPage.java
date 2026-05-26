package pages;

import drivers.DriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utils.WaitUtils;

public class LoginPage {

    private final WebDriver driver;

    public LoginPage() {
        this.driver = DriverFactory.getDriver();
    }

    // Landing page
    private final By loginLandingButton =
            By.xpath("//a[normalize-space()='Login']");

    // Login form
    private final By emailInput =
            By.name("email");

    private final By passwordInput =
            By.name("password");

    private final By signInButton =
            By.xpath("//button[@type='submit']");

    // Dashboard
    private final By addNoteButton =
            By.xpath("//button[contains(text(),'Add Note')]");

    private final By loginError =
            By.xpath("//div[contains(text(),'Incorrect email address or password')]");


    public void openLoginForm() {
        var loginBtn = WaitUtils.waitForClickable(loginLandingButton);

        // scroll to button
        ((org.openqa.selenium.JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView(true);", loginBtn);

        // JS click (bypasses overlay/intercept)
        ((org.openqa.selenium.JavascriptExecutor) driver)
                .executeScript("arguments[0].click();", loginBtn);
    }

    public void enterEmail(String email) {
        WaitUtils.waitForVisible(emailInput).clear();
        WaitUtils.waitForVisible(emailInput).sendKeys(email);
    }

    public void enterPassword(String password) {
        WaitUtils.waitForVisible(passwordInput).clear();
        WaitUtils.waitForVisible(passwordInput).sendKeys(password);
    }

    public void clickSignIn() {
        var signInBtn = WaitUtils.waitForClickable(signInButton);

        // scroll into view
        ((org.openqa.selenium.JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView({block:'center'});", signInBtn);

        // safer JS click
        ((org.openqa.selenium.JavascriptExecutor) driver)
                .executeScript("arguments[0].click();", signInBtn);
    }

    public void login(String email, String password) {
        openLoginForm();   // IMPORTANT FIX
        enterEmail(email);
        enterPassword(password);
        clickSignIn();
    }

    public boolean isLoginErrorVisible() {
        return WaitUtils.waitForVisible(loginError).isDisplayed();
    }

    public boolean isLoginSuccessful() {
        return WaitUtils.waitForVisible(addNoteButton).isDisplayed();
    }
}