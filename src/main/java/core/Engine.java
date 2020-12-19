package core;

import common.Paths;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.File;
import java.net.URL;

public class Engine extends Application {
    @Override
    public void start (Stage stage) throws Exception {
        URL url = new File("D:\\COMPUTER\\Hristian projects\\JAVA\\InstagramBotManeger project\\git\\InstagramBotManager\\src\\main\\java\\GUI\\javafx.fxml").toURI().toURL();
        Parent root = FXMLLoader.load(url);
        stage.setTitle("Instagram Bot Manager");

        Scene scene = new Scene(root);
        //scene.getStylesheets().add("D:\\COMPUTER\\Hristian projects\\JAVA\\zip files\\style.css");
        stage.setScene(scene);
        stage.show();
    }

    public void run() {
//        Thread t = new Thread(() -> {
//        });
//        t.start();
        launch();
    }

    public static WebDriver getWebDriver(){
        System.setProperty("webdriver.chrome.driver", Paths.DRIVER_PATH);
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--lang=en");
        return new ChromeDriver(options);
    }
}
