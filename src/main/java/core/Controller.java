package core;

import GUI.ControllerGUI;
import org.openqa.selenium.JavascriptExecutor;
import utilities.FileController;
import common.Paths;
import entities.Account;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static common.Paths.*;
import static common.Paths.PHOTOS_TO_UPLOAD;

public class Controller {
    FileController followedProfiles = new FileController(FOLLOWED_PROFILES);
    public static WebDriver webDriver = Engine.getWebDriver();

    Runnable unfollowing = new Runnable() {
        @Override
        public void run() {
            try {
                /*
                //the code bellow downloads 10 photos (in random order) from an instagram account
                JavascriptExecutor js = (JavascriptExecutor) controller.webDriver;
                InstagramDownloader downloader = new InstagramDownloader();
                Random r = new Random();

                controller.navigateToURL("https://www.instagram.com/with.samsung/");
                js.executeScript("window.scrollBy(0,450)", "");
                Thread.sleep(3000);

                List<WebElement> photosInArray = controller.findElements(By.cssSelector("._9AhH0"));
                System.out.println("Number of photos: " + photosInArray.size());
                try{
                    photosInArray.get(36).click();
                } catch (Exception ignored){
                    System.out.println("normal click failed need to use javascript");
                    js.executeScript("arguments[0].click();", photosInArray.get(35));
                }

                 */

//                int randomPhotoIndex;
//                List<Integer> photosToSkip = new ArrayList<>();
//                for (int i = 10; i > 0; i--) {
//                    if(photosToSkip.size() % 10 == 0 && !photosToSkip.isEmpty()){
//                        //think of a formula for scrolling that scales with the number of banned photos
//                        js.executeScript("window.scrollBy(0,450)", "");
//                    }
//                    List<WebElement> photosInArray = contoller.findElements(By.cssSelector("._9AhH0"));
//                    for (int integer : photosToSkip) {
//                        photosInArray.remove(integer);
//                    }
//
//                    randomPhotoIndex = r.nextInt(10);
//                    //write to file instead of adding to list
//                    photosToSkip.add(randomPhotoIndex);
//                    try {
//                        photosInArray.get(randomPhotoIndex).click();
//                    } catch (Exception e) {
//                        JavascriptExecutor executor = (JavascriptExecutor) contoller.webDriver;
//                        executor.executeScript("arguments[0].click();", photosInArray.get(randomPhotoIndex));
//                    }
//                    //downloader.downloadMedia(Controller.webDriver.getCurrentUrl(), "D:\\COMPUTER\\Hristian projects\\JAVA\\InstagramBotManeger project\\git\\resources\\uploadPhotos");
//                    downloader.downloadMedia(Controller.webDriver.getCurrentUrl(), "D:\\COMPUTER\\Хр фаилове\\random bs");
//                    contoller.findElement(By.cssSelector(".Iazdo")).click();
//                }

//                String photoToBeBannedURL = "https://instagram.fsof9-1.fna.fbcdn.net/v/t51.2885-15/e35/157692751_275458017282720_3640385515095652013_n.jpg?tp=1&_nc_ht=instagram.fsof9-1.fna.fbcdn.net&_nc_cat=100&_nc_ohc=g08bz6madjwAX9v1AyU&oh=eaa07b2c0b3cf7020ecf4c1077db2fa8&oe=6071756D";
//                String[] tempName = photoToBeBannedURL.split("/");
//                String filename = tempName[tempName.length - 1].split("[?]")[0];
////                File file = new File("D:\\COMPUTER\\Hristian projects\\JAVA\\InstagramBotManeger project\\git\\resources\\uploadPhotos\\images\\" + filename);
////                Image image = new Image(file.toURI().toString());
//                //System.out.println("Image location: " + "D:\\COMPUTER\\Hristian projects\\JAVA\\InstagramBotManeger project\\git\\resources\\uploadPhotos\\images\\" + filename);
//                Image image = new Image("D:\\COMPUTER\\Hristian projects\\JAVA\\InstagramBotManeger project\\git\\resources\\uploadPhotos\\images\\157692751_275458017282720_3640385515095652013_n.jpg");
//                ControllerGUI g = new ControllerGUI();
//                g.imageToUpload.setImage(image);

                //gets image caption
//                controller.navigateToURL("https://www.instagram.com/samsung.world_/");
//                JavascriptExecutor js = (JavascriptExecutor) Controller.webDriver;
//                js.executeScript("window.scrollBy(0,450)", "");
//                List<WebElement> photosInArray = controller.findElements(By.cssSelector("._9AhH0"));
//                photosInArray.get(0).click();
//                try{
//                    controller.findElement(By.cssSelector(".sXUSN")).click();
//                } catch (Exception ignored){}
//                String caption = controller.findElement(By.cssSelector("._8Pl3R")).findElement(By.tagName("span")).getText();
//                System.out.println("Caption:\n" + caption);

                //Thread.sleep(1200000);

                //while(true){
                //TODO: wait for current executing threads so interference doesn't happen
                //wait.start();
                //waits for login before checking for automatic functions to execute
//                    while(!ControllerGUI.isLoggedIn){
//                        System.out.println("Waiting for login...");
//                        Thread.sleep(10000);
//                    }

                //wait for current executing threads so interference doesn't happen
//                    if(ControllerGUI.ftf != null){
//                        while (ControllerGUI.ftf.isAlive()) {
//                            Thread.sleep(10000);
//                        }
//                    }
                System.out.println("\nUnfollow check active!");

                List<String> dataFromFile = followedProfiles.getAllData();
                List<String> tempDataFromFile = dataFromFile;
                List<String> peopleToUnfollow;
                for (String datum : dataFromFile) {
                    if(datum.trim().equals("")){
                        continue;
                    }
                    //String format is:
                    //samplePersonToUnfollow1,samplePersonToUnfollow2,samplePersonToUnfollowN---Tue Aug 28 11:10:40 UTC 2018>>>>Tue Aug 30 11:10:40 UTC 2018
                    //this will help understand the lines below.
                    String pplToUnfollowString = datum.split("---")[0];
                    String dates = datum.split("---")[1];
                    String unfollowDateString = dates.split(">>>>")[1];
                    peopleToUnfollow = Arrays.asList(pplToUnfollowString.split(","));

                    Calendar currDate = Calendar.getInstance();
                    Calendar unfollowDate = parseStringToCalendarObj(unfollowDateString);

                    if (unfollowDate.before(currDate)) {
                        //unfollow accounts
                        System.out.println("UNFOLLOW ENGAGE!");
                        System.out.println("Accounts to unfollow: " + peopleToUnfollow);
                        unfollowListOfAccounts(peopleToUnfollow);
                        dislikePhotos(peopleToUnfollow, 3);
                        navigateToURL("https://www.instagram.com/");
                        //remove current line from txt file
                        String fileDataToBeWritten = "";
                        System.out.println("\n\n\nData in the whole file ->");
                        for (String s : tempDataFromFile) {
                            System.out.println(s);
                        }
                        System.out.println("\n\n");
                        for (String s : tempDataFromFile) {
                            System.out.println("Current string ->\n" + s + "\nString to be removed ->\n" + datum + "\nDoes current string equal string to be removed: " + s.equals(datum));
                            if(!s.equals(datum)){
                                fileDataToBeWritten += s + "\n";
                            }
                        }
                        System.out.println("Data to be written after removal:\n-----------------------------------------------------------------------------------------\n" + fileDataToBeWritten + "\n-----------------------------------------------------------------------------------------");
                        followedProfiles.writeData(fileDataToBeWritten, false, true);
                        tempDataFromFile = Arrays.asList(fileDataToBeWritten.split("\\r?\\n"));
                    }
                }
                System.out.println("Unfollow check done!\n");
                //20min
                //Thread.sleep(1200000);
                //1.30min
                //Thread.sleep(90000);
                //5min
                //Thread.sleep(300000);
                //2min
                //Thread.sleep(120000);
                //}
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };
    Runnable photoUpload = new Runnable() {
        @Override
        public void run() {
            try{
                //while(true){
                //TODO: wait for all threads to finish so we don't break something
                //wait.start();
                //waits for login before checking for automatic functions to execute
//                    while(!ControllerGUI.isLoggedIn){
//                        System.out.println("Waiting for login...");
//                        Thread.sleep(10000);
//                    }

                //wait for current executing threads so interference doesn't happen
//                    if(ControllerGUI.ftf != null){
//                        while (ControllerGUI.ftf.isAlive()) {
//                            Thread.sleep(10000);
//                        }
//                    }
//                    while (!checkForUnfollowing.getState().equals("TIMED_WAITING")) {
//                        Thread.sleep(10000);
//                    }
                System.out.println("\nUpload photo check active!");

                FileController photosCaptions = new FileController(PHOTOS_CAPTIONS);
                File dir = new File(PHOTOS_TO_UPLOAD);
                List<String> fileData = photosCaptions.getAllData();
                String stringDate = fileData.get(0);

                Calendar currentDate = Calendar.getInstance();
                Calendar dateOfLastUpload = parseStringToCalendarObj(stringDate);
                boolean sameDay = currentDate.get(Calendar.YEAR) == dateOfLastUpload.get(Calendar.YEAR)
                        && currentDate.get(Calendar.MONTH) == dateOfLastUpload.get(Calendar.MONTH)
                        && currentDate.get(Calendar.DAY_OF_MONTH) == dateOfLastUpload.get(Calendar.DAY_OF_MONTH);

                if(!sameDay){
                    System.out.println("UPLOAD PHOTO ENGAGE!");
                    String photoName = "";
                    for (final File f : dir.listFiles()) {
                        photoName = f.getName();
                        break;
                    }

                    String stringFileData = "";
                    fileData.set(0, currentDate.getTime() + "");
                    for (String fileDatum : fileData) {
                        stringFileData += fileDatum + "\n";
                    }
                    String caption = stringFileData.substring(stringFileData.indexOf(photoName), stringFileData.length()).split("<--------------------------------------------------->")[0].split("\\^")[1];

                    uploadPhoto(PHOTOS_TO_UPLOAD + photoName, caption);
                    String captionToDellete = photoName + "\n^" + caption + "<--------------------------------------------------->";
                    //System.out.println("Caption to delete:\n" + captionToDellete + "\nData in file:\n" + stringFileData + "\nData in file contains caption to delete: " + stringFileData.contains(captionToDellete));
                    stringFileData = stringFileData.replace(captionToDellete, "");
                    List<String> lines = Arrays.asList(stringFileData.split("\\r?\\n"));
                    stringFileData = "";
                    for (String line : lines) {
                        if(!line.equals("")){
                            stringFileData += line + "\n";
                        }
                    }
                    stringFileData = stringFileData.trim();
                    photosCaptions.writeData(stringFileData, true, true);

                    //deletes image
                    Path imagesPath = java.nio.file.Paths.get(PHOTOS_TO_UPLOAD + photoName);
                    Files.delete(imagesPath);
                }

                System.out.println("Upload photo check done!\n");
                //waits one hour until next check for photo upload
                //Thread.sleep(3600000);
                //2min
                //Thread.sleep(120000);
                //}
            }catch (Exception ignored){}
        }
    };
    Runnable hashtagFollowing = new Runnable() {
        @Override
        public void run() {
            Functionalities f = new Functionalities();
            try {
                f.executeFTF();
            } catch (Exception ignored) {}
        }
    };

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
        //findElement(By.cssSelector(".sqdOP.L3NKy.y3zKF")).click();
        wait(1);
        findElement(By.xpath("//button[text()='Log In']")).click();
        //wait(10);
        FileController fileController = new FileController(Paths.CREDENTIALS_FILE);
        Account account = fileController.getAccountByName(accountName);
        WebElement usernameInput = findElement(By.cssSelector("[name=\"username\"]"));

        usernameInput.sendKeys(account.getEmail());
        WebElement passwordInput = findElement(By.cssSelector("[name=\"password\"]"));
        passwordInput.sendKeys(account.getPassword());
        findElement(By.xpath("//div[text()='Log In']")).click();
        wait(5); // wait for 5s
        findElement(By.xpath("//button[text()='Not Now']")).click(); // save your login info decline
        try{
            findElement(By.xpath("//button[text()='Cancel']")).click(); // notifications decline
        } catch(Exception ignored) {}
    }

    public void unfollowListOfAccounts(List<String> accounts){
        String unfollowBtnSelect = "._5f5mN.-fzfL._6VtSN.yZn4P";
        for (String account : accounts) {
            navigateToURL("https://www.instagram.com/" + account);

            //scroll to top of the page to ensure the 'follow' btn is in range to be clicked
            JavascriptExecutor js = (JavascriptExecutor) Controller.webDriver;
            js.executeScript("window.scrollTo(0,0)", "");

            try {
                //clicks the unfollow button
                findElement(By.cssSelector(unfollowBtnSelect)).click();
                //clicks the confirmation for unfollow
                findElement(By.cssSelector(".aOOlW.-Cab_")).click();
            } catch (Exception ignored){}
        }
        navigateToURL("https://www.instagram.com");
    }

    public void dislikePhotos(List<String> accounts, int numberOfPhotosToBeDisliked){
        String imagesClass = "._9AhH0";
        JavascriptExecutor js = (JavascriptExecutor) Controller.webDriver;

        for (String account : accounts) {
            navigateToURL("https://www.instagram.com/" + account);

            for (int i = 0; i < numberOfPhotosToBeDisliked; i++) {
                List<WebElement> imagesToClickOn = findElements(By.cssSelector(imagesClass));

                js.executeScript("window.scrollTo(0,450)", "");
                imagesToClickOn.get(i).click();
                WebElement likeBtn;
                try{
                    likeBtn = findElement(By.cssSelector(".fr66n")).findElement(By.cssSelector(".wpO6b"));
                    //System.out.println(likeBtn.findElement(By.cssSelector(".QBdPU")).findElement(By.tagName("span")).findElement(By.cssSelector("._8-yf5")).getAttribute("aria-label"));
                    //findElement(By.xpath("//span[@aria-label='Unlike']")).click();
                    if(likeBtn.findElement(By.cssSelector(".QBdPU")).findElement(By.tagName("span")).findElement(By.cssSelector("._8-yf5")).getAttribute("aria-label").equals("Unlike")){
                        likeBtn.click();
                    }
                }catch (Exception ignored){
                    js.executeScript("window.scrollBy(0,100)", "");
                    likeBtn = findElement(By.cssSelector(".fr66n")).findElement(By.cssSelector(".wpO6b"));
                    //findElement(By.xpath("//span[@aria-label='Unlike']")).click();
                    if(likeBtn.findElement(By.cssSelector(".QBdPU")).findElement(By.tagName("span")).findElement(By.cssSelector("._8-yf5")).getAttribute("aria-label").equals("Unlike")){
                        likeBtn.click();
                    }
                }

                findElement(By.cssSelector(".mXkkY.HOQT4")).click();
            }
        }
    }

    public void uploadPhoto(String photoPath, String caption) throws AWTException {
        //navigate to instagram home page
        navigateToURL("https://www.instagram.com/");
        //click upload photo btn
        try{
            findElement(By.cssSelector(".q02Nz._0TPg")).click();
        } catch (Exception ignored){
            findElement(By.cssSelector(".aOOlW.HoLwm")).click();
            findElement(By.cssSelector(".q02Nz._0TPg")).click();
        }

        Robot robot = new Robot();
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        StringSelection stringSelection = new StringSelection(photoPath);
        clipboard.setContents(stringSelection, null);

        //Use Robot class instance to simulate CTRL+C and CTRL+V key events :
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_CONTROL);
        //Simulate Enter key event
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);

