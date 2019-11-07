package loans.direct.account;

import loans.direct.Money;
import lombok.AllArgsConstructor;

import java.util.Map;

@AllArgsConstructor
public class AccountInMemRepository implements AccountRepository {

    private final Map<AccountKey, Account> accounts;

    public Account get(AccountKey key) {
        return accounts.computeIfAbsent(key, this::create);
    }

    public void save(Account account) {
        // already in map
    }

    private Account create(AccountKey key) {
        return new Account(key.getFrom(), key.getTo(), Money.zero());
    }
}
