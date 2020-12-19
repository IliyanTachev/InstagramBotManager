package repositories;

import entities.Account;

import java.util.ArrayList;
import java.util.List;

public class AccountsRepository {
    private List<Account> accounts;

    public AccountsRepository() {
        this.accounts = new ArrayList<>();
    }
}
