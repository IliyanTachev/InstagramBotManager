package core;

import org.openqa.selenium.By;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Functionalities {
    public void executeFTF(){
        int totalAllowedFollowActions = 20;
        List<String> hashtags = Arrays.asList("followtofollow");
        Controller controller = new Controller();
        //followtofollowback", "followtofollowers", "followtofollo");

        for (String hashtag : hashtags) {
            controller.navigateToURL("https://www.instagram.com/explore/tags/" + hashtag);
            int followNumberPerHashtag = totalAllowedFollowActions / hashtags.size();

            List<String> imageLinkElements = controller.findElements(By.cssSelector(".v1Nh3.kIKUG._bz0w > a"))
                    .stream()
                    .map(element -> element.getAttribute("href"))
                    .filter(link -> {
                        controller.get(link);
                        String profileUsername = controller.findElement(By.cssSelector("a.sqdOP.yWX7d._8A5w5.ZIAjV")).getText();
                        return false;//!FTF.getFollowedProfileNames().contains(profileUsername);
                    })
                    .collect(Collectors.toList());

            imageLinkElements.forEach(unfollowedLink -> {
                controller.findElement(By.xpath("//button[text()='Follow']")).click();
                controller.wait(2);

                String followStatus = controller.findElement(By.xpath("//button[text()='Following']")) != null ? "completed" : "failed";
                System.out.println(unfollowedLink + " -> " + followStatus);
            });
        }
    }
}
