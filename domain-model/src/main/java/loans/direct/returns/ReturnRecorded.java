package loans.direct.returns;

import loans.direct.TransactionDetails;
import lombok.Value;

@Value
public class ReturnRecorded {
    ReturnId id;
    TransactionDetails details;
}
