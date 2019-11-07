package loans.direct.offering;

import loans.direct.Money;
import loans.direct.UserId;
import loans.direct.account.AccountService;
import loans.direct.account.Balance;
import loans.direct.account.Operation;
import loans.direct.history.TransactionHistoryService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class OfferService {

    private final OfferRepository repository;
    private final AccountService accounting;
    private final TransactionHistoryService history;

    public OfferCreated propose(OfferId id, UserId debtor, Money money) {
        OfferNegotiation negotiation = repository.get(id);
        OfferCreated created = negotiation.propose(debtor, money);
        repository.save(negotiation);
        return created;
    }

    public OfferCreated ask(OfferId id, UserId creditor, Money money) {
        OfferNegotiation negotiation = repository.get(id);
        OfferCreated created = negotiation.ask(creditor, money);
        repository.save(negotiation);
        return created;
    }

    public TransactionRecorded accept(OfferId id, UserId accepting) {
        OfferNegotiation negotiation = repository.get(id);
        TransactionRecorded transaction = negotiation.accept(accepting);
        repository.save(negotiation);
        Balance balance = accounting.process(transaction.getDetails());
        Operation operation = Operation.loan(transaction.getDetails(), balance);
        history.apply(operation);
        return transaction;
    }

    public Rejected reject(OfferId id, UserId rejecting) {
        OfferNegotiation negotiation = repository.get(id);
        Rejected reject = negotiation.reject(rejecting);
        repository.save(negotiation);
        return reject;
    }

}
