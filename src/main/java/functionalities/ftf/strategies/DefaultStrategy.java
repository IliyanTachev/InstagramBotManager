package functionalities.ftf.strategies;

import classes.DriverController;
import functionalities.ftf.FTF;
import functionalities.ftf.FTFStrategy;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class DefaultStrategy extends FTFStrategy {
    private List<String> hashtags;
    private int totalAllowedFollowActions = 20;

    public DefaultStrategy(DriverController driver) {
        super(driver);
    }

    public void execute() {
        hashtags = Arrays.asList("followtofollow");
        //followtofollowback", "followtofollowers", "followtofollo");

        for (String hashtag : hashtags) {
            driverController.navigateToURL("https://www.instagram.com/explore/tags/" + hashtag);
            int followNumberPerHashtag = totalAllowedFollowActions / hashtags.size();
            List<String> imageLinkElements = driverController.getDriver().findElements(By.cssSelector(".v1Nh3.kIKUG._bz0w > a"))
                    .stream()
                    .map(element -> element.getAttribute("href"))
                    .filter(link -> {
                        WebDriver driver = driverController.getDriver();
                        driver.get(link);
                        String profileUsername = driverController.getDriver().findElement(By.cssSelector("a.sqdOP.yWX7d._8A5w5.ZIAjV")).getText();
                        return !FTF.getFollowedProfileNames().contains(profileUsername);
                    })
                    .collect(Collectors.toList());

            imageLinkElements.forEach(unfollowedLink -> {
                driverController.getDriver().findElement(By.xpath("//button[text()='Follow']")).click();
                driverController.getDriver().manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS); // wait for 5s
                String followStatus = driverController.getDriver().findElement(By.xpath("//button[text()='Following']")) != null ? "completed" : "failed";
                System.out.println(unfollowedLink + " -> " + followStatus);
            });
        }
    }
}
