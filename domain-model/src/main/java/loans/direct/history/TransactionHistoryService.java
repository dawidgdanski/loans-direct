package loans.direct.history;

import loans.direct.account.Operation;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class TransactionHistoryService {

    private final TransactionHistoryRepository repository;

    public void apply(Operation operation) {
        HistoryKey key = HistoryKey.of(operation.getDetails());
        update(operation, key);
        update(operation, key.reverse());
    }

    public List<String> getDescriptions(HistoryKey key) {
        TransactionHistory history = repository.get(key);
        return history.getDescriptions();
    }

    private void update(Operation operation, HistoryKey key) {
        TransactionHistory history = repository.get(key);
        history.apply(operation);
        repository.save(history);
    }
}
