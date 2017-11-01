package testbase;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SeleniumTestBase {

    private static final long TIMEOUT_SECONDS = 15;
    private static final String RELATIVE_CHROMEDRIVER_LOCATION = "bin/chromedriver-2.29.exe";
    private static final String CHROMEDRIVER_PROPERTY_NAME = "webdriver.chrome.driver";

    protected WebDriver driver;

    public SeleniumTestBase(){}

    public SeleniumTestBase(WebDriver driver) {
        this.driver = driver;
    }

    public WebDriver setNewChromeDriver() {
        System.setProperty(CHROMEDRIVER_PROPERTY_NAME, RELATIVE_CHROMEDRIVER_LOCATION);
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        return driver;
    }

    public void inputByLocator(By locator, String input) {
        new WebDriverWait(driver, TIMEOUT_SECONDS).until(
                ExpectedConditions.visibilityOfElementLocated(locator))
                .sendKeys(input);
    }

    public void waitForVisbilityOf(By lookFor) {
        new WebDriverWait(driver, TIMEOUT_SECONDS).until(ExpectedConditions
                .visibilityOfElementLocated((lookFor)));
    }

    public void scrollIntoViewWithJSExecutor(WebElement elementToScrollTo) {
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("arguments[0].scrollIntoView()", elementToScrollTo);
    }
}
