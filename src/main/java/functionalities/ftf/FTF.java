package functionalities.ftf;

import classes.DriverController;
import classes.FileController;
import classes.Paths;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

public class FTF extends DriverController { // [TO_DO] remove inheritance
    public FTF(FTFStrategy strategy) throws FileNotFoundException {
        strategy.execute();
    }
    public Map<String, String> getCategoriesByHashtag(String hashtag){
        return null;
//        Map<String, String> categories = new HashMap<>();
//        WebElement searchBox = driver.findElement(By.cssSelector("[placeholder=\"Search\"]"));
//        searchBox.sendKeys(hashtag);
//        List<String> categoryLinks = driver.findElements(By.className("yCE8d")).stream().map(webElement -> webElement.getAttribute("href")).collect(Collectors.toList());
//        List<String> categoryNames = driver.findElements(By.className("Ap253")).stream().map(WebElement::getText).collect(Collectors.toList());
//        for(int i=0;i<categoryLinks.size();i++) categories.put(categoryNames.get(i), categoryLinks.get(i));
//        return categories;
    }

    public static List<String> getFollowedProfileNames() {
        FileController fileController = new FileController(Paths.FOLLOWED_PROFILES_LINKS);
        return fileController.getAllData();
    }

}