        //select 'next' btn
        try{
            findElement(By.cssSelector(".UP43G")).click();
        } catch (Exception ignored){
            try {
                findElement(By.xpath("//button[text()='Next']")).click();
            } catch (Exception ignored1){}
        }
        //enters the caption
        try{
            WebElement usernameInput = findElement(By.cssSelector("._472V_"));
            usernameInput.sendKeys(caption);
        } catch (Exception ignored){
            try {
                WebElement usernameInput = findElement(By.cssSelector("._472V_"));
                usernameInput.sendKeys(caption);
            } catch (Exception ignored1){}
        }
        //clicks Share btn
        try{
            findElement(By.cssSelector(".UP43G")).click();
        } catch (Exception ignored){
            try {
                findElement(By.xpath("//button[text()='Share']")).click();
            } catch (Exception ignored1){}
        }
    }

    public String generatePhotoCaption(String accountName, String oldCaption){
        String newCaption =
                "Wow!! Amazing photo ❤\n" +
                "What do you think about these phones?\n" +
                "Credit: *\n" +
                "#\n" +
                "#\n" +
                "#\n" +
                "#\n" +
                "#\n" +
                "#\n" +
                "#\n" +
                "#\n" +
                "#\n" +
                "#\n" +
                "#\n" +
                "#\n" +
                "#\n" +
                "#\n" +
                "#samsung #samsunggalaxy #huawei #xiaomi #smartphone #phone #technology #tech #shotoniphone #oppo #iphone10 #oneplus #smartwatch #photography #mi #camera #iphonese #google #sony";
        String patternRegex = "@[A-z0-9_.]+";
        Pattern r = Pattern.compile(patternRegex);

        int index = -1;
        String oldCaptionLowerCase = oldCaption.toLowerCase();
        if(oldCaptionLowerCase.contains("source") || oldCaptionLowerCase.contains("credit") ||
                oldCaptionLowerCase.contains("credits") || oldCaptionLowerCase.contains("found on") ||
                oldCaptionLowerCase.contains("\uD83D\uDCF7") || oldCaptionLowerCase.contains("pic by") ||
                oldCaptionLowerCase.contains("photo by")){
            if(oldCaptionLowerCase.contains("source")){
                index = oldCaptionLowerCase.indexOf("source");
            }else if(oldCaptionLowerCase.contains("credit")){
                index = oldCaptionLowerCase.indexOf("credit");
            }else if(oldCaptionLowerCase.contains("credits")){
                index = oldCaptionLowerCase.indexOf("credits");
            }else if(oldCaptionLowerCase.contains("found on")){
                index = oldCaptionLowerCase.indexOf("found on");
            }else if(oldCaptionLowerCase.contains("\uD83D\uDCF7")){
                index = oldCaptionLowerCase.indexOf("\uD83D\uDCF7");
            }else if(oldCaptionLowerCase.contains("pic by")){
                index = oldCaptionLowerCase.indexOf("pic by");
            }else if(oldCaptionLowerCase.contains("photo by")){
                index = oldCaptionLowerCase.indexOf("photo by");
            }

            oldCaption = oldCaption.substring(index, oldCaption.length());
            Matcher m = r.matcher(oldCaption);
            String personToCredit = "";
            if(m.find()){
                personToCredit = m.group();
            }else{
                personToCredit = "@" + accountName;
            }
            newCaption = newCaption.replace("*", personToCredit);
        }else{
            newCaption = newCaption.replace("*", "@" + accountName);
        }

        return newCaption;
    }

    public Calendar parseStringToCalendarObj(String stringDate){
        Calendar c = Calendar.getInstance();
        String[] tokens = stringDate.split(" ");

        switch (tokens[1]) {
            case "Jan":
                c.set(Integer.parseInt(tokens[5]), Calendar.JANUARY, Integer.parseInt(tokens[2]), Integer.parseInt(tokens[3].split(":")[0]), Integer.parseInt(tokens[3].split(":")[1]), Integer.parseInt(tokens[3].split(":")[2]));
                break;
            case "Feb":
                c.set(Integer.parseInt(tokens[5]), Calendar.FEBRUARY, Integer.parseInt(tokens[2]), Integer.parseInt(tokens[3].split(":")[0]), Integer.parseInt(tokens[3].split(":")[1]), Integer.parseInt(tokens[3].split(":")[2]));
                break;
            case "Mar":
                c.set(Integer.parseInt(tokens[5]), Calendar.MARCH, Integer.parseInt(tokens[2]), Integer.parseInt(tokens[3].split(":")[0]), Integer.parseInt(tokens[3].split(":")[1]), Integer.parseInt(tokens[3].split(":")[2]));
                break;
            case "Apr":
                c.set(Integer.parseInt(tokens[5]), Calendar.APRIL, Integer.parseInt(tokens[2]), Integer.parseInt(tokens[3].split(":")[0]), Integer.parseInt(tokens[3].split(":")[1]), Integer.parseInt(tokens[3].split(":")[2]));
                break;
            case "May":
                c.set(Integer.parseInt(tokens[5]), Calendar.MAY, Integer.parseInt(tokens[2]), Integer.parseInt(tokens[3].split(":")[0]), Integer.parseInt(tokens[3].split(":")[1]), Integer.parseInt(tokens[3].split(":")[2]));
                break;
            case "Jun":
                c.set(Integer.parseInt(tokens[5]), Calendar.JUNE, Integer.parseInt(tokens[2]), Integer.parseInt(tokens[3].split(":")[0]), Integer.parseInt(tokens[3].split(":")[1]), Integer.parseInt(tokens[3].split(":")[2]));
                break;
            case "Jul":
                c.set(Integer.parseInt(tokens[5]), Calendar.JULY, Integer.parseInt(tokens[2]), Integer.parseInt(tokens[3].split(":")[0]), Integer.parseInt(tokens[3].split(":")[1]), Integer.parseInt(tokens[3].split(":")[2]));
                break;
            case "Aug":
                c.set(Integer.parseInt(tokens[5]), Calendar.AUGUST, Integer.parseInt(tokens[2]), Integer.parseInt(tokens[3].split(":")[0]), Integer.parseInt(tokens[3].split(":")[1]), Integer.parseInt(tokens[3].split(":")[2]));
                break;
            case "Sep":
                c.set(Integer.parseInt(tokens[5]), Calendar.SEPTEMBER, Integer.parseInt(tokens[2]), Integer.parseInt(tokens[3].split(":")[0]), Integer.parseInt(tokens[3].split(":")[1]), Integer.parseInt(tokens[3].split(":")[2]));
                break;
            case "Oct":
                c.set(Integer.parseInt(tokens[5]), Calendar.OCTOBER, Integer.parseInt(tokens[2]), Integer.parseInt(tokens[3].split(":")[0]), Integer.parseInt(tokens[3].split(":")[1]), Integer.parseInt(tokens[3].split(":")[2]));
                break;
            case "Nov":
                c.set(Integer.parseInt(tokens[5]), Calendar.NOVEMBER, Integer.parseInt(tokens[2]), Integer.parseInt(tokens[3].split(":")[0]), Integer.parseInt(tokens[3].split(":")[1]), Integer.parseInt(tokens[3].split(":")[2]));
                break;
            case "Dec":
                c.set(Integer.parseInt(tokens[5]), Calendar.DECEMBER, Integer.parseInt(tokens[2]), Integer.parseInt(tokens[3].split(":")[0]), Integer.parseInt(tokens[3].split(":")[1]), Integer.parseInt(tokens[3].split(":")[2]));
                break;
        }

        return c;
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

    //public Date additionOfDaysToDate (int days) {
    public Calendar additionOfDaysToDate (int days) {
        Calendar futureDate = Calendar.getInstance();
        futureDate.add(Calendar.DAY_OF_MONTH, days);

        return futureDate;
        //return futureDate.getTime();
    }
}
