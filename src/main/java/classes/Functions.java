package classes;

import functionalities.ftf.FTF;
import functionalities.ftf.strategies.DefaultStrategy;
import java.io.FileNotFoundException;

public final class Functions {
    public static void executeFTF(DriverController controller) throws FileNotFoundException {
        FTF FTF = new FTF(new DefaultStrategy(controller));
    }
}
