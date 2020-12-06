package application_window;

import classes.Account;
import classes.DriverController;
import classes.FileController;
import classes.Paths;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.control.Button;

import javafx.event.ActionEvent;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;

import java.awt.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.scene.image.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import static classes.Paths.CUSTOM_CURSOR;

public class Controller implements Initializable {

    ObservableList<String> accountList;
    private String br = "-fx-background-radius: 30;";
    boolean pane1_isVisible = true;
    boolean isClicked = false;

    @FXML
    private Button createAcc;
    @FXML
    private Button logIn;
    @FXML
    private ComboBox<String> chooseAcc = new ComboBox<>();
    @FXML
    private AnchorPane anchorPane = new AnchorPane();
    @FXML
    private Pane pane1 = new Pane();


    private void initialize_accountList() throws FileNotFoundException {
        accountList = FXCollections.observableArrayList();
        FileController controller = new FileController(Paths.CREDENTIALS_FILE);
        List<Account> accounts = controller.getAllAccounts();
        for(int i = 0; i < accounts.size(); i++){
            accountList.add(accounts.get(i).getName());
        }
    }

    @FXML
    private void anchorPane_defaultOptions () throws FileNotFoundException, InterruptedException {
        //Button -> logIn
//        System.out.println("kartof");
//        FileInputStream inputstream = new FileInputStream("D:\\COMPUTER\\Hristian projects\\JAVA\\InstagramBotManeger project\\git\\InstagramBotManager\\src\\main\\java\\application_window\\cantClickCursor1.png");
//        Image image = new Image(inputstream);
//        logIn.setCursor(new ImageCursor(image, image.getWidth() / 5, image.getHeight() /5));
        Thread t = new Thread(() -> {
            if(isClicked){
                pane1.setVisible(false);                                                                ////////////////////////
            }
        });
        t.start();
    }

    @FXML
    private void button_onAction(ActionEvent event) {
        Button b = (Button) event.getSource();
        String buttonID = b.getId();

        switch (buttonID){
            case "createAcc":
                System.out.println("Functionality not finished!");
                break;
            case "logIn":
                isClicked = true;                                                           ////////////////////////////
                System.out.println("Functionality not finished!");
                break;
        }
    }

    @FXML
    private void button_onMouseEntered(MouseEvent event){
        Button button = (Button) event.getSource();
        button.setStyle(br + "-fx-background-color: #1e1e1e;");
    }

    @FXML
    private void button_onMouseExited(MouseEvent event){
        Button button = (Button) event.getSource();
        button.setStyle(br + "-fx-background-color: #141414;");
    }

    @FXML
    private void button_onMousePressed(MouseEvent event){
        Button button = (Button) event.getSource();
        button.setStyle(br + "-fx-background-color: #141414; -fx-text-fill: #94c487;");
    }

    @FXML
    private void button_onMouseReleased(MouseEvent event){
        Button button = (Button) event.getSource();
        button.setStyle(br + "-fx-background-color: #1e1e1e; -fx-text-fill: #bdffae;");
    }

    @FXML
    private void chooseAcc_onMouseClicked() throws FileNotFoundException {
        initialize_accountList();
        chooseAcc.setItems(accountList);
        chooseAcc.setValue(accountList.get(0));
        logIn.setDisable(false);
        //logIn.setCursor(Cursor.HAND);
    }

    @FXML
    private void logIn_onAction() throws FileNotFoundException {
        //DriverController driver = new DriverController();
        //driver.loginToIG("phoneprincipal");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
