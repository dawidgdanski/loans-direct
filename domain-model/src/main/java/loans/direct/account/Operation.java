package loans.direct.account;

import loans.direct.Money;
import loans.direct.TransactionDetails;
import loans.direct.UserId;
import lombok.Value;

import java.util.Objects;

@Value
public class Operation {

    Kind kind;
    TransactionDetails details;
    Liabilities balance;

    public enum Kind {
        LOAN, RETURN
    }

    @Value
    public static class Liabilities {
        UserId debtor;
        Money amount;

        public static Liabilities zero() {
            return new Liabilities(null, Money.zero());
        }

        public Money perspectiveOf(UserId user) {
            return Objects.equals(debtor, user) ? amount.negate() : amount;
        }
    }
}
