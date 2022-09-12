package QKART_TestNG.tests;

import QKART_TestNG.pages.Login;
import QKART_TestNG.pages.Register;
import static org.testng.Assert.assertTrue;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;


public class testCase_01 {

    static RemoteWebDriver driver;
    static String LastGeneratedName__;

    @BeforeSuite(alwaysRun = true)
    public void createDriver() throws MalformedURLException {
        // Code to Launch Browser using Zalenium in Crio workspace
        final DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setBrowserName(BrowserType.CHROME);
        driver = new RemoteWebDriver(new URL("http://localhost:8082/wd/hub"), capabilities);
    }


    public void takeScreenshot(String screenshotType, String Description) {
        try {
            File theDir = new File("/screenshots");
            if (!theDir.exists()) {
                theDir.mkdirs();
            }
            String timestamp = String.valueOf(java.time.LocalDateTime.now());
            String fileName = String.format("screenshot_%s_%s_%s.png", timestamp, screenshotType,
                    Description);
            TakesScreenshot scrShot = ((TakesScreenshot) driver);
            File SrcFile = scrShot.getScreenshotAs(OutputType.FILE);
            File DestFile = new File("screenshots/" + fileName);
            FileUtils.copyFile(SrcFile, DestFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void registerNewser() throws InterruptedException {
        SoftAssert sa = new SoftAssert();
        Register registration = new Register(driver);
        registration.navigateToRegisterPage();
        sa.assertTrue(registration.registerUser("testUser", "abc@123", true),
                "Failed to create a new user ");
        LastGeneratedName__ = registration.lastGeneratedUsername;
    }

    @Test(description = "Verify if new user can be created and logged in ")
    public void TestCase01() throws InterruptedException {
        registerNewser();
        String lastGeneratedUserName = LastGeneratedName__;
        Login login = new Login(driver);
        login.navigateToLoginPage();
        var status = login.PerformLogin(lastGeneratedUserName, "abc@123");
        assertTrue(status);
    }

    @AfterSuite
    public void quitDriver() {
        driver.quit();
    }


}
