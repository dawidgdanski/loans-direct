package loans.direct.offering

import loans.direct.UserId
import spock.lang.Specification

class OfferScenariosTest extends Specification implements OfferTesting {

    def "propose then accept"() {
        given:
        def offer = newOffer(exampleCreditor())

        when:
        def initiated = offer.propose(exampleDebiter(), money(20))

        then:
        initiated.creditor == exampleCreditor()
        initiated.debtor == exampleDebiter()
        initiated.isCounterParty(exampleDebiter())

        when:
        def transaction = offer.accept(exampleDebiter())

        then:
        transaction.details == details(exampleCreditor(), exampleDebiter(), money(20))
    }

    def "propose then reject"() {
        given:
        def offer = newOffer(exampleCreditor())

        when:
        def initiated = offer.propose(exampleDebiter(), money(20))

        then:
        initiated.creditor == exampleCreditor()
        initiated.debtor == exampleDebiter()
        initiated.isCounterParty(exampleDebiter())

        when:
        def reject = offer.reject(exampleDebiter())

        then:
        reject.rejecting == exampleDebiter()
    }

    def "propose then expire"() {
        given:
        def offer = newOffer(exampleCreditor())

        when:
        def initiated = offer.propose(exampleDebiter(), money(20))

        then:
        initiated.creditor == exampleCreditor()
        initiated.debtor == exampleDebiter()
        initiated.isCounterParty(exampleDebiter())

        when:
        def reject = offer.expire()

        then:
        reject.rejecting == UserId.system()
    }

    def "ask then accept"() {
        given:
        def offer = newOffer(exampleDebiter())

        when:
        def initiated = offer.ask(exampleCreditor(), money(20))

        then:
        initiated.creditor == exampleCreditor()
        initiated.debtor == exampleDebiter()
        initiated.isCounterParty(exampleCreditor())

        when:
        def transaction = offer.accept(exampleCreditor())

        then:
        transaction.details == details(exampleCreditor(), exampleDebiter(), money(20))
    }

    def "ask then reject"() {
        given:
        def offer = newOffer(exampleDebiter())

        when:
        def initiated = offer.ask(exampleCreditor(), money(20))

        then:
        initiated.creditor == exampleCreditor()
        initiated.debtor == exampleDebiter()
        initiated.isCounterParty(exampleCreditor())

        when:
        def reject = offer.reject(exampleCreditor())

        then:
        reject.rejecting == exampleCreditor()
    }

    def "ask then expire"() {
        given:
        def offer = newOffer(exampleDebiter())

        when:
        def initiated = offer.ask(exampleCreditor(), money(20))

        then:
        initiated.creditor == exampleCreditor()
        initiated.debtor == exampleDebiter()
        initiated.isCounterParty(exampleCreditor())

        when:
        def reject = offer.expire()

        then:
        reject.rejecting == UserId.system()
    }
}
