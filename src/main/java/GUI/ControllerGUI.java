package GUI;

import core.Controller;
import entities.Account;
import javafx.scene.Node;
import javafx.scene.control.Separator;
import javafx.scene.effect.BoxBlur;
import javafx.scene.shape.Line;
import utilities.FileController;
import common.Paths;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import javafx.event.ActionEvent;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;

import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;


public class ControllerGUI implements Initializable, Cloneable {

    Controller controller = new Controller();
    ObservableList<String> accountList;

    @FXML
    private AnchorPane anchorPane = new AnchorPane();
    @FXML
    private Pane pane1 = new Pane();
    @FXML
    private Pane pane2 = new Pane();
    @FXML
    private Pane pane3 = new Pane();

    @FXML
    private Button b_createAcc;
    @FXML
    private Button b_logIn;
    @FXML
    private Button b_followForFollow;

    @FXML
    private ComboBox<String> chooseAcc = new ComboBox<>();

    @FXML
    private Separator line1;
    @FXML
    private Separator line2;
    @FXML
    private Separator bottomLineTest;
    @FXML
    private Separator topLineTest;

    @FXML
    private Line leftL;
    @FXML
    private Line rightL;

    private void initialize_accountList() throws FileNotFoundException { // TODO: THIS SHOULD HAPPEN AT THE START OF THE PROGRAM AND NOT ON MOUSE ENTERED INTO THE ANCHOR PANE
        accountList = FXCollections.observableArrayList();
        FileController controller = new FileController(Paths.CREDENTIALS_FILE);
        List<Account> accounts = controller.getAllAccounts();
        for (int i = 0; i < accounts.size(); i++) {
            accountList.add(accounts.get(i).getName());
        }
    }

    private String getButtonRadius(Button button) {
        String text = button.getStyle() + "          ";
        String wordToFind = "-fx-background-radius";

        Pattern word = Pattern.compile(wordToFind);
        Matcher match = word.matcher(text);

        match.find();
        String backgroundRadius = text.substring(match.start(), match.end() + 10);
        backgroundRadius = backgroundRadius.substring(0, backgroundRadius.indexOf(";") + 1);

        return backgroundRadius;
    }

    private void setLineCoordinatesAndRadius(Line line1, Line line2, double X, double rotateValue, double Y, boolean finalSet, Map<String, List<Double>> lines) {
        //we multiply by -1 the values for line2 because the two lines go in opposite directions
        if(finalSet){
            if(X == lines.get("bottom").get(0)){
                line1.setLayoutX(X);
                line2.setLayoutX(lines.get("top").get(0));

                line1.setRotate(rotateValue);
                line2.setRotate(90);

                line1.setLayoutY(Y);
                line2.setLayoutY(lines.get("top").get(1));
            } else {
                line1.setLayoutX(X);
                line2.setLayoutX(lines.get("right").get(0));

                line1.setRotate(rotateValue);
                line2.setRotate(180);

                line1.setLayoutY(Y);
                line2.setLayoutY(lines.get("right").get(1));
            }
        } else {
            line1.setLayoutX(line1.getLayoutX() + X);
            line2.setLayoutX(line2.getLayoutX() + X * -1);

            line1.setRotate(line1.getRotate() + rotateValue);
            line2.setRotate(line2.getRotate() + rotateValue);

            line1.setLayoutY(line1.getLayoutY() + Y);
            line2.setLayoutY(line2.getLayoutY() + Y * -1);
        }
    }

