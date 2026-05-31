package utils;

import drivers.DriverFactory;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;

public class ScreenshotUtils {

    public static String captureScreenshot(String testName) {

        if (DriverFactory.getDriver() == null) {
            return "Driver not initialized";
        }

        File src =
                ((TakesScreenshot) DriverFactory.getDriver())
                        .getScreenshotAs(OutputType.FILE);

        String path =
                "reports/screenshots/"
                      lc  + testName + "_"
                        + LocalDateTime.now()
                        .toString()
                        .replace(":", "-")
                        + ".png";

        try {
            FileUtils.copyFile(src, new File(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return path;
    }
}