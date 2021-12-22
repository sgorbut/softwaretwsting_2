import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;

public class FirstTest {

    private AppiumDriver driver;

    @Before
    public void setUp() throws Exception{
        DesiredCapabilities  capabilities= new DesiredCapabilities();

        capabilities.setCapability("platformName","Android");
        capabilities.setCapability("deviceName","AndroidDevice");
        capabilities.setCapability("platformVersion","8.0");
        capabilities.setCapability("automationName","Appium");
        capabilities.setCapability("appPackage","org.wikipedia");
        capabilities.setCapability("appActivity","main.MainActivity");
        capabilities.setCapability("app","/Users/sgorbut/IdeaProjects/softwaretwsting_2/apks/org.wikipedia.apk");

        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
    }

    @After
    public void tearDown(){
        driver.quit();
    }

    @Test
    public void firstTest(){

        WebElement element_to_init_search = driver.findElementByXPath("//*[contains(@text, 'Search Wikipedia')]");
        element_to_init_search.click();

//        WebElement element_to_enter_search_line = driver.findElementByXPath("//*[contains(@text, 'Search…')]");
//        element_to_enter_search_line.sendKeys("Appium");

        WebElement element_to_enter_search_line = waitForElementPresentByXpath(
                "//*[contains(@text, 'Search…')]",
                "Cannot find search input"
        );
        element_to_enter_search_line.sendKeys("Java");
        waitForElementPresentByXpath(
                "//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']",
                "Can not find text 'Object-oriented programming language'",
                10
        );

    }

    @Test
    public void secondTest(){
        waitForElementByXpathAndClick("//*[contains(@text, 'Search Wikipedia')]",
                "Can not find Search Wikipedia input",
                5);
        waitForElementByXpathAndSendKeys("//*[contains(@text, 'Search…')]",
                "Java",
                "Can not find input",
                5
                );
        waitForElementPresentByXpath(
                "//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']",
                "Can not find text 'Object-oriented programming language'",
                10);
    };

    @Test
    public void testCancelSearch(){
        waitForElementByIdAndClick(
                "org.wikipedia:id/search_container",
                "Can not find 'Search Wikipedia' input",
                5
        );
        waitForElementByIdAndClick(
                "org.wikipedia:id/search_close_btn",
                "Can not find X to cancel search",
                5
        );
        waitForElementNotPresentById(
                "org.wikipedia:id/search_close_btn",
                "X still present on the page",
                5
        );
    }

    @Test
    public void anotherTest(){
        waitForElementAndClick(By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Can not find Search Wikipedia input",
                5);
        waitForElementAndSendKeys(By.xpath("//*[contains(@text, 'Search…')]"),
                "Java",
                "Can not find input",
                5);
        waitForElementPresent(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
                "Can not find text 'Object-oriented programming language'",
                10);
    }

    @Test
    public void anotherSecondTest(){
        waitForElementAndClick(By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Can not find Search Wikipedia input",
                5);
        waitForElementAndSendKeys(By.xpath("//*[contains(@text, 'Search…')]"),
                "Java",
                "Can not find input",
                5
        );
        waitForElementPresent(By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
                "Can not find text 'Object-oriented programming language'",
                10);
    };

    @Test
    public void anotherTestCancelSearch(){
        waitForElementAndClick(By.id(
                "org.wikipedia:id/search_container"),
                "Can not find 'Search Wikipedia' input",
                5
        );
        waitForElementAndClick(By.id(
                "org.wikipedia:id/search_close_btn"),
                "Can not find X to cancel search",
                5
        );
        waitForElementNotPresent(By.id(
                "org.wikipedia:id/search_close_btn"),
                "X still present on the page",
                5
        );
    }

