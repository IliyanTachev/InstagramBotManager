package functionalities.impl;

import classes.DriverController;
import functionalities.FTFStrategy;
import org.openqa.selenium.WebDriver;

import java.util.Arrays;
import java.util.List;

public class DefaultStrategy extends FTFStrategy {
    private List<String> hashtags;
    private int totalAllowedFollowActions = 20;

    public DefaultStrategy(DriverController driver){
        super(driver);
    }

    public void execute() {
     hashtags = Arrays.asList("followtofollow");
     // "followtofollowback", "followtofollowers", "followtofollo");
     for(String hashtag : hashtags){
        driverController.navigateToURL("https://www.instagram.com/explore/tags/" + hashtag);
        int followNumberPerHashtag = totalAllowedFollowActions / hashtags.size();

     }
   }
}
