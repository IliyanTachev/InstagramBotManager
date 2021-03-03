package core;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Functionalities {
    public void executeFTF(){

        String accountNameSelector = ".sqdOP.yWX7d._8A5w5.ZIAjV";
        String followBtnSelector = ".sqdOP.yWX7d.y3zKF";
        String nextImageArrowSelector = "._65Bje.coreSpriteRightPaginationArrow";

        int totalAllowedFollowActions = 20;
        List<String> hashtags = Arrays.asList("followtofollow", "followtofollowback", "followtofollowers", "followtofollo");
        Controller controller = new Controller();
        int followNumberPerHashtag = totalAllowedFollowActions / hashtags.size();

        List<String> followedAccounts = new ArrayList<>();

        for (String hashtag : hashtags) {
            controller.navigateToURL("https://www.instagram.com/explore/tags/" + hashtag);

            String imagesClass = "._9AhH0";
            List<WebElement> imagesToClickOn = controller.findElements(By.cssSelector(imagesClass));

            for(int i=0;i<followNumberPerHashtag;i++){
                int random = (int) ((Math.random() * (5 - 1)) + 1);
                controller.wait(random);
                try {
                    imagesToClickOn.get(i).click();
                } catch(Exception ignored){}

                try {
                    WebElement followBtn = controller.findElement(By.cssSelector(followBtnSelector));
                    controller.wait(2);
                    controller.executeScript(followBtn);
                    String accountName = controller.findElement(By.cssSelector(accountNameSelector)).getText();
                    followedAccounts.add(accountName);
                } catch(Exception ex){
                    System.out.println("Ne moje da nameri butona za follow");
                } finally {
                    controller.findElement(By.cssSelector(nextImageArrowSelector)).click();
                    controller.wait(3);
                }

//                finally {
//                    controller.findElement(By.cssSelector("._2dDPU.CkGkG")).click(); // click onto dialog to exit current image
//                }
                //followedAccounts.add(controller.findElement(By.cssSelector(profileNameSelector)).getText());
            }
        }
    }
}
