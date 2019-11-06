package loans.direct.history;

import loans.direct.UserId;
import loans.direct.account.Operation;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
public class TransactionHistory {

    private final UserId user;
    private final String counterPartyName;
    private final List<String> descriptions;

    private static final Map<Operation.Kind, String> fromMe = Map.of(
            Operation.Kind.LOAN, "%s pożyczył od Ciebie kwotę %s PLN, saldo po operacji %s PLN",
            Operation.Kind.RETURN, "%s otrzymał od Ciebie kwotę %s PLN, saldo po operacji %s PLN"
    );
    private static final Map<Operation.Kind, String> toMe = Map.of(
            Operation.Kind.RETURN, "%s zwrócił Ci kwotę %s PLN, saldo po operacji %s PLN",
            Operation.Kind.LOAN, "%s pożyczył Ci kwotę %s PLN, saldo po operacji %s PLN"
    );

    void apply(Operation operation) {
        boolean fromMe = operation.getDetails().getFrom().equals(user);
        Operation.Kind kind = operation.getKind();
        String template = fromMe ? this.fromMe.get(kind) : this.toMe.get(kind);
        String operationTitle = String.format(template,
                counterPartyName,
                operation.getDetails().getAmount(),
                operation.getBalance().perspectiveOf(user)
        );
        descriptions.add(operationTitle);
    }

    List<String> getDescriptions() {
        return descriptions;
    }
}
