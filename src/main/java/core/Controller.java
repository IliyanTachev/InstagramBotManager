package core;

import org.openqa.selenium.JavascriptExecutor;
import utilities.FileController;
import common.Paths;
import entities.Account;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

public class Controller {
    private static WebDriver webDriver = Engine.getWebDriver();

//    static{
//        webDriver = Engine.getWebDriver();
//    }

    public void navigateToURL(String url){
        webDriver.navigate().to(url);
        if(url.equals(Paths.SITE_URL)){ // instagram only
            try{
                WebElement acceptBtn = webDriver.findElement(By.cssSelector("button.aOOlW.bIiDR")); //TO FIX !!!
                acceptBtn.click(); // accept cookies
            } catch(NoSuchElementException ignored){
                //should be empty no need to worry
            }
        }
    }

    public void loginToIG(String accountName) throws FileNotFoundException { // String username, String password
        navigateToURL(Paths.SITE_URL);
        wait(10);
        FileController fileController = new FileController(Paths.CREDENTIALS_FILE);
        Account account = fileController.getAccountByName(accountName);
        WebElement usernameInput = findElement(By.cssSelector("[name=\"username\"]"));

        usernameInput.sendKeys(account.getEmail());
        WebElement passwordInput = findElement(By.cssSelector("[name=\"password\"]"));
        passwordInput.sendKeys(account.getPassword());
        findElement(By.xpath("//div[text()='Log In']")).click();
        wait(5); // wait for 5s
        findElement(By.xpath("//button[text()='Not Now']")).click(); // save your login info decline
        findElement(By.xpath("//button[text()='Not Now']")).click(); // notifications decline
    }

    public WebElement findElement (By selector){
       return webDriver.findElement(selector);
    }

    public List<WebElement> findElements (By selector){
        return webDriver.findElements(selector);
    }

    public void get(String link){
        webDriver.get(link);
    }

    public void wait(int seconds){
       webDriver.manage().timeouts().implicitlyWait(seconds, TimeUnit.SECONDS);
    }

    public void executeScript(WebElement element) {
        JavascriptExecutor javascriptExecutor = (JavascriptExecutor) webDriver;
//        javascriptExecutor.executeScript("arguments[0].click();", findElement(By.cssSelector("button.sqdOP.yWX7d.y3zKF")));
        javascriptExecutor.executeScript("arguments[0].click();", element);
    }
}
