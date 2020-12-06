package classes;

import classes.Account;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class FileController {
    private final File file;

    public FileController(String filename){
        file = new File(filename);
    }

    public Account getAccountByName(String accountName) throws FileNotFoundException {
        Account account = getAllAccounts().stream().filter(acc -> acc.getName().equals(accountName)).collect(Collectors.toList()).get(0);
        return account;
    }

    public List<Account> getAllAccounts() throws FileNotFoundException {
        List<Account> accounts = new ArrayList<>();
        Scanner myReader = new Scanner(file);
        while (myReader.hasNextLine()) {
            String[] accountData = myReader.nextLine().split(",");
            accounts.add(new Account(accountData[0], accountData[1], accountData[2]));
        }

        return accounts;
    }

    public List<String> getAllData(){
        List<String> data = new ArrayList<>();
        Scanner myReader = null;
        try {
            myReader = new Scanner(file);
        } catch (FileNotFoundException e) {
            System.out.println("File cannot be found.");
        }
        while (myReader.hasNextLine()) {
            data.add(myReader.nextLine());
        }
        return data;
    }

    public void putData(String data) throws IOException {
        FileWriter fileWriter = new FileWriter(file);
        fileWriter.write(data);
    }
}
