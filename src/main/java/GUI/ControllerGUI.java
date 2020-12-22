package GUI;

import core.Controller;
import entities.Account;
import javafx.scene.Node;
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

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;


public class ControllerGUI implements Initializable, Cloneable {

    private final Controller controller = new Controller();
    private ObservableList<String> accountList;

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
    private Line leftL;
    @FXML
    private Line rightL;
    @FXML
    private Line bottomL;
    @FXML
    private Line topL;

    private Thread threadPaneTransition;

    private void initialize_accountList() throws FileNotFoundException { // TODO: THIS SHOULD HAPPEN AT THE START OF THE PROGRAM AND NOT ON MOUSE ENTERED INTO THE ANCHOR PANE
        accountList = FXCollections.observableArrayList();
        FileController controller = new FileController(Paths.CREDENTIALS_FILE);
        List<Account> accounts = controller.getAllAccounts();
        for (int i = 0; i < accounts.size(); i++) {
            accountList.add(accounts.get(i).getName());
        }
    }

    private void setLineCoordinatesAndRadius(Line line, double X, double rotate, double Y) {
        line.setLayoutX(X);
        line.setRotate(rotate);
        line.setLayoutY(Y);
    }

    private void setLineCoordinatesAndRadiusAnimation(Line line1, Line line2, Line line3, Line line4, double X, double rotateValue, double Y, boolean finalSet, Map<String, List<Double>> lines) {
        //we multiply by -1 the values for line2 because the two lines go in opposite directions
        if (finalSet) {
            if (X == lines.get("bottom").get(0)) {
                setLineCoordinatesAndRadius(line1, X, rotateValue, Y);
                setLineCoordinatesAndRadius(line2, lines.get("top").get(0), rotateValue, lines.get("top").get(1));
                setLineCoordinatesAndRadius(line3, lines.get("right").get(0), 0, lines.get("right").get(1));
                setLineCoordinatesAndRadius(line4, lines.get("left").get(0), 0, lines.get("left").get(1));
            } else if (X == lines.get("right").get(0)) {
                setLineCoordinatesAndRadius(line1, X, rotateValue, Y);
                setLineCoordinatesAndRadius(line2, lines.get("left").get(0), rotateValue, lines.get("left").get(1));
                setLineCoordinatesAndRadius(line3, lines.get("top").get(0), 90, lines.get("top").get(1));
                setLineCoordinatesAndRadius(line4, lines.get("bottom").get(0), 90, lines.get("bottom").get(1));
            }
        } else {
            setLineCoordinatesAndRadius(line1, line1.getLayoutX() + X, line1.getRotate() + rotateValue, line1.getLayoutY() + Y);
            setLineCoordinatesAndRadius(line2, line2.getLayoutX() + X * -1, line2.getRotate() + rotateValue, line2.getLayoutY() + Y * -1);
        }
    }

    private void setLineDefaultValues(Line line, double X, double Y, double length, double width, String color) {
        BoxBlur blur = new BoxBlur();
        blur.setHeight(width);
        blur.setWidth(width);
        blur.setIterations(1);

        line.setStrokeWidth(width);
        line.setStartX(0);
        line.setStartY(0);
        line.setEndX(0);
        line.setEndY(length);
        line.setLayoutX(X);
        line.setLayoutY(Y);
        line.setEffect(blur);
        line.setStyle("-fx-stroke: " + color);
        line.setVisible(true);
    }

