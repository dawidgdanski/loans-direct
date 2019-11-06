package loans.direct.history

import loans.direct.Money
import loans.direct.TransactionDetails
import loans.direct.UserId
import loans.direct.account.Operation
import spock.lang.Specification

import java.time.Instant

class TransactionHistoryTest extends Specification {

    def "01.11.2019 Maciek pożyczył od Ciebie kwotę 25 PLN, saldo po operacji 25 PLN"() {
        given:
        def history = emptyHistory()

        when:
        history.apply(loan(exampleUser(), exampleCounterParty(), money(25),
                balance(exampleCounterParty(), money(25))))

        then:
        history.getDescriptions() == ["Maciek pożyczył od Ciebie kwotę 25 PLN, saldo po operacji 25 PLN"]
    }

    def "02.11.2019 Maciek zwrócił Ci kwotę 20 PLN, saldo po operacji 5 PLN"() {
        given:
        def history = emptyHistory()

        when:
        history.apply(returns(exampleCounterParty(), exampleUser(), money(20),
                balance(exampleCounterParty(), money(5))))

        then:
        history.getDescriptions() == ["Maciek zwrócił Ci kwotę 20 PLN, saldo po operacji 5 PLN"]
    }

    def "03.11.2019 Maciek pożyczył Ci kwotę 15 PLN, saldo po operacji minus10 PLN"() {
        given:
        def history = emptyHistory()

        when:
        history.apply(loan(exampleCounterParty(), exampleUser(), money(15),
                balance(exampleUser(), money(10))))

        then:
        history.getDescriptions() == ["Maciek pożyczył Ci kwotę 15 PLN, saldo po operacji -10 PLN"]
    }

    def "04.11.2019 Maciek otrzymał od Ciebie kwotę 10 PLN, saldo po operacji 0 PLN"() {
        given:
        def history = emptyHistory()

        when:
        history.apply(returns(exampleUser(), exampleCounterParty(), money(10),
                Operation.Liabilities.zero()))

        then:
        history.getDescriptions() == ["Maciek otrzymał od Ciebie kwotę 10 PLN, saldo po operacji 0 PLN"]
    }

    def "Multiple messages"() {
        given:
        def history = emptyHistory()

        when:
        history.apply(loan(exampleUser(), exampleCounterParty(), money(25),
                balance(exampleCounterParty(), money(25))))
        history.apply(returns(exampleCounterParty(), exampleUser(), money(20),
                balance(exampleCounterParty(), money(5))))
        history.apply(loan(exampleCounterParty(), exampleUser(), money(15),
                balance(exampleUser(), money(10))))
        history.apply(returns(exampleUser(), exampleCounterParty(), money(10),
                Operation.Liabilities.zero()))

        then:
        history.getDescriptions() == [
                "Maciek pożyczył od Ciebie kwotę 25 PLN, saldo po operacji 25 PLN",
                "Maciek zwrócił Ci kwotę 20 PLN, saldo po operacji 5 PLN",
                "Maciek pożyczył Ci kwotę 15 PLN, saldo po operacji -10 PLN",
                "Maciek otrzymał od Ciebie kwotę 10 PLN, saldo po operacji 0 PLN"
        ]
    }


    Operation.Liabilities balance(UserId debtor, Money balance) {
        new Operation.Liabilities(debtor, balance)
    }

    Operation loan(UserId from, UserId to, Money amount, Operation.Liabilities balance) {
        new Operation(Operation.Kind.LOAN, details(from, to, amount), balance)
    }

    Operation returns(UserId from, UserId to, Money amount, Operation.Liabilities balance) {
        new Operation(Operation.Kind.RETURN, details(from, to, amount), balance)
    }

    private TransactionHistory emptyHistory() {
        new TransactionHistory(exampleUser(), exampleCounterPartyName(), new ArrayList<String>())
    }

    UserId exampleUser() {
        new UserId(2)
    }

    UserId exampleCounterParty() {
        new UserId(1)
    }

    String exampleCounterPartyName() {
        "Maciek"
    }

    def details(UserId creditor, UserId debtor, Money amount) {
        new TransactionDetails(Instant.now(), creditor, debtor, amount)
    }

    Money money(def amount) {
        new Money(amount)
    }
}
