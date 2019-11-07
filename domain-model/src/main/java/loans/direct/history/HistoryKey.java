package loans.direct.history;

import loans.direct.TransactionDetails;
import loans.direct.UserId;
import lombok.Value;

@Value
class HistoryKey {
    private UserId from;
    private UserId to;

    static HistoryKey of(TransactionDetails transaction) {
        return new HistoryKey(transaction.getFrom(), transaction.getTo());
    }

    HistoryKey reverse() {
        return new HistoryKey(to, from);
    }
}