    private void paneTransitionAnimation(Pane pane1, Pane pane2) {
        Thread threadBlur = new Thread(() -> {
            BoxBlur blur = new BoxBlur();
            BigDecimal opacity = new BigDecimal("1.00");
            int counter = 0;
            double blurHeight = 0;
            double blurWidth = 0;
            blur.setIterations(10);

            for (int i = 0; i < 40 * 2; i++) {
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                counter++;
                if (counter > 40) {
                    if (counter == 41) {
                        pane1.setVisible(false);
                        pane2.setVisible(true);
                    }

                    opacity = opacity.add(new BigDecimal("0.025"));

                    blur.setWidth(blurWidth);
                    blurWidth--;
                    if (i % 3 == 0) {
                        blur.setHeight(blurHeight);
                        blurHeight--;
                    }

                    if (counter == 80) {
                        blur.setWidth(0);
                        blur.setHeight(0);
                    }
                    pane2.setEffect(blur);

                } else {

                    blur.setWidth(blurWidth);
                    blurWidth++;
                    if (i % 3 == 0) {
                        blur.setHeight(blurHeight);
                        blurHeight++;
                    }

                    opacity = opacity.subtract(new BigDecimal("0.025"));
                    pane1.setEffect(blur);
                }

                pane1.setOpacity(opacity.doubleValue());
            }
        });
        threadBlur.start();
    }

