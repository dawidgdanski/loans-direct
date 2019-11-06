package loans.direct.account;

import loans.direct.Money;
import loans.direct.TransactionDetails;
import loans.direct.UserId;

import java.util.Set;

class Account {

    private final UserId from;
    private final UserId to;
    private Money balance;

    Account(UserId from, UserId to, Money balance) {
        this.from = from;
        this.to = to;
        this.balance = balance;
    }

    Balance process(TransactionDetails transaction) {
        rightAccountForUsers(transaction);
        if (from.equals(transaction.getFrom())) {
            balance = balance.add(transaction.getAmount());
        } else {
            balance = balance.subtract(transaction.getAmount());
        }
        return balance();
    }

    public Balance balance() {
        return new Balance(
                this.from,
                this.to,
                this.balance
        );
    }

    private void rightAccountForUsers(TransactionDetails transaction) {
        if (!Set.of(from, to).containsAll(Set.of(transaction.getFrom(), transaction.getTo()))) {
            throw new IllegalArgumentException();
        }
    }
}
