package classes;

import functionalities.Paths;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.FileNotFoundException;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

public class DriverController {
    protected WebDriver driver;
    public DriverController(){
        System.setProperty("webdriver.chrome.driver", Paths.DRIVER_PATH);
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--lang=en");
        driver = new ChromeDriver(options);
        navigateToURL(Paths.SITE_URL);
    }

    public void navigateToURL(String url){
        driver.navigate().to(url);
        if(url.equals(Paths.SITE_URL)){ // instagram only
            WebElement acceptBtn = driver.findElement(By.cssSelector(".aOOlW.bIiDR")); //TO FIX !!!
            acceptBtn.click(); // accept cookies
        }
    }

    public void loginToIG(String accountName) throws FileNotFoundException { // String username, String password
        navigateToURL(Paths.SITE_URL);
        FileReader fileController = new FileReader(Paths.CREDENTIALS_FILE);
        Account account = fileController.getAccountByName(accountName);
        WebElement usernameInput = handleExceptionIfElementNotFound(By.cssSelector("[name=\"username\"]"), "username");
        if(usernameInput == null) return;

        usernameInput.sendKeys(account.getEmail());
        WebElement passwordInput = driver.findElement(By.cssSelector("[name=\"password\"]"));
        passwordInput.sendKeys(account.getPassword());
        driver.findElement(By.xpath("//div[text()='Log In']")).click();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS); // wait for 5s
        driver.findElement(By.xpath("//button[text()='Not Now']")).click(); // save your login info decline
        driver.findElement(By.xpath("//button[text()='Not Now']")).click(); // notifications decline
    }

    private WebElement handleExceptionIfElementNotFound(By selector, String fieldName){
        try {
            WebElement element = driver.findElement(selector);
            return element;
        } catch(Exception ex){
            System.out.println("Field " + fieldName + " cannot be found.");
            return null;
        }
    }

    public WebDriver getDriver() {
        return driver;
    }
}
