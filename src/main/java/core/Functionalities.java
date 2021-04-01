package core;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import utilities.FileController;

import javafx.scene.image.ImageView;

import java.awt.*;

import javafx.scene.control.Button;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.List;

import javafx.scene.control.TextArea;
import utilities.InstagramDownloader;

import static GUI.ControllerGUI.changePhotoBtnWasClicked;
import static GUI.ControllerGUI.approvePhotoBtnWasClicked;
import static common.Paths.*;

public class Functionalities {
    public void executeFTF() throws IOException, InterruptedException {

        String accountNameSelector = ".sqdOP.yWX7d._8A5w5.ZIAjV";
        String followBtnSelector = ".sqdOP.yWX7d.y3zKF";
        String nextImageArrowSelector = "._65Bje.coreSpriteRightPaginationArrow";
        String instagramLogoSelector = ".Igw0E.rBNOH.eGOV_.ybXk5._4EzTm";
        String photoCloseBtn = ".wpO6b";

        int totalAllowedFollowActions = 8;
        List<String> hashtags = Arrays.asList("followtofollow", "followtofollowback", "followtofollowers", "followtofollo");
        Controller controller = new Controller();
        JavascriptExecutor js = (JavascriptExecutor) Controller.webDriver;
        int followNumberPerHashtag = totalAllowedFollowActions / hashtags.size();
        List<String> followedAccounts = new ArrayList<>();
        Random r = new Random();
        String imagesClass = "._9AhH0";

        for (String hashtag : hashtags) {
            controller.navigateToURL("https://www.instagram.com/explore/tags/" + hashtag);

            for (int i = 0; i < followNumberPerHashtag; i++) {
                //scroll so we can get the latest posts insted of the top ones
                js.executeScript("window.scrollTo(0,550)", "");

                List<WebElement> imagesToClickOn = controller.findElements(By.cssSelector(imagesClass));
                String accountName = "";
                imagesToClickOn.get(r.nextInt(9) + 9).click();
                //imagesToClickOn.get(i).click();
                //if profile if already followed skip it
                try{
                    if(controller.findElement(By.cssSelector(".bY2yH")).findElement(By.cssSelector(".sqdOP.yWX7d._8A5w5")).getText().equals("Following")){
                        controller.navigateToURL("https://www.instagram.com/explore/tags/" + hashtag);
                        continue;
                    }
                }catch (Exception ignored){}

                //opens profile, follows, and saves its name
                WebElement account = controller.findElement(By.cssSelector(".sqdOP.yWX7d._8A5w5.ZIAjV "));
                accountName = account.getText();
                followedAccounts.add(accountName);
                account.click();
                controller.findElement(By.cssSelector("._5f5mN.jIbKX._6VtSN.yZn4P")).click();

                int numberOfPhotosInProfile = controller.findElements(By.cssSelector(imagesClass)).size();
                if(numberOfPhotosInProfile > 3){
                    numberOfPhotosInProfile = 3;
                }
                //clicks on the image and likes it (3 or less times)
                for(int k = 0; k < numberOfPhotosInProfile; k++){
                    //gets the new images (from the account itself not from a hashtag)
                    imagesToClickOn = controller.findElements(By.cssSelector(imagesClass));

                    //scroll so we can get the persons last 3 (or less) photos and like them
                    js.executeScript("window.scrollTo(0,0)", "");
                    while (true){
                        try{
                            js.executeScript("window.scrollBy(0,120)", "");
                            imagesToClickOn.get(k).click();
                            break;
                        }catch (Exception ignored){}
                    }

                    //scroll so we can hit the like btn (that sometimes will not be visible at first unless we scroll)
                    js.executeScript("window.scrollTo(0,0)", "");
                    while (true){
                        try{
                            js.executeScript("window.scrollBy(0,100)", "");
                            controller.findElement(By.cssSelector(".fr66n")).findElement(By.cssSelector(".wpO6b")).click();
                            break;
                        }catch (Exception ignored){}
                    }

                    controller.findElement(By.cssSelector(".mXkkY.HOQT4")).click();
                }
                //return to hashtag
                controller.navigateToURL("https://www.instagram.com/explore/tags/" + hashtag);
            }
        }
        //writes followed accounts and date (variable - lineToWrite) onto a text file (so it can unfollow them after specified amount of days)
        if(!followedAccounts.isEmpty()){
            String lineToWrite = "";
            for (int i = 0; i < followedAccounts.size(); i++) {
                if (i == followedAccounts.size() - 1) {
                    lineToWrite += followedAccounts.get(i) + "---";
                } else {
                    lineToWrite += followedAccounts.get(i) + ",";
                }
            }
            Calendar currentDate = Calendar.getInstance();
            Calendar futureDate = controller.additionOfDaysToDate(4);
            lineToWrite += currentDate.getTime() + ">>>>" + futureDate.getTime();
//            Date futureDate = controller.additionOfDaysToDate(4);
//            lineToWrite += currentDate.getTime() + ">>>>" + futureDate;

            //writes 'lineToWrite' variable onto a text file
            FileController fc = new FileController(FOLLOWED_PROFILES);
            fc.writeData(lineToWrite, true, false);
            System.out.println("Line To Write On File -> " + lineToWrite);
        }

        //goes back to home page by entering default instagram url address
        controller.navigateToURL("https://www.instagram.com");
        //declines notification (if it pops up)
        try{
            controller.findElement(By.cssSelector(".aOOlW.HoLwm")).click();
        }catch (Exception ignored){}
    }

