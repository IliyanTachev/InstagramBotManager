package classes;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.FileNotFoundException;
import java.util.concurrent.TimeUnit;

public abstract class BaseFunctionality {
    protected static WebDriver driver;
    private static final String SITE_URL = "https://instagram.com";
    private static String CREDENTIALS_FILE;
    static{
        String driverPath;
        String os = System.getProperty("os.name");
        if(os.equals("Mac OS X")) {
            driverPath = "./chromedriver";
            CREDENTIALS_FILE = "/Users/iliyan/Documents/programming/instagram_bot_manager/git/config/credentials";
        }
        else {
            driverPath = "chromedriver.exe"; // windows
            CREDENTIALS_FILE = "D:\\COMPUTER\\Hristian projects\\JAVA\\InstagramBotManeger project\\git\\resources\\credentials";
        }

        System.setProperty("webdriver.chrome.driver", driverPath);
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--lang=en");
        driver = new ChromeDriver(options);
    }

    public static void navigateToURL(String url){
        driver.navigate().to(url); // go to instagram
        WebElement acceptBtn = driver.findElement(By.cssSelector(".aOOlW.bIiDR")); //TO FIX !!!
        acceptBtn.click(); // accept cookies

    }

    public static void loginToIG(String accountName) throws FileNotFoundException { // String username, String password
        navigateToURL(SITE_URL);
        FileReader fileController = new FileReader(CREDENTIALS_FILE);
        Account account = fileController.getAccountByName(accountName);

        WebElement usernameInput = driver.findElement(By.cssSelector("[name=\"username\"]"));
        usernameInput.sendKeys(account.getEmail());
        WebElement passwordInput = driver.findElement(By.cssSelector("[name=\"password\"]"));
        passwordInput.sendKeys(account.getPassword());
        driver.findElement(By.xpath("//div[text()='Log In']")).click();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS); // wait for 5s
        driver.findElement(By.xpath("//button[text()='Not Now']")).click(); // save your login info decline
        driver.findElement(By.xpath("//button[text()='Not Now']")).click(); // notifications decline
    }


}