    private <T> void colorTransition(Node obj) {
        Thread tb = new Thread(() -> {
            int r = 0, g = 0, b = 255;
            for (int i = 1; i > 0; i++) {

                try {
                    Thread.sleep(30);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if (g < 255 && b == 255 && r == 0) {
                    g++;
                }
                if (g == 255 && r == 0) {
                    if (b >= 1) {
                        b--;
                    } else {
                        r++;
                    }
                }
                if (g == 255 && r >= 1) {
                    if (r < 255) {
                        r++;
                    }
                }
                if (g == 255 && r == 255) {
                    g--;
                }
                if (g < 255 && g >= 1 && r == 255) {
                    g--;
                }
                if (r == 255 && g == 0) {
                    if (b < 255) {
                        b++;
                    } else {
                        r--;
                    }
                }
                if (r < 255 && r >= 1 && b == 255) {
                    r--;
                }

                String hex = String.format("#%02x%02x%02x;", r, g, b);

                obj.setStyle(obj.getStyle() + "-fx-background-color: " + hex);
//                if(obj instanceof Button) {
//                    //obj = (Button) obj;
//                    ((Button)obj).setStyle(((Button)obj).getStyle() + "-fx-background-color: " + hex);
//                } else if(obj instanceof AnchorPane) {
//                    AnchorPane anchorPane = (AnchorPane) obj;
//                    anchorPane.setStyle(anchorPane.getStyle() + "-fx-background-color: " + hex);
//                } else if(obj instanceof Separator) {
//                    Separator separator = (Separator) obj;
//                    separator.setStyle(separator.getStyle() + "-fx-background-color: " + hex);
//                }
            }
        });
        tb.start();
    }

    //TODO: FINISH loadingAnimation method ->
    /*
    private void loadingAnimation(Separator line1, Separator line2) throws InterruptedException {
        //LINE 2 SHOULD BE: X = line1.getLayoutX() + 64, Y = line1.getLayoutY();
        double leftLineLayoutX = line1.getLayoutX();
        double leftLineLayoutY = line1.getLayoutY();

        double rightLineLayoutX = line2.getLayoutX();
        double rightLineLayoutY = line2.getLayoutY();

        double topLineLayoutX = topLineTest.getLayoutX(); //  X = 407
        double topLineLayoutY = topLineTest.getLayoutY(); //  Y = 154

        double bottomLineLayoutX = bottomLineTest.getLayoutX();
        double bottomLineLayoutY = bottomLineTest.getLayoutY();
        //line1 X = 378, Y = 181

        Thread.sleep(1000);
        Thread threadLA = new Thread(() -> {
            boolean passedOnce = false;
            for (int i = 0; i < 1; i--) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if(passedOnce){
                    line1.setLayoutX(leftLineLayoutX);
                    line1.setLayoutY(leftLineLayoutY);

                    line2.setLayoutX(rightLineLayoutX);
                    line2.setLayoutY(rightLineLayoutY);

                    passedOnce = false;
                }

                if (line1.getLayoutX() >= leftLineLayoutX && line1.getLayoutX() < bottomLineLayoutX && line1.getLayoutY() >= leftLineLayoutY && line1.getLayoutY() < bottomLineLayoutY) {
                    //TODO: LEFT TO BOTTOM                    (just wanted the highlight)
                    setLineCoordinatesAndRadius(line1, line1.getLayoutX() + 1, line1.getRotate() + 3.1, line1.getLayoutY() + 1);

                    //left line is bottom (ending the movement of line 1)
                    if (line1.getLayoutX() == bottomLineLayoutX - 1) {
                        setLineCoordinatesAndRadius(line1, bottomLineLayoutX, 90, bottomLineLayoutY);
                    }
                } else if (line1.getLayoutX() == bottomLineLayoutX && line2.getLayoutX() <= rightLineLayoutX && line2.getLayoutY() <= rightLineLayoutY && line2.getLayoutX() > topLineLayoutX && line2.getLayoutY() > topLineLayoutY) {
                    //check if line 1 is bottom so we can move right line to the top (1 line above)
                    //TODO: RIGHT TO TOP                    (just wanted the highlight)
                    setLineCoordinatesAndRadius(line2, line2.getLayoutX() - 1.1, line2.getRotate() + 3.3, line2.getLayoutY() - 1);

                    //right line is top (ending the movement of line 2)
                    if (line2.getLayoutX() > topLineLayoutX && line2.getLayoutX() < topLineLayoutX + 2) {
                        setLineCoordinatesAndRadius(line2, topLineLayoutX, 90, topLineLayoutY);
                    }
                } else if (line2.getLayoutX() == topLineLayoutX && line1.getLayoutX() >= bottomLineLayoutX && line1.getLayoutY() <= bottomLineLayoutY && line1.getLayoutX() < rightLineLayoutX && line1.getLayoutY() > rightLineLayoutY) {
                    //check if line2 is top so we can move bottom line to the right (1 line above)
                    //TODO: BOTTOM TO RIGHT                    (just wanted the highlight)
                    setLineCoordinatesAndRadius(line1, line1.getLayoutX() + 1, line1.getRotate() + 3.1, line1.getLayoutY() - 1);

                    //bottom line is right (ending the movement of line 1)
                    if (line1.getLayoutX() < rightLineLayoutX && line1.getLayoutX() >= rightLineLayoutX - 2) {
                        setLineCoordinatesAndRadius(line1, rightLineLayoutX - 3, 180, rightLineLayoutY);
                    }
                }else if(line1.getLayoutX() == rightLineLayoutX - 3 && line2.getLayoutX() <= topLineLayoutX && line2.getLayoutY() >= topLineLayoutY - 2 && line2.getLayoutX() > leftLineLayoutX && line2.getLayoutY() < leftLineLayoutY){
                    //check if line1 is right so we can move top line to the left (1 line above)
                    //TODO: TOP TO LEFT                    (just wanted the highlight)
                    setLineCoordinatesAndRadius(line2, line2.getLayoutX() - 1.07, line2.getRotate() + 3.3, line2.getLayoutY() + 1);

                    //top line is left (ending the movement of line 2)
                    if (line2.getLayoutX() > leftLineLayoutX && line2.getLayoutX() < leftLineLayoutX + 1) {
                        setLineCoordinatesAndRadius(line2, leftLineLayoutX - 3, 180, leftLineLayoutY);
                    }
                } else {
                    double line1X = line1.getLayoutX();
                    double line1Y = line1.getLayoutY();

                    line1.setLayoutX(line2.getLayoutX());
                    line1.setLayoutY(line2.getLayoutY());

                    line2.setLayoutX(line1X);
                    line2.setLayoutY(line1Y);
                    passedOnce = true;
                }
            }
        });
        threadLA.start();
    }
     */

    private void betterLoadingAnimation(Line line1, Line line2, double X, double Y, double length, double width) throws InterruptedException {
        BoxBlur blur = new BoxBlur();
        blur.setHeight(width);
        blur.setWidth(width);
        blur.setIterations(1);

        line1.setStrokeWidth(width);
        line1.setStartX(0);
        line1.setStartY(0);
        line1.setEndX(0);
        line1.setEndY(length);
        line1.setLayoutX(X);
        line1.setLayoutY(Y);

        line2.setStrokeWidth(width);
        line2.setStartX(0);
        line2.setStartY(0);
        line2.setEndX(0);
        line2.setEndY(length);
        line2.setLayoutX(X + (length + 16));
        line2.setLayoutY(Y);

        line1.setEffect(blur);
        line2.setEffect(blur);
        line1.setVisible(true);
        line2.setVisible(true);

        double leftLineLayoutX = line1.getLayoutX();
        double leftLineLayoutY = line1.getLayoutY();

        double rightLineLayoutX = line2.getLayoutX();
        double rightLineLayoutY = line2.getLayoutY();

        double topLineLayoutX = leftLineLayoutX + (rightLineLayoutX - leftLineLayoutX) / 2 + 0.5;
        double topLineLayoutY = leftLineLayoutY - 30;

        double bottomLineLayoutX = topLineLayoutX;
        double bottomLineLayoutY = leftLineLayoutY + 30;

        Map<String, List<Double>> lines = new HashMap<>();
        lines.put("left", new ArrayList<Double>(){{add(leftLineLayoutX); add(leftLineLayoutY);}});
        lines.put("right", new ArrayList<Double>(){{add(rightLineLayoutX); add(rightLineLayoutY);}});
        lines.put("top", new ArrayList<Double>(){{add(topLineLayoutX); add(topLineLayoutY);}});
        lines.put("bottom", new ArrayList<Double>(){{add(bottomLineLayoutX); add(bottomLineLayoutY);}});
        int numberOfSteps = 30;

//        System.out.println("Left line X: " + leftLineLayoutX + "    Y: " + leftLineLayoutY);
//        System.out.println("Bottom line X: " + bottomLineLayoutX + "    Y: " + bottomLineLayoutY);
//        System.out.println("Right line X: " + rightLineLayoutX + "    Y: " + rightLineLayoutY);
//        System.out.println("Top line X: " + topLineLayoutX + "    Y: " + topLineLayoutY);
        Thread.sleep(2000);
        Thread threadBLA = new Thread(() -> {
            boolean passedOnce = false;
            for (int i = 0; i < 1; i--) {
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if(passedOnce){
                    passedOnce = false;
                }

                if (line1.getLayoutX() >= leftLineLayoutX && line1.getLayoutX() < bottomLineLayoutX && line1.getLayoutY() >= leftLineLayoutY && line1.getLayoutY() < bottomLineLayoutY) {
                    //left line go bottom, right line go top
                    double travelX = (bottomLineLayoutX - leftLineLayoutX) / numberOfSteps;
                    double travelY = (bottomLineLayoutY - leftLineLayoutY) / numberOfSteps;

                    setLineCoordinatesAndRadius(line1, line2, travelX, 90 / 30, travelY, false, lines);

                    if (line1.getLayoutX() > bottomLineLayoutX && line1.getLayoutX() < bottomLineLayoutX + travelX) {
                        setLineCoordinatesAndRadius(line1, line2, bottomLineLayoutX, 90, bottomLineLayoutY, true, lines);
                    }
                    //System.out.println("Line1 X: " + line1.getLayoutX() + "    Y: " + line1.getLayoutY() + "    Rotate: " + line1.getRotate() + "    isVisible: " + line1.isVisible());
                } else if (line1.getLayoutX() >= bottomLineLayoutX && line1.getLayoutX() < rightLineLayoutX && line1.getLayoutY() <= bottomLineLayoutY && line1.getLayoutY() > rightLineLayoutY) {
                    //bottom line go right, top line go left
                    double travelX = (rightLineLayoutX - bottomLineLayoutX) / numberOfSteps;
                    double travelY = (rightLineLayoutY - bottomLineLayoutY) / numberOfSteps;

                    setLineCoordinatesAndRadius(line1, line2, travelX, 90 / 30, travelY, false, lines);

                    if (line1.getLayoutX() > rightLineLayoutX && line1.getLayoutX() < rightLineLayoutX + travelX) {
                        setLineCoordinatesAndRadius(line1, line2, rightLineLayoutX, 180, rightLineLayoutY, true, lines);
                    }
                    //System.out.println("Line1 X: " + line1.getLayoutX() + "    Y: " + line1.getLayoutY() + "    Rotate: " + line1.getRotate() + "    isVisible: " + line1.isVisible());
                } else {
                    double line1X = line1.getLayoutX();
                    double line1Y = line1.getLayoutY();

                    line1.setLayoutX(line2.getLayoutX());
                    line1.setLayoutY(line2.getLayoutY());

                    line2.setLayoutX(line1X);
                    line2.setLayoutY(line1Y);
                    passedOnce = true;
                }
            }
        });
        threadBLA.start();
    }

    @FXML
    private void anchorPane_defaultOptions() throws FileNotFoundException, InterruptedException {
        //Button -> logIn
//        System.out.println("kartof");
//        FileInputStream inputstream = new FileInputStream("D:\\COMPUTER\\Hristian projects\\JAVA\\InstagramBotManeger project\\git\\InstagramBotManager\\src\\main\\java\\application_window\\cantClickCursor1.png");
//        Image image = new Image(inputstream);
//        logIn.setCursor(new ImageCursor(image, image.getWidth() / 5, image.getHeight() /5));

//        Thread t = new Thread(() -> {
//            if(isClicked){
//                pane1.setVisible(false);                                                                ////////////////////////
//            }
//        });
//        t.start();
    }

    @FXML
    private void button_onAction(ActionEvent event) {
        Button b = (Button) event.getSource();
        String buttonID = b.getId();

        switch (buttonID) {
            case "b_createAcc":

                System.out.println("Functionality not finished!");
                break;

            case "b_logIn":

                paneTransitionAnimation(pane1, pane2);
                Thread t = new Thread(() -> {
                    try {
                        //loadingAnimation(line1, line2);
                        betterLoadingAnimation(leftL, rightL, 375, 181, 45, 3);
                        controller.loginToIG(chooseAcc.getValue());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    //System.out.println("Line1 X: " + line1.getLayoutX() + "    Y: " + line1.getLayoutY() + "            Line2 X: " + line2.getLayoutX() + "    Y: " + line2.getLayoutY() + "            Bottom line X: " + bottomLineTest.getLayoutX() + "    Y: " + bottomLineTest.getLayoutY() + "            Top line X: " + topLineTest.getLayoutX() + "    Y: " + topLineTest.getLayoutY());
                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    paneTransitionAnimation(pane2, pane3);
                });
                t.start();
                break;

            case "b_followForFollow":

                System.out.println("Functionality not finished!");
                break;

            default:
                System.out.println("Button action not set!");
        }
    }

    @FXML
    private void button_onMouseEntered(MouseEvent event) {
        Button button = (Button) event.getSource();
//        if(button.getId().equals("b_createAcc")){
//            System.out.printf("%.10f", 3.3333333333 + 3.3333333333 + 3.3333333333 + 0.1);
//        }
        button.setStyle(getButtonRadius(button) + "-fx-background-color: #1e1e1e;");
    }

    @FXML
    private void button_onMouseExited(MouseEvent event) {
        Button button = (Button) event.getSource();
        button.setStyle(getButtonRadius(button) + "-fx-background-color: #141414;");
    }

    @FXML
    private void button_onMousePressed(MouseEvent event) {
        Button button = (Button) event.getSource();
        button.setStyle(getButtonRadius(button) + "-fx-background-color: #141414; -fx-text-fill: #94c487;");
    }

    @FXML
    private void button_onMouseReleased(MouseEvent event) {
        Button button = (Button) event.getSource();
        button.setStyle(getButtonRadius(button) + "-fx-background-color: #1e1e1e; -fx-text-fill: #bdffae;");
    }

    @FXML
    private void chooseAcc_onMouseClicked() throws FileNotFoundException {
        initialize_accountList();
        chooseAcc.setItems(accountList);
        chooseAcc.setValue(accountList.get(0));
        b_logIn.setDisable(false);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

}
