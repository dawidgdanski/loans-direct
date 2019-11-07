package loans.direct.history;

import loans.direct.users.UsersService;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.Map;

@AllArgsConstructor
public class TransactionHistoryInMemRepository implements TransactionHistoryRepository {

    private final Map<HistoryKey, TransactionHistory> history;
    private final UsersService names;

    public TransactionHistory get(HistoryKey key) {
        return history.computeIfAbsent(key, this::create);
    }

    private TransactionHistory create(HistoryKey key) {
        return new TransactionHistory(
                key.getFrom(),
                names.getName(key.getTo()),
                new ArrayList<>()
        );
    }

    public void save(TransactionHistory history) {
        // already in map
    }
}
