package testbase;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * A class to organize a test base for functionality that would be useful across an entire test suite of pages and tests.
 *
 * @author hmccardell
 */
public class SeleniumTestBase {

    private static final long TIMEOUT_SECONDS = 15;
    private static final String RELATIVE_CHROMEDRIVER_WINDOWS = "src/test/resources/chromedriver-2.29.exe";
    private static final String RELATIVE_CHROMEDRIVER_MAC = "src/test/resources/chromedriver";
    private static final String CHROMEDRIVER_PROPERTY_NAME = "webdriver.chrome.driver";
    private static final String OPERATING_SYSTEM = System.getProperty("os.name").toLowerCase();

    protected WebDriver driver;

    public SeleniumTestBase(){}

    public SeleniumTestBase(WebDriver driver) {
        this.driver = driver;
    }

    public WebDriver setNewChromeDriver() {

        if(isMac()){
            System.setProperty(CHROMEDRIVER_PROPERTY_NAME, RELATIVE_CHROMEDRIVER_MAC);
        } else {
            System.setProperty(CHROMEDRIVER_PROPERTY_NAME, RELATIVE_CHROMEDRIVER_WINDOWS);
        }

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

    public static boolean isMac() {
        return (OPERATING_SYSTEM.indexOf("mac") >= 0);
    }

}
