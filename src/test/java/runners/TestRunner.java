package runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import listeners.TestListener;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;

@Listeners(TestListener.class)
@CucumberOptions(
        features    = "src/test/resources/features/ui",
        glue        = {"stepdefinitions", "hooks"},
        plugin      = {
                "pretty",
                "html:target/cucumber-report.html",
                "json:target/cucumber-report.json",
                "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"
        },
        monochrome  = true,
        dryRun      = false,
        publish     = false
)
public class TestRunner extends AbstractTestNGCucumberTests {

        @Override
        @DataProvider(parallel = false)
        public Object[][] scenarios() {
                return super.scenarios();
        }
}