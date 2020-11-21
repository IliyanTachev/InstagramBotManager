package classes;

import functionalities.FTF;
import functionalities.impl.DefaultStrategy;

import java.io.FileNotFoundException;
import java.util.Map;
import java.util.Scanner;    

public final class Functions {
    public static void executeFTF(DriverController controller) throws FileNotFoundException {
        FTF FTF = new FTF(new DefaultStrategy(controller));
        Map<String, String> result = FTF.getCategoriesByHashtag("#caseiphone");

        for(Map.Entry<String, String> e : result.entrySet()){
            System.out.println(e.getKey() + " -> " + e.getValue());
        }

        Scanner scanner = new Scanner(System.in);
        System.out.print("Select category: ");
        String category = "#" + scanner.next();
        controller.navigateToURL(result.get(category));
    }
}
