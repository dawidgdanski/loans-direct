package loans.direct.account;

import loans.direct.Money;
import loans.direct.TransactionDetails;
import loans.direct.UserId;
import lombok.Value;

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
    }
}
