package classes;

import classes.Account;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class FileReader {
    private final File file;

    public FileReader(String filename){
        file = new File(filename);
    }

    public Account getAccountByName(String accountName) throws FileNotFoundException {
        Account account = getAllAccounts().stream().filter(acc -> acc.getName().equals(accountName)).collect(Collectors.toList()).get(0);
        return account;
    }

    private List<Account> getAllAccounts() throws FileNotFoundException {
        List<Account> accounts = new ArrayList<>();
        Scanner myReader = new Scanner(file);
        while (myReader.hasNextLine()) {
            String[] accountData = myReader.nextLine().split(",");
            accounts.add(new Account(accountData[0], accountData[1], accountData[2]));
        }

        return accounts;
    }
}