    @Test
    public void testCompareArticleTitle() {
        waitForElementAndClick(By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Can not find Search Wikipedia input",
                5);
        waitForElementAndSendKeys(By.xpath("//*[contains(@text, 'Search…')]"),
                "Java",
                "Can not find input",
                5);
        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
                "Can not find text 'Object-oriented programming language'",
                5);
        waitForElementPresent(By.id("org.wikipedia:id/view_page_title_text"),
                "Can't find article title",
                15);
        WebElement title_element = waitForElementPresent(By.id("org.wikipedia:id/view_page_title_text"),
                "Can't find article title",
                15);
    //    String article_title = title_element.getAttribute("text");    // Не заработал атрибут текст
        String article_title = title_element.getText();

        Assert.assertEquals(
                "Unexpected title",
                "Java (programming language)",
                article_title);

    }

    @Test
    public void anotherTestCancelSearch2(){
        waitForElementAndClick(By.id(
                        "org.wikipedia:id/search_container"),
                "Can not find 'Search Wikipedia' input",
                5);
        waitForElementAndSendKeys(By.xpath("//*[contains(@text, 'Search…')]"),
                "Java",
                "Can not find input",
                5);
        waitForElementAndClear(By.id(
                        "org.wikipedia:id/search_src_text"),
                "Can not find search field",
                5);
        waitForElementAndClick(By.id(
                        "org.wikipedia:id/search_close_btn"),
                "Can not find X to cancel search",
                5);
        waitForElementNotPresent(By.id(
                        "org.wikipedia:id/search_close_btn"),
                "X still present on the page",
                5);
    }



    private WebElement waitForElementPresentByXpath (String xpath, String error_message, long timeoutInSeconds){
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message + "\n");
        By by = By.xpath(xpath);
        return wait.until(
                ExpectedConditions.presenceOfElementLocated(by)
        );
    }

    private WebElement waitForElementPresentByXpath (String xpath, String error_message){
        return waitForElementPresentByXpath(xpath, error_message, 5);
    }

    private WebElement waitForElementByXpathAndClick(String xpath, String error_message, long timeoutInSeconds){
        WebElement element = waitForElementPresentByXpath(xpath, error_message, timeoutInSeconds);
        element.click();
        return element;
    };

    private WebElement waitForElementByXpathAndSendKeys(String xpath, String value, String error_message, long timeoutInSeconds){
        WebElement element = waitForElementPresentByXpath(xpath, error_message, timeoutInSeconds);
        element.sendKeys(value);
        return element;
    };

    private WebElement waitForElementPresentById (String id, String error_message, long timeoutInSeconds){
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message + "\n");
        By by = By.id(id);
        return wait.until(
                ExpectedConditions.presenceOfElementLocated(by)
        );
    }

    private WebElement waitForElementByIdAndClick(String id, String error_message, long timeoutInSeconds){
        WebElement element = waitForElementPresentById(id, error_message, timeoutInSeconds);
        element.click();
        return element;
    };

    private boolean waitForElementNotPresentById(String id, String error_message, long timeoutInSeconds){
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message + "\n");
        By by = By.id(id);
        return wait.until(
                ExpectedConditions.invisibilityOfElementLocated(by));
    };

    private WebElement waitForElementPresent (By by, String error_message, long timeoutInSeconds){
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message + "\n");
        return wait.until(
                ExpectedConditions.presenceOfElementLocated(by)
        );
    }

    private WebElement waitForElementPresent (By by, String error_message){
        return waitForElementPresent(by, error_message, 5);
    }

    private WebElement waitForElementAndClick(By by, String error_message, long timeoutInSeconds){
        WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);
        element.click();
        return element;
    };

    private WebElement waitForElementAndSendKeys(By by, String value, String error_message, long timeoutInSeconds){
        WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);
        element.sendKeys(value);
        return element;
    };

    private boolean waitForElementNotPresent(By by, String error_message, long timeoutInSeconds){
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message + "\n");
        return wait.until(
                ExpectedConditions.invisibilityOfElementLocated(by));
    };

    private WebElement waitForElementAndClear (By by, String error_message, long timeoutInSeconds){
        WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);
        element.clear();
        return element;
    };

}