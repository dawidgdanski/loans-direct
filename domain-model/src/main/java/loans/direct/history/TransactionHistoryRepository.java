package loans.direct.history;

public interface TransactionHistoryRepository {
    TransactionHistory get(HistoryKey key);

    void save(TransactionHistory history);
}
