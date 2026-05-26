package hooks;

import base.BaseTest;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import utils.ScreenshotUtils;

public class Hooks extends BaseTest {

    @After
    public void afterScenario(Scenario scenario) {
        try {
            if (scenario.isFailed()) {
                ScreenshotUtils.captureScreenshot(scenario.getName());
            }
        } finally {
            // Always quit driver after every scenario — no leaks
            tearDown();
        }
    }
}