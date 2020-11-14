import funcs.FTF;

import java.io.FileNotFoundException;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws InterruptedException, FileNotFoundException {
//        String driverPath;
//        String os = System.getProperty("os.name");
//        if(os.equals("Mac OS X")) driverPath = "./chromedriver";
//        else driverPath = "./chromedriver.exe"; // windows
//
//        System.setProperty("webdriver.chrome.driver", driverPath);
//        WebDriver driver=new ChromeDriver();
//        driver.navigate().to("https://instagram.com");

//        WebElement acceptBtn = driver.findElement(By.cssSelector(".aOOlW.bIiDR"));
//        acceptBtn.click();

//        driver.findElement(By.xpath("//div[text()='Log In']")).click();
//        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
//        driver.findElement(By.xpath("//button[text()='Not Now']")).click(); // save your login info decline
//        driver.findElement(By.xpath("//button[text()='Not Now']")).click(); // notifications decline

        FTF FTF = new FTF("phoneprincipal");
        Map<String, String> result = FTF.getCategoriesByHashtag("#caseiphone");
        for(Map.Entry<String, String> e : result.entrySet()){
            System.out.println(e.getKey() + " -> " + e.getValue());
        }
        Scanner scanner = new Scanner(System.in);
        System.out.print("Select category: ");
        String category = "#" + scanner.next();
        FTF.navigateTo(result.get(category));

    }
}
