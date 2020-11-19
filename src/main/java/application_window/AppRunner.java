package application_window;

import functionalities.FTF;
import classes.DriverController;
import functionalities.impl.DefaultStrategy;
import org.openqa.selenium.WebDriver;

import java.io.FileNotFoundException;
import java.sql.Driver;
import java.util.Map;
import java.util.Scanner;

public class AppRunner {
    public static void main(String[] args) throws FileNotFoundException {
        DriverController driverController = new DriverController();
        driverController.loginToIG("phoneprincipal");

        GUI app = new GUI(driverController);
        app.constructApplication();
    }
}
