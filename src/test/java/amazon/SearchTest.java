package amazon;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

import static org.junit.Assert.assertEquals;

/**
 * Selenium tests using the SearchPage object model.
 *
 * @author hmccardell
 */
public class SearchTest extends SearchPage {

    private WebDriver driver;
    private SearchPage searchPage;

    @Before
    public void setup() {
        driver = setNewChromeDriver();
        searchPage = new SearchPage(driver);
    }

    @After
    public void tearDown() throws Exception {
        driver.close();
        driver.quit();
    }

    @Test
    public void findHighestPricedNikeShoeAtSize10OnPageOne() throws Exception {

        searchPage.navigateToPage();
        searchPage.searchAmazonForText(NIKE_SHOES);
        String highestPriceOnPageOne = searchPage.findHighestPricedShoeOnPageOneAndClick();
        searchPage.findAndClickTargetSizeInDropdown(SHOE_SIZE_TARGET);
        String priceFromProductPage = searchPage.checkPriceOfShoeFromProductPage();

        double expected = Double.parseDouble(highestPriceOnPageOne);
        double actual = Double.parseDouble(priceFromProductPage);

        assertEquals(Double.doubleToLongBits(expected), Double.doubleToLongBits(actual));
    }
}
