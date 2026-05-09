package githubactions;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import io.qameta.allure.Step;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.ByteArrayInputStream;

@Epic("Demo UI regression")
@Feature("Browser navigation")
public class Demo {
    protected WebDriver driver;

    @Step("Start Chrome browser")
    private void startBrowser() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--window-size=1920,1080");
        driver = new ChromeDriver(options);
    }

    @BeforeMethod
    public void setUp() {
        startBrowser();
        Allure.step("Browser session started and ready for navigation");
    }

    @Step("Open page {url}")
    private void openPage(String url) {
        driver.get(url);
        Allure.step("Opened URL: " + url);
    }

    @Test(description = "Open GitHub and verify page title is present")
    @Severity(SeverityLevel.CRITICAL)
    @Story("Open GitHub home page")
    @Description("Navigate to GitHub and validate that the page title is not empty.")
    public void testcase1() {
        openPage("https://github.com");
        String title = driver.getTitle();
        Allure.step("GitHub title: " + title);
        Assert.assertTrue(title != null && !title.isBlank(), "Expected non-empty GitHub title");
    }

    @Test(description = "Open Google and verify page title is present")
    @Severity(SeverityLevel.NORMAL)
    @Story("Open Google home page")
    @Description("Navigate to Google and validate that the page title is not empty.")
    public void testcase2() {
        openPage("https://google.com");
        String title = driver.getTitle();
        Allure.step("Google title: " + title);
        Assert.assertTrue(title != null && !title.isBlank(), "Expected non-empty Google title");
    }

    @Test(description = "Open Facebook and verify page title is present")
    @Severity(SeverityLevel.MINOR)
    @Story("Open Facebook home page")
    @Description("Navigate to Facebook and validate that the page title is not empty.")
    public void testcase3() {
        openPage("https://facebook.com");
        String title = driver.getTitle();
        Allure.step("Facebook title: " + title);
        Assert.assertTrue(title != null && !title.isBlank(), "Expected non-empty Facebook title");
    }

    @AfterMethod
    public void tearDown(ITestResult result) {
        if (!result.isSuccess() && driver != null) {
            byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            Allure.addAttachment("Failure screenshot", new ByteArrayInputStream(screenshot), "image/png", "png");
        }

        if (driver != null) {
            driver.quit();
        }
    }
}