    private void paneTransitionAnimation(Pane pane1, Pane pane2) {
        threadPaneTransition = new Thread(() -> {
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
        threadPaneTransition.start();
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

                if (obj instanceof Line) {
                    //System.out.println("r: " + r + "    g: " + g + "    b: " + b);
                    obj.setStyle(obj.getStyle() + "-fx-stroke: " + hex);
                } else {
                    obj.setStyle(obj.getStyle() + "-fx-background-color: " + hex);
                }
            }
        });
        tb.start();
    }

    private void loadingAnimation(Line line1, Line line2, Line bottom, Line top, double X, double Y, double length, double width) {
        setLineDefaultValues(line1, X, Y, length, width, "#bdffae");
        setLineDefaultValues(line2, X + (length + 16), Y, length, width, "#bdffae");
        setLineDefaultValues(bottom, X + 31, Y + 31, length, width, "#bdffae");
        setLineDefaultValues(top, X + 31, Y - 30, length, width, "#bdffae");
        bottom.setRotate(90);
        top.setRotate(-90);

        double leftLineLayoutX = line1.getLayoutX();
        double leftLineLayoutY = line1.getLayoutY();

        double rightLineLayoutX = line2.getLayoutX();
        double rightLineLayoutY = line2.getLayoutY();

        double topLineLayoutX = leftLineLayoutX + (rightLineLayoutX - leftLineLayoutX) / 2 + 0.5;
        double topLineLayoutY = leftLineLayoutY - 30;

        double bottomLineLayoutX = topLineLayoutX;
        double bottomLineLayoutY = leftLineLayoutY + 31;
        //System.out.printf("LeftL X: %f  Y: %f  Rotate: %f\nBottomL X: %f  Y: %f  Rotate: %f\nRightL X: %f  Y: %f  Rotate: %f\nTopL X: %f  Y: %f  Rotate: %f\n\n", line1.getLayoutX(), line1.getLayoutY(), line1.getRotate(), bottomL.getLayoutX(), bottomL.getLayoutY(), bottomL.getRotate(), rightL.getLayoutX(), rightL.getLayoutY(), rightL.getRotate(), topL.getLayoutX(), topL.getLayoutY(), topL.getRotate());
        //System.out.println(String.format("Left X: %f; Y: %f;\nBottom X: %f; Y: %f;\nRight X: %f; Y: %f;\nTop X: %f; Y: %f;\n\n", leftLineLayoutX, leftLineLayoutY, bottomLineLayoutX, bottomLineLayoutY, rightLineLayoutX, rightLineLayoutY, topLineLayoutX, topLineLayoutY));

        //TODO: CHANGING THE NUMBER OF STEPS BREAKS THE ANIMATION
        int numberOfSteps = 30;
        Map<String, List<Double>> lines = new HashMap<>();
        lines.put("left", new ArrayList<Double>() {{
            add(leftLineLayoutX);
            add(leftLineLayoutY);
        }});
        lines.put("right", new ArrayList<Double>() {{
            add(rightLineLayoutX);
            add(rightLineLayoutY);
        }});
        lines.put("top", new ArrayList<Double>() {{
            add(topLineLayoutX);
            add(topLineLayoutY);
        }});
        lines.put("bottom", new ArrayList<Double>() {{
            add(bottomLineLayoutX);
            add(bottomLineLayoutY);
        }});

        //waiting pane transition to finish
        while (threadPaneTransition.isAlive()) {
        }

        Thread threadBLA = new Thread(() -> {
            int timeMs = 25;
            try {
                for (int i = 0; i < 1; i--) {
                    //stops the animation when we switch to another pane
                    if (threadPaneTransition.isAlive()) {
                        return;
                    }

                    Thread.sleep(timeMs);
                    if (line1.getLayoutX() >= leftLineLayoutX && line1.getLayoutX() < bottomLineLayoutX && line1.getLayoutY() >= leftLineLayoutY && line1.getLayoutY() < bottomLineLayoutY) {
                        //left line go bottom, right line go top
                        double travelX = (bottomLineLayoutX - leftLineLayoutX) / numberOfSteps;
                        double travelRotate = 90 / numberOfSteps;
                        double travelY = (bottomLineLayoutY - leftLineLayoutY) / numberOfSteps;
                        double travelXSquare = (rightLineLayoutX - bottomLineLayoutX) / numberOfSteps;
                        double travelRotateSquare = -90 / numberOfSteps;
                        double travelYSquare = (rightLineLayoutY - bottomLineLayoutY) / numberOfSteps;

                        setLineCoordinatesAndRadiusAnimation(bottom, top, null, null, travelXSquare, travelRotateSquare, travelYSquare, false, lines);
                        travelRotate *= -1;
                        setLineCoordinatesAndRadiusAnimation(line1, line2, null, null, travelX, travelRotate, travelY, false, lines);

                        if (line1.getLayoutX() >= bottomLineLayoutX && line1.getLayoutX() < bottomLineLayoutX + travelX) {
                            setLineCoordinatesAndRadiusAnimation(line1, line2, bottom, top, bottomLineLayoutX, 90, bottomLineLayoutY, true, lines);
                            Thread.sleep(80);
                        }

                    } else if (line1.getLayoutX() >= bottomLineLayoutX && line1.getLayoutX() < rightLineLayoutX && line1.getLayoutY() <= bottomLineLayoutY && line1.getLayoutY() > rightLineLayoutY) {
                        //bottom line go right, top line go left
                        double travelX = (rightLineLayoutX - bottomLineLayoutX) / numberOfSteps;
                        double travelRotate = 90 / numberOfSteps;
                        double travelY = (rightLineLayoutY - bottomLineLayoutY) / numberOfSteps;
                        double travelXSquare = (topLineLayoutX - rightLineLayoutX) / numberOfSteps;
                        double travelRotateSquare = -90 / numberOfSteps;
                        double travelYSquare = (rightLineLayoutY - topLineLayoutY) / numberOfSteps;

                        setLineCoordinatesAndRadiusAnimation(bottom, top, null, null, travelXSquare, travelRotateSquare, travelYSquare, false, lines);
                        travelRotate *= -1;
                        setLineCoordinatesAndRadiusAnimation(line1, line2, null, null, travelX, travelRotate, travelY, false, lines);

                        if (line1.getLayoutX() >= rightLineLayoutX && line1.getLayoutX() < rightLineLayoutX + travelX) {
                            setLineCoordinatesAndRadiusAnimation(line1, line2, bottom, top, rightLineLayoutX, 180, rightLineLayoutY, true, lines);
                            Thread.sleep(80);
                        }
                    } else {
                        //SETTING DEFAULTS SO THE ANIMATION CAN START AGAIN
                        double line1X = line1.getLayoutX();
                        double line1Y = line1.getLayoutY();
                        double line3X = bottom.getLayoutX();
                        double line3Y = bottom.getLayoutY();
                        line1.setLayoutX(line2.getLayoutX());
                        line1.setLayoutY(line2.getLayoutY());
                        line2.setLayoutX(line1X);
                        line2.setLayoutY(line1Y);
                        bottom.setLayoutX(top.getLayoutX());
                        bottom.setLayoutY(top.getLayoutY());
                        top.setLayoutX(line3X);
                        top.setLayoutY(line3Y);
                        //Thread.sleep(100);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        threadBLA.start();
    }

    public void anchorPane_setDefaultOptions() {
        //sets all panes invisible (except the first one)
        for (Node child : anchorPane.getChildren()) {
            child.setVisible(false);
        }
        pane1.setVisible(true);
        System.out.println(anchorPane.getChildren());
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
                Thread t = new Thread(() -> {
                    try {
                        paneTransitionAnimation(pane1, pane2);
                        loadingAnimation(leftL, rightL, bottomL, topL, 375, 181, 45, 3);
                        controller.loginToIG(chooseAcc.getValue());
                        Thread.sleep(500);
                        paneTransitionAnimation(pane2, pane3);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
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
        button.setStyle(button.getStyle() + "-fx-background-color: #1e1e1e;");
    }

    @FXML
    private void button_onMouseExited(MouseEvent event) {
        Button button = (Button) event.getSource();
        button.setStyle(button.getStyle() + "-fx-background-color: #141414;");
    }

    @FXML
    private void button_onMousePressed(MouseEvent event) {
        Button button = (Button) event.getSource();
        button.setStyle(button.getStyle() + "-fx-background-color: #141414; -fx-text-fill: #94c487;");
    }

    @FXML
    private void button_onMouseReleased(MouseEvent event) {
        Button button = (Button) event.getSource();
        button.setStyle(button.getStyle() + "-fx-background-color: #1e1e1e; -fx-text-fill: #bdffae;");
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
