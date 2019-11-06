package loans.direct.offering

import spock.lang.Specification

class OfferInvariantViolationsTest extends Specification implements OfferTesting {

    def "ask twice"() {
        given:
        def offer = newOffer()

        when:
        def initiated = offer.ask(exampleCreditor(), money(15))
        offer.ask(exampleCreditor(), money(25))

        then:
        initiated != null
        thrown(IllegalStateException)
    }

    def "propose twice"() {
        given:
        def offer = newOffer()

        when:
        def initiated = offer.propose(exampleDebiter(), money(15))
        offer.propose(exampleDebiter(), money(25))

        then:
        initiated != null
        thrown(IllegalStateException)
    }

    def "ask then propose"() {
        given:
        def offer = newOffer()

        when:
        def initiated = offer.ask(exampleDebiter(), money(15))
        offer.propose(exampleDebiter(), money(25))

        then:
        initiated != null
        thrown(IllegalStateException)
    }

    def "accept none initiated"() {
        given:
        def offer = newOffer()

        when:
        offer.accept(exampleDebiter())

        then:
        thrown(IllegalStateException)
    }

    def "accept with wrong user"() {
        given:
        def offer = newOffer()
        def initiated = offer.ask(exampleCreditor(), money(15))

        when:
        offer.accept(exampleDebiter())

        then:
        thrown(IllegalArgumentException)
    }

    def "accept rejected"() {
        given:
        def offer = newOffer()
        def initiated = offer.ask(exampleCreditor(), money(15))

        when:
        def reject = offer.reject(exampleCreditor())
        offer.accept(exampleCreditor())

        then:
        reject != null
        thrown(IllegalStateException)
    }

    def "accept twice"() {
        given:
        def offer = newOffer()
        def initiated = offer.ask(exampleCreditor(), money(15))

        when:
        def accept = offer.accept(exampleCreditor())
        offer.accept(exampleCreditor())

        then:
        accept != null
        thrown(IllegalStateException)
    }

    def "reject accepted"() {
        given:
        def offer = newOffer()
        def initiated = offer.ask(exampleCreditor(), money(15))

        when:
        def accept = offer.accept(exampleCreditor())
        offer.reject(exampleCreditor())

        then:
        accept != null
        thrown(IllegalStateException)
    }

    def "reject twice"() {
        given:
        def offer = newOffer()
        def initiated = offer.ask(exampleCreditor(), money(15))

        when:
        def reject = offer.reject(exampleCreditor())
        offer.reject(exampleCreditor())

        then:
        reject != null
        thrown(IllegalStateException)
    }
}
