package loans.direct.offering;

import loans.direct.Money;
import loans.direct.UserId;
import lombok.AllArgsConstructor;

import java.time.Clock;
import java.time.Instant;

@AllArgsConstructor
class OfferNegotiation {
    private final OfferId id;
    private final UserId user;

    private OfferCreated initiated;
    private TransactionRecorded transaction;
    private Rejected reject;

    private final Clock clock;

    OfferCreated propose(UserId debtor, Money money) {
        offerNotInitiated();
        initiated = OfferCreated.byCreditorSide(id, Instant.now(clock))
                .details(user, debtor, money);
        return initiated;
    }

    OfferCreated ask(UserId creditor, Money money) {
        offerNotInitiated();
        initiated = OfferCreated.byDebitorSide(id, Instant.now(clock))
                .details(creditor, user, money);
        return initiated;
    }

    TransactionRecorded accept(UserId accepting) {
        offerActive();
        respondedByCounterParty(accepting);
        transaction = new TransactionRecorded(
                id,
                initiated.getDetails(Instant.now(clock))
        );
        return transaction;
    }

    Rejected reject(UserId rejecting) {
        offerActive();
        respondedByCounterParty(rejecting);
        reject = new Rejected(id, rejecting, Instant.now(clock));
        return reject;
    }

    Rejected expire() {
        offerActive();
        reject = new Rejected(id, UserId.system(), Instant.now(clock));
        return reject;
    }

    private void offerNotInitiated() {
        if (initiated != null) throw new IllegalStateException();
    }


    private void offerActive() {
        if (initiated == null) throw new IllegalStateException();
        if (transaction != null) throw new IllegalStateException();
        if (reject != null) throw new IllegalStateException();
    }

    private void respondedByCounterParty(UserId accepting) {
        if (!initiated.isCounterParty(accepting)) {
            throw new IllegalArgumentException();
        }
    }
}
