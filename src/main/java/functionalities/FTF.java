package functionalities;

import classes.BaseFunctionality;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FTF extends BaseFunctionality {
    public FTF(String accountName) throws FileNotFoundException {
        loginToIG(accountName);
    }
    public Map<String, String> getCategoriesByHashtag(String hashtag){
        Map<String, String> categories = new HashMap<>();
        WebElement searchBox = driver.findElement(By.cssSelector("[placeholder=\"Search\"]"));
        searchBox.sendKeys(hashtag);
        List<String> categoryLinks = driver.findElements(By.className("yCE8d")).stream().map(webElement -> webElement.getAttribute("href")).collect(Collectors.toList());
        List<String> categoryNames = driver.findElements(By.className("Ap253")).stream().map(WebElement::getText).collect(Collectors.toList());
        for(int i=0;i<categoryLinks.size();i++) categories.put(categoryNames.get(i), categoryLinks.get(i));
        return categories;
    }


}
