package core;

import GUI.ControllerGUI;
import common.Paths;
import entities.Account;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import utilities.FileController;

import java.io.File;
import java.net.URL;
import java.util.*;

import static common.Paths.*;

public class Engine extends Application {

    @Override
    public void start(Stage stage) throws Exception {
//        EntityManagerFactory factory = Persistence.createEntityManagerFactory("instagram_bot"); //name of persistence unit here
//        EntityManager entityManager = factory.createEntityManager();

        URL url;
        String os = System.getProperty("os.name");
        if (os.equals("Mac OS X")) {
            url = new File("/Users/iliyan/Documents/programming/instagram_bot_manager/git/InstagramBotManager/src/main/java/GUI/javafx.fxml").toURI().toURL();
        } else {
            //url = new File("D:\\COMPUTER\\Hristian projects\\JAVA\\InstagramBotManeger project\\git\\InstagramBotManager\\src\\main\\java\\GUI\\javafx.fxml").toURI().toURL();
            url = new File("D:\\COMPUTER\\Hristian projects\\JAVA\\IG_Bot\\InstagramBotManager\\src\\main\\java\\GUI\\javafx.fxml").toURI().toURL();
        }

        ControllerGUI gui = new ControllerGUI();
        Parent root = FXMLLoader.load(url);
        stage.setTitle("Instagram Bot Manager");

        Scene scene = new Scene(root);
        stage.setScene(scene);
        //scene.getStylesheets().add("D:\\COMPUTER\\Hristian projects\\JAVA\\zip files\\style.css");
        //gui.anchorPane_setDefaultOptions();
        stage.show();
        programSetupOnStart();
    }

    public void run() {
        launch();
    }

    public static WebDriver getWebDriver() {
        System.setProperty("webdriver.chrome.driver", Paths.DRIVER_PATH);

//        DesiredCapabilities capabilities = DesiredCapabilities.chrome();
//        capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
//        //chrome browser set to phone mode
//        HashMap<String, String> mobileEmulation  = new HashMap<>();
//        String deviceName = "Galaxy S5";	//iPhone X/Galaxy S5
//        mobileEmulation.put("deviceName",deviceName);
//        Map<String, Object> chromeOptions = new HashMap<>();
//        chromeOptions.put("mobileEmulation",mobileEmulation);
//        capabilities.setCapability(ChromeOptions.CAPABILITY,chromeOptions);
//        return new ChromeDriver(capabilities);

        Map<String, String> mobileEmulation = new HashMap<>();
        mobileEmulation.put("deviceName", "Galaxy S5");
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setExperimentalOption("mobileEmulation", mobileEmulation);
        chromeOptions.addArguments("--lang=en");
        chromeOptions.addArguments("--disable-notifications");
//        chromeOptions.addArguments("--headless", "--window-size=1050,840", "--lang=en", "--disable-gpu", "--allow-insecure-localhost", "--acceptInsecureCerts=true");

//        chromeOptions.addArguments("--headless", "--window-size=1050,840", "--lang=en", "--disable-gpu", "--ignore-certificate-errors",
//                "--disable-extensions", "--no-sandbox", "--disable-dev-shm-usage", "--allow-insecure-localhost", "--acceptInsecureCerts=true");

//        chromeOptions.addArguments("--window-size=1050,840", "--lang=en", "--disable-gpu", "--ignore-certificate-errors",
//                "--disable-extensions", "--no-sandbox", "--disable-dev-shm-usage");

        return new ChromeDriver(chromeOptions);
    }

    public void programSetupOnStart() throws InterruptedException {
        Thread auto = new Thread(() -> {
            try {
                /*
                String url_db = "jdbc:mysql://localhost:3306/instagram_bot";
                String user_db = "root";
                String password_db = "rootpassword";

                Class.forName("com.mysql.jdbc.Driver");
                Connection connection = DriverManager.getConnection(url_db, user_db, password_db);
                //String query = "Insert into accounts(id) values(101)";
                JSONObject obj = new JSONObject();

                File f0 = new File("D:\\COMPUTER\\Hristian projects\\JAVA\\InstagramBotManeger project\\git\\resources\\accounts\\phone_headmaster\\uploadPhotos\\images\\158428510_1117595855334961_642162430569187952_n.jpg");
                File f1 = new File("D:\\COMPUTER\\Hristian projects\\JAVA\\InstagramBotManeger project\\git\\resources\\accounts\\phone_headmaster\\uploadPhotos\\images\\158919073_440346570357279_1504342314697831141_n.jpg");
                obj.put("photo_0", f0);
                obj.put("photo_1", f1);
                PreparedStatement pstmt = connection.prepareStatement("insert into accounts values(?)");
                pstmt.setString(2, obj.toString());
                //pstmt.setString(2, "hello");
                //pstmt.executeUpdate();

                int i = pstmt.executeUpdate();
                System.out.println(i + " records inserted");
                Thread.sleep(1000000000);
                */

                Controller c = new Controller();
                FileController fc = new FileController(CREDENTIALS_FILE);
                boolean firstLoop = true;
                //when chrome is headless the code breaks and i don't want to fix it now so i just move the window off the screen so it doesn't interfere.
                //Controller.webDriver.manage().window().setPosition(new Point(-2000, 0));

                while (true) {
                    List<Account> accounts = fc.getAllAccounts();


//                    while (!ControllerGUI.isLoggedIn) {
//                        System.out.println("Waiting for login...");
//                        Thread.sleep(5000);
//                    }
//                    c.logOutOfIG(accounts.get(1).getName());
//                    Thread.sleep(10000000);


                    for (Account account : accounts) {
                        //change all Paths depending on which account is currently logged in
                        if(firstLoop){
                            c.changeAllPaths("account-name-here", account.getName());
                        }else{
                            int index;
                            if(accounts.indexOf(account) == 0){
                                index = accounts.size() - 1;
                            }else{
                                index = accounts.indexOf(account) - 1;
                            }

                            c.changeAllPaths(accounts.get(index).getName(), account.getName());
                        }

                        //if the current account doesn't have all the files the program needs, it creates them
                        File accountFolder = new File(ACCOUNTS_FOLDER + account.getName());
                        if(!accountFolder.exists()){
                            c.createAccountFiles(account.getName());
                        }

//                        while (!ControllerGUI.isLoggedIn) {
//                            System.out.println("Waiting for login...");
//                            Thread.sleep(10000);
//                        }

                        c.loginToIG(account.getName());

                        Thread checkForUnfollowing = new Thread(c.unfollowing);
                        Thread checkForPhotoUpload = new Thread(c.photoUpload);
                        Thread executeHashtagFollowing = new Thread(c.hashtagFollowing);

                        Thread.sleep(5000);
                        checkForPhotoUpload.start();
                        checkForPhotoUpload.join();

                        //Thread.sleep(3600000);
                        Thread.sleep(5000);
                        executeHashtagFollowing.start();
                        executeHashtagFollowing.join();

                        //Thread.sleep(25000);
                        checkForUnfollowing.start();
                        checkForUnfollowing.join();
                        Thread.sleep(5000);

                        c.logOutOfIG(account.getName());
                        firstLoop = false;
                    }
                }

            } catch (Exception ignored) {
                ignored.printStackTrace();
                System.out.println(ignored);
            }
        });
        auto.start();
    }
}
