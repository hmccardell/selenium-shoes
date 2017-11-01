package test.amazon;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Select;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import testbase.SeleniumTestBase;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * Created by hmccardell on 11/1/2017.
 */
public class AmazonSearchPage extends SeleniumTestBase {

    private Logger logger = LoggerFactory.getLogger(AmazonSearchTest.class);

    public AmazonSearchPage() {
    }

    public AmazonSearchPage(WebDriver driver) {
        super(driver);
    }

    public static final String AMAZON_URL = "http://www.amazon.com";
    public static final String SEARCH_BOX_ID = "twotabsearchtextbox";
    public static final String NIKE_SHOES = "Nike Shoes";
    public static final String SHOE_ELEMENT_CLASS = "div.s-item-container a.a-link-normal span.sx-price";
    public static final String SHOE_PRICE_WHOLE = "span.sx-price-whole";
    public static final String SHOE_PRICE_FRACTIONAL = "sup.sx-price-fractional";
    public static final String RESOLVED_SHOE_PRICE_ID = "priceblock_ourprice";
    public static final String SIZE_DROPDOWN_ELEMENT_ID = "native_dropdown_selected_size_name";

    public static final By BY_RESOLVED_SHOE_PRICE_ID = By.id(RESOLVED_SHOE_PRICE_ID);
    public static final By BY_SEARCH_BOX_CLASS = By.id(SEARCH_BOX_ID);
    public static final By BY_SHOE_ELEMENTS = By.cssSelector(SHOE_ELEMENT_CLASS);
    public static final By BY_SHOE_PRICE_WHOLE = By.cssSelector(SHOE_PRICE_WHOLE);
    public static final By BY_SHOE_PRICE_FRACTIONAL = By.cssSelector(SHOE_PRICE_FRACTIONAL);

    public static final String NO_SUCH_ELEMENT_SEARCHBOX_EXECEPTION_MSG = "Attempted to find the searchbox element but element returned null";
    public static final String NO_SUCH_ELEMENT_SHOES_EXECEPTION_MSG = "Attempted to select shoe elements but returned null or empty list";
    public static final String NO_SUCH_ELEMENT_SIZE_DROPDOWN_EXECEPTION_MSG = "Attempted to select shoe size dropdown but returned null";
    public static final String NO_SUCH_ELEMENT_SIZE_DROPDOWN_OPTIONS_EXECEPTION_MSG = "Attempted to select shoe size dropdown options but returned null or empty";

    public static final String SHOE_SIZE_TARGET = "10";

    public void navigateToPage() {
        logger.info("Navigating to amazon.com");
        driver.get(AMAZON_URL);
    }

    public void searchAmazonForText(String textToSearch) {
        logger.info("Waiting for visibility of search box class");
        waitForVisbilityOf(BY_SEARCH_BOX_CLASS);
        logger.info("Sending search text to search box");
        inputByLocator(BY_SEARCH_BOX_CLASS, textToSearch);
        WebElement element = driver.findElement(BY_SEARCH_BOX_CLASS);
        if (element == null) {
            logger.error(NO_SUCH_ELEMENT_SEARCHBOX_EXECEPTION_MSG);
            throw new NoSuchElementException(NO_SUCH_ELEMENT_SEARCHBOX_EXECEPTION_MSG);
        }
        logger.info("Sending enter keys to search box");
        element.sendKeys(Keys.ENTER);
    }

    public String findHighestPricedShoeOnPageOneAndClick() {
        waitForVisbilityOf(BY_SHOE_ELEMENTS);
        logger.info("Shoe elements were visible");
        logger.info("Selecting all shoe elements");
        List<WebElement> shoes = driver.findElements(BY_SHOE_ELEMENTS);

        if (shoes == null || shoes.isEmpty()) {
            logger.error(NO_SUCH_ELEMENT_SHOES_EXECEPTION_MSG);
            throw new NoSuchElementException(NO_SUCH_ELEMENT_SHOES_EXECEPTION_MSG);
        }

        WebElement highestPricedShoe = null;
        double highestPrice = 0.01;

        logger.info("Shoes were found, finding the highest priced on first page");
        for (WebElement shoeLink : shoes) {
            StringBuilder builder = new StringBuilder();
            builder.append(shoeLink.findElement(BY_SHOE_PRICE_WHOLE).getText());
            builder.append(".");
            builder.append(shoeLink.findElement(BY_SHOE_PRICE_FRACTIONAL).getText());
            String price = builder.toString().trim();
            double actualPrice = Double.parseDouble(price);

            //If prices are equal on more than one shoe element, we take the first one
            if (actualPrice > highestPrice) {
                highestPrice = actualPrice;
                highestPricedShoe = shoeLink;
            }
        }

        logger.info("Highest price found on page one results: " + highestPrice);

        if (highestPricedShoe != null) {
            //The element we want may be off-screen (not clickable), so let's scroll view.
            logger.info("Scrolling the view to highest priced shoe");
            scrollIntoViewWithJSExecutor(highestPricedShoe);
            logger.info("Clicking highest priced shoe element");
            highestPricedShoe.click();
        }

        return Double.toString(highestPrice);
    }

    public void findAndClickTargetSizeInDropdown(String target){

        logger.info("Selecting the size dropdown");
        WebElement selectBoxElement = driver.findElement(By.id(SIZE_DROPDOWN_ELEMENT_ID));
        if (selectBoxElement == null) {
            logger.error(NO_SUCH_ELEMENT_SIZE_DROPDOWN_EXECEPTION_MSG);
            throw new NoSuchElementException(NO_SUCH_ELEMENT_SIZE_DROPDOWN_EXECEPTION_MSG);
        }
        Select dropdown = new Select(selectBoxElement);

        logger.info("Checking the dropdown options");
        List<WebElement> dropDownOptions = dropdown.getOptions();
        if (dropDownOptions == null || dropDownOptions.isEmpty()) {
            logger.error(NO_SUCH_ELEMENT_SIZE_DROPDOWN_OPTIONS_EXECEPTION_MSG);
            throw new NoSuchElementException(NO_SUCH_ELEMENT_SIZE_DROPDOWN_OPTIONS_EXECEPTION_MSG);
        }

        for (WebElement option : dropDownOptions) {
            String optionValue = option.getText();
            if (optionValue != null) {
                optionValue = optionValue.trim().substring(0, 2);
                if (optionValue.equals(target)) {
                    logger.info("Found shoe size target " + optionValue);
                    option.click();
                    break;
                }
            }
        }
    }

    public String checkPriceOfShoeFromProductPage(){
        waitForVisbilityOf(By.id("productTitle"));
        WebElement productPrice = driver.findElement(BY_RESOLVED_SHOE_PRICE_ID);

        if (productPrice == null || productPrice.getText().isEmpty()) {
            throw new ElementNotVisibleException("attempted to find productPrice but was not visible");
        }

        String priceText = productPrice.getText();
        logger.info("Price range text: " + priceText);
        return priceText;
    }

}