    public void executeUploadPhoto(ImageView imageToUpoad, TextArea oldCaption, TextArea newCaption) throws IOException, AWTException, InterruptedException {
//        int randomPhotoIndex;
//        List<Integer> photosToSkip = new ArrayList<>();
//        for (int i = 10; i > 0; i--) {
//            List<WebElement> photosInArray = controller.findElements(By.cssSelector("._9AhH0"));
//            for (int integer : photosToSkip) {
//                photosInArray.remove(integer);
//            }
//
//            randomPhotoIndex = random.nextInt(10);
//            photosToSkip.add(randomPhotoIndex);
//            try {
//                photosInArray.get(randomPhotoIndex).click();
//            } catch (Exception e) {
//                JavascriptExecutor executor = (JavascriptExecutor) controller.webDriver;
//                executor.executeScript("arguments[0].click();", photosInArray.get(randomPhotoIndex));
//            }
//            //downloader.downloadMedia(Controller.webDriver.getCurrentUrl(), "D:\\COMPUTER\\Hristian projects\\JAVA\\InstagramBotManeger project\\git\\resources\\uploadPhotos");
//            downloader.downloadMedia(Controller.webDriver.getCurrentUrl(), "D:\\COMPUTER\\Хр фаилове\\random bs");
//            controller.findElement(By.cssSelector(".Iazdo")).click();
//        }


        List<String> accountsList = Arrays.asList("https://www.instagram.com/xiaomiuiglobal/", "https://www.instagram.com/aliartist3d/",
                "https://www.instagram.com/ktechzone/", "https://www.instagram.com/samsung.world_/",
                "https://www.instagram.com/smart_samsungs/", "https://www.instagram.com/ispazio/",
                "https://www.instagram.com/with.samsung/", "https://www.instagram.com/stylo.cell/");
        Controller controller = new Controller();
        FileController fileController = new FileController(BANNED_PHOTOS);
        JavascriptExecutor js = (JavascriptExecutor) Controller.webDriver;
        InstagramDownloader downloader = new InstagramDownloader();
        Random random = new Random();

        while (true) {
            //reads file: 'bannedPhotos.txt'
            List<String> unwantedPhotos = fileController.getAllData();
            //navigate to random account from 'accountsList'
            controller.navigateToURL(accountsList.get(random.nextInt(8)));
            //scrolls down so it can get the photos otherwise it throws an exception because it can't find any photos
            js.executeScript("window.scrollBy(0,450)", "");

            String photoToBeBannedURL = "";
            do {
                //get a random photo from the random account
                List<WebElement> photosInArray = controller.findElements(By.cssSelector("._9AhH0"));
                int randomPhotoIndex = random.nextInt(10);
                try {
                    photosInArray.get(random.nextInt(10)).click();
                } catch (Exception e) {
                    js.executeScript("arguments[0].click();", photosInArray.get(randomPhotoIndex));
                }
                photoToBeBannedURL = Controller.webDriver.getCurrentUrl();
                controller.findElement(By.cssSelector(".Iazdo")).click();
            } while (unwantedPhotos.contains(photoToBeBannedURL));
            //download the photo and gets its url
            String imageName = downloader.downloadImage(photoToBeBannedURL, "D:\\COMPUTER\\Hristian projects\\JAVA\\InstagramBotManeger project\\git\\resources\\uploadPhotos\\images");
            //add photo to file: 'bannedPhotos.txt'
            fileController.writeData(photoToBeBannedURL, true, false);

            //takes the image url and splits it so we can get the name of the image itself
            String[] tempName = imageName.split("/");
            String filename = tempName[tempName.length - 1].split("[?]")[0];

            //wait until user decides if he is going to change the photo or upload it
            while (!changePhotoBtnWasClicked && !approvePhotoBtnWasClicked) {
                Thread.sleep(500);
            }
            //check which btn was pressed by the user
            if (changePhotoBtnWasClicked) {
                //deletes image
                Path imagesPath = Paths.get("D:\\COMPUTER\\Hristian projects\\JAVA\\InstagramBotManeger project\\git\\resources\\uploadPhotos\\images\\"
                        + filename);
                Files.delete(imagesPath);
                changePhotoBtnWasClicked = false;
            } else if (approvePhotoBtnWasClicked) {
                //uploads image
                controller.uploadPhoto("D:\\COMPUTER\\Hristian projects\\JAVA\\InstagramBotManeger project\\git\\resources\\uploadPhotos\\images\\"
                        + filename, "test123");
                //deletes image
                Path imagesPath = Paths.get("D:\\COMPUTER\\Hristian projects\\JAVA\\InstagramBotManeger project\\git\\resources\\uploadPhotos\\images\\"
                        + filename);
                Files.delete(imagesPath);

                //returns to the screen with all functionalities (in the javafx app)
                approvePhotoBtnWasClicked = false;
                //Need to pane transition (doesn't work currently)
                //ControllerGUI.paneTransitionAnimation(ControllerGUI.pane5, ControllerGUI.pane4);

                //end the loop of photo downloading
                break;
            }
        }
    }

