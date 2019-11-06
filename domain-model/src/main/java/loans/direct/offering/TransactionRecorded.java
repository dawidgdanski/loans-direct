package loans.direct.offering;

import loans.direct.TransactionDetails;
import lombok.Value;

@Value
public class TransactionRecorded {
    OfferId id;
    TransactionDetails details;
}
