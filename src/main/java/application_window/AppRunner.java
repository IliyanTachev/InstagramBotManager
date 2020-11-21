package application_window;

import classes.DriverController;
import classes.Functions;

import java.io.FileNotFoundException;

public class AppRunner {
    public static void main(String[] args) throws FileNotFoundException {
        DriverController driverController = new DriverController();
        driverController.loginToIG("phoneprincipal");
        Functions.executeFTF(driverController);

//        GUI app = new GUI(driverController);
//        app.constructApplication();
    }
}
