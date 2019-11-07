package loans.direct.account;

import loans.direct.TransactionDetails;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AccountService {

    private final AccountRepository repository;

    public Balance process(TransactionDetails transaction) {
        Account account = repository.get(AccountKey.of(transaction));
        Balance balance = account.process(transaction);
        repository.save(account);
        return balance;
    }
}
