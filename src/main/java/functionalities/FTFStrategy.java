package functionalities;

import classes.DriverController;
import org.openqa.selenium.WebDriver;

public abstract class FTFStrategy {
    protected DriverController driverController;
    public FTFStrategy(DriverController driver){
        driverController = driver;
    }

    public abstract void execute();
}
