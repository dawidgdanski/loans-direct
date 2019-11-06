package loans.direct.account;

import loans.direct.Money;
import loans.direct.UserId;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Balance {
    private final UserId from;
    private final UserId to;
    private final Money balance;

    public Money of(UserId side) {
        if (from.equals(side)) {
            return balance;
        } else {
            return balance.negate();
        }
    }

    public Operation.Liabilities liabilities() {
        if (balance.equals(Money.zero())) {
            return Operation.Liabilities.zero();
        }
        if (balance.isNegative()) {
            return new Operation.Liabilities(from, balance.negate());
        } else {
            return new Operation.Liabilities(to, balance);
        }
    }
}
