package test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hmccardell on 11/1/2017.
 */
public class AmazonTest {

    private static WebDriver driver;
    private static final String SEARCH_BOX_ID = "twotabsearchtextbox";
    private static final String SEARCH_KEYS = "Nike Shoes";
    private static final String SHOE_ELEMENT_CLASS = "div.s-item-container a.a-link-normal span.sx-price";
    private static final String SHOE_PRICE_WHOLE = "span.sx-price-whole";
    private static final String SHOE_PRICE_FRACTIONAL = "sup.sx-price-fractional";
    private static final String RESOLVED_SHOE_PRICE_ID = "priceblock_ourprice";


    protected static final By BY_RESOLVED_SHOE_PRICE_ID = By.id(RESOLVED_SHOE_PRICE_ID);
    protected static final By BY_SEARCH_BOX_CLASS = By.id(SEARCH_BOX_ID);
    protected static final By BY_SHOE = By.cssSelector(SHOE_ELEMENT_CLASS);
    protected static final By BY_SHOE_PRICE_WHOLE = By.cssSelector(SHOE_PRICE_WHOLE);
    protected static final By BY_SHOE_PRICE_FRACTIONAL = By.cssSelector(SHOE_PRICE_FRACTIONAL);

    public static void inputByLocator(By locator, String input) {
        new WebDriverWait(driver, 15).until(
                ExpectedConditions.visibilityOfElementLocated(locator))
                .sendKeys(input);
    }

    public WebDriver setNewChromeDriver() {

        System.setProperty("webdriver.chrome.driver",
                "bin/chromedriver-2.29.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        return driver;
    }

    public String waitForVisbilityOf(By lookFor) {
        new WebDriverWait(driver, 30).until(ExpectedConditions
                .visibilityOfElementLocated((lookFor)));
        return null;
    }

    @Before
    public void setup() {
        driver = setNewChromeDriver();
    }

    @After
    public void tearDown() throws Exception {
        driver.close();
        driver.quit();
    }

    @Test
    public void shouldNavigateToAmazon() {
        driver.get("http://www.amazon.com");

        waitForVisbilityOf(BY_SEARCH_BOX_CLASS);
        inputByLocator(BY_SEARCH_BOX_CLASS, SEARCH_KEYS);
        WebElement element = driver.findElement(BY_SEARCH_BOX_CLASS);
        element.sendKeys(Keys.ENTER);

        waitForVisbilityOf(BY_SHOE);

        List<WebElement> shoes = driver.findElements(BY_SHOE);

        WebElement highestPricedShoe = null;
        double highestPrice = 0.01;

        for (WebElement shoeLink : shoes) {
            String whole = shoeLink.findElement(BY_SHOE_PRICE_WHOLE).getText();
            String fraction = shoeLink.findElement(BY_SHOE_PRICE_FRACTIONAL).getText();
            String price = whole + "." + fraction;
            double actualPrice = Double.parseDouble(price);

            if (actualPrice > highestPrice) {
                highestPrice = actualPrice;
                highestPricedShoe = shoeLink;
            }
        }

        System.out.println("Highest price: " + highestPrice);

        if(highestPricedShoe != null){
            JavascriptExecutor jse = (JavascriptExecutor)driver;
            jse.executeScript("arguments[0].scrollIntoView()", highestPricedShoe);
            highestPricedShoe.click();
        }




        waitForVisbilityOf(By.id("productTitle"));




//        WebElement selectBoxElement = driver.findElement(By.id("native_dropdown_selected_size_name"));
//        Select dropdown = new Select(selectBoxElement);
//
//        dropdown.selectByVisibleText("10 D(M) US");


    }
}
