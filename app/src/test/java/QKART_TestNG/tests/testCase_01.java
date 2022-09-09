package QKART_TestNG.tests;

import QKART_TestNG.pages.Login;
import QKART_TestNG.pages.Register;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;
import static org.testng.Assert.*;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.chrome.ChromeDriver;
import java.io.File;


public class testCase_01 {

    static ChromeDriver driver;
    static String LastGeneratedName__;

    @BeforeSuite(alwaysRun = true)
    public static void createDriver() {
        // IMPORTANT!: Enter the Driver Location here
        String driverLocation = "/Users/praveenkumar/Downloads/chromedriver 6";// IMPORTANT!:Change driver locat
        System.setProperty("webdriver.chrome.driver", driverLocation);
        System.setProperty("webdriver.chrome.logfile", "chromedriver.log");
        System.setProperty("webdriver.chrome.verboseLogging", "true");
        driver = new ChromeDriver();
    }


    public void takeScreenshot(String screenshotType, String Description) {
        try {
            File theDir = new File("/screenshots");
            if (!theDir.exists()) {
                theDir.mkdirs();
            }
            String timestamp = String.valueOf(java.time.LocalDateTime.now());
            String fileName = String.format("screenshot_%s_%s_%s.png", timestamp, screenshotType, Description);
            TakesScreenshot scrShot = ((TakesScreenshot) driver);
            File SrcFile = scrShot.getScreenshotAs(OutputType.FILE);
            File DestFile = new File("screenshots/" + fileName);
            FileUtils.copyFile(SrcFile, DestFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void registerNewser() throws InterruptedException
    {   SoftAssert sa = new SoftAssert();
        Register registration = new Register(driver);
        registration.navigateToRegisterPage();
        sa.assertTrue(registration.registerUser("testUser", "abc@123", true),"Failed to create a new user ");
        LastGeneratedName__ = registration.lastGeneratedUsername;
    }

    @Test(description = "Verify if new user can be created and logged in ")
    public static void TestCase01() throws InterruptedException {
        registerNewser();
        String  lastGeneratedUserName = LastGeneratedName__;
        Login login = new Login(driver);
        login.navigateToLoginPage();
        var status = login.PerformLogin(lastGeneratedUserName, "abc@123");
        assertTrue(status);
    }

    @AfterSuite
    public static void quitDriver()
    {
        driver.quit();
    }


}
