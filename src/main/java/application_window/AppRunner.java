package application_window;

import functionalities.FTF;
import classes.BaseFunctionality;

import java.io.FileNotFoundException;
import java.util.Map;
import java.util.Scanner;

public class AppRunner {
    public static void main(String[] args) throws InterruptedException, FileNotFoundException {
//        executeFTF();
    }

    private static void executeFTF() throws FileNotFoundException {
        FTF FTF = new FTF("phoneprincipal");
        Map<String, String> result = FTF.getCategoriesByHashtag("#caseiphone");

        for(Map.Entry<String, String> e : result.entrySet()){
            System.out.println(e.getKey() + " -> " + e.getValue());
        }

        Scanner scanner = new Scanner(System.in);
        System.out.print("Select category: ");
        String category = "#" + scanner.next();
        BaseFunctionality.navigateToURL(result.get(category));
    }

}