    public void executeUploadPhoto_Auto(Button uploadPhotoBtn, Button rejectPhotoBtn, Button approvePhotoBtn) throws IOException, AWTException, InterruptedException {
        Controller controller = new Controller();
        FileController fileController = new FileController(BANNED_PHOTOS);
        JavascriptExecutor js = (JavascriptExecutor) Controller.webDriver;
        InstagramDownloader downloader = new InstagramDownloader();
        Random random = new Random();
        FileController fController = new FileController(ACCOUNTS_TO_GET_PHOTOS_FROM);

        List<String> accountsList = fController.getAllData();
        //IF 'ACCOUNTS_TO_GET_PHOTOS_FROM' FILE IS EMPTY 'RETURN' (TO END THE METHOD) AND ALSO PRINT ERROR MSG
        if(accountsList.isEmpty() || (accountsList.size() == 1 && accountsList.get(0).trim().equals(""))){
            System.out.println("Photo downloading failed. No accounts to download photos from were found!");
            rejectPhotoBtn.setVisible(false);
            approvePhotoBtn.setVisible(false);
            uploadPhotoBtn.setVisible(true);
            controller.navigateToURL("https://www.instagram.com/");
            return;
        }

        for (int i = 0; i < 2; i++) {
            //reads file: 'bannedPhotos.txt'
            List<String> unwantedPhotos = fileController.getAllData();

            String photoToBeBannedURL = "";
            String photoCaption = "";
            do {
                //navigate to random account from 'accountsList'
                controller.navigateToURL(accountsList.get(random.nextInt(accountsList.size())));
                //scrolls down so it can get the photos otherwise it throws an exception because it can't find any photos
                js.executeScript("window.scrollTo(0,450)", "");

                //get a random photo from the random account
                List<WebElement> photosInArray = controller.findElements(By.cssSelector("._9AhH0"));
                int randomPhotoIndex = random.nextInt(10);
                try {
                    photosInArray.get(randomPhotoIndex).click();
                } catch (Exception e) {
                    js.executeScript("arguments[0].click();", photosInArray.get(randomPhotoIndex));
                }

                //clicks 'more' to expand caption after that it takes the caption and passes it to method 'generatePhotoCaption'
                try {
                    controller.findElement(By.cssSelector(".sXUSN")).click();
                } catch (Exception ignored) {
                }
                String caption = controller.findElement(By.cssSelector("._8Pl3R")).findElement(By.tagName("span")).getText();
                photoCaption = controller.generatePhotoCaption(controller.findElement(By.cssSelector(".sqdOP.yWX7d._8A5w5.ZIAjV")).getText(), caption);

                photoToBeBannedURL = Controller.webDriver.getCurrentUrl();
                controller.findElement(By.cssSelector(".Iazdo")).click();
            } while (unwantedPhotos.contains(photoToBeBannedURL));
            //download the photo and gets its url
            String imageName = downloader.downloadImage(photoToBeBannedURL, PHOTOS_TO_BE_CHECKED);
            //add photo to file: 'bannedPhotos.txt'
            fileController.writeData(photoToBeBannedURL, true, false);

            //takes the image url and splits it so we can get the name of the image itself
            String[] tempName = imageName.split("/");
            String filename = tempName[tempName.length - 1].split("[?]")[0];

            //wait until user decides if he is going to change the photo or upload it
            while (!changePhotoBtnWasClicked && !approvePhotoBtnWasClicked) {
                Thread.sleep(500);
            }
            //check which btn was pressed by the user
            if (changePhotoBtnWasClicked) {
                //deletes image
                Path imagesPath = Paths.get(PHOTOS_TO_BE_CHECKED + filename);
                Files.delete(imagesPath);
                //we decrease i by 1 so at the end we can have the number of photos we want, not less
                i--;
                changePhotoBtnWasClicked = false;
            } else if (approvePhotoBtnWasClicked) {
                //moves the photo from the old path to the new one and deletes the old photo
                String oldPath = PHOTOS_TO_BE_CHECKED;
                String newPath = PHOTOS_TO_UPLOAD;
                File image = new File(oldPath + filename);
                image.renameTo(new File(newPath + filename));
                image.delete();

                //writes the ready caption in file 'photosCaptions.txt' (in order to write in that file we need a new file controller object)
                FileController fc = new FileController(PHOTOS_CAPTIONS);
                String stringToWrite = filename + "\n^\n" + photoCaption + "\n<--------------------------------------------------->";
                fc.writeData(stringToWrite, true, false);
                approvePhotoBtnWasClicked = false;
            }
        }
        rejectPhotoBtn.setVisible(false);
        approvePhotoBtn.setVisible(false);
        uploadPhotoBtn.setVisible(true);
        controller.navigateToURL("https://www.instagram.com/");
    }
}
