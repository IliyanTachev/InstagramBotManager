import funcs.FTF;
import funcs.Functionality;

import java.io.FileNotFoundException;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws InterruptedException, FileNotFoundException {
        FTF FTF = new FTF("phoneprincipal");
        Map<String, String> result = FTF.getCategoriesByHashtag("#caseiphone");

        for(Map.Entry<String, String> e : result.entrySet()){
            System.out.println(e.getKey() + " -> " + e.getValue());
        }

        Scanner scanner = new Scanner(System.in);
        System.out.print("Select category: ");
        String category = "#" + scanner.next();
        Functionality.navigateTo(result.get(category));

    }
}
