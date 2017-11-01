package test.amazon;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;

import static org.junit.Assert.assertEquals;

/**
 * Created by hmccardell on 11/1/2017.
 */
public class AmazonSearchTest extends AmazonSearchPage {

    private WebDriver driver;
    private AmazonSearchPage amazonSearchPage;

    @Before
    public void setup() {
        driver = setNewChromeDriver();
        amazonSearchPage = new AmazonSearchPage(driver);
    }

    @After
    public void tearDown() throws Exception {
        driver.close();
        driver.quit();
    }

    @Test
    public void findHighestPricedNikeShoeAtSize10OnPageOne() throws InterruptedException {

        amazonSearchPage.navigateToPage();
        amazonSearchPage.searchAmazonForText(NIKE_SHOES);
        String highestPriceOnPageOne = amazonSearchPage.findHighestPricedShoeOnPageOneAndClick();
        amazonSearchPage.findAndClickTargetSizeInDropdown(SHOE_SIZE_TARGET);
        String priceFromProductPage = amazonSearchPage.checkPriceOfShoeFromProductPage();

        assertEquals(highestPriceOnPageOne, priceFromProductPage);
    }
}
