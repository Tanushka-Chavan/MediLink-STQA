package com.medilink.tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

/**
 * BaseTest initializes WebDriver, handles timeouts, and captures failure screenshots.
 */
public class BaseTest {
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected String basePath;

    @BeforeClass
    @Parameters({"headless"})
    public void setUp(@Optional("false") String headless) {
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-popup-blocking");
        options.addArguments("--remote-allow-origins=*");
        if ("true".equalsIgnoreCase(headless)) {
            options.addArguments("--headless=new");
            options.addArguments("--window-size=1920,1080");
        }

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(8));
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Resolve local project base path
        File projectDir = new File(System.getProperty("user.dir"));
        File mediLinkDir = new File(projectDir.getParentFile(), "");
        if (projectDir.getName().equals("stqa")) {
            basePath = projectDir.getParentFile().toURI().toString();
        } else {
            basePath = new File(projectDir, "MediLink").toURI().toString();
        }
    }

    public void navigateTo(String page) {
        driver.get(basePath + page);
    }

    public WebElement waitForElement(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public WebElement waitForClickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    @AfterMethod
    public void captureScreenshotOnFailure(ITestResult result) {
        if (ITestResult.FAILURE == result.getStatus()) {
            TakesScreenshot ts = (TakesScreenshot) driver;
            File source = ts.getScreenshotAs(OutputType.FILE);
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String fileName = "screenshots/" + result.getName() + "_" + timestamp + ".png";
            try {
                FileUtils.copyFile(source, new File(fileName));
                System.out.println("[STQA] Screenshot captured for failed test: " + fileName);
            } catch (IOException e) {
                System.err.println("Failed to save screenshot: " + e.getMessage());
            }
        }
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
