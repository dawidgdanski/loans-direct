package loans.direct.offering

import loans.direct.Money
import loans.direct.TransactionDetails
import loans.direct.UserId

import java.time.Clock
import java.time.Instant
import java.time.ZoneId

trait OfferTesting {

    Clock clock = Clock.fixed(Instant.now(), ZoneId.systemDefault())

    OfferNegotiation newOffer(UserId initiating) {
        new OfferNegotiation(someOfferId(), initiating, null, null, null, clock)
    }

    OfferId someOfferId() {
        new OfferId(1)
    }

    UserId exampleCreditor() {
        new UserId(1)
    }

    UserId exampleDebiter() {
        new UserId(2)
    }

    def details(UserId creditor, UserId debtor, Money amount) {
        new TransactionDetails(Instant.now(clock), creditor, debtor, amount)
    }

    Money money(def amount) {
        new Money(amount)
    }
}
