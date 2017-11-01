package testbase;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class SeleniumTestBase {

    public WebDriver setNewChromeDriver() {
        System.setProperty("webdriver.chrome.driver",
                "bin/chromedriver-2.29.exe");
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        return driver;
    }

}
