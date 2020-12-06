package application_window;

import classes.DriverController;
import classes.Functions;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import static classes.Paths.CUSTOM_CURSOR;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        URL url = new File("D:\\COMPUTER\\Hristian projects\\JAVA\\InstagramBotManeger project\\git\\InstagramBotManager\\src\\main\\java\\application_window\\javafx.fxml").toURI().toURL();
        Parent root = FXMLLoader.load(url);
        stage.setTitle("Instagram Bot Manager");

        Scene scene = new Scene(root);
        //scene.getStylesheets().add("D:\\COMPUTER\\Hristian projects\\JAVA\\zip files\\style.css");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) throws FileNotFoundException {
        launch(args);
    }
}
