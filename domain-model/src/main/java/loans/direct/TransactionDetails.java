package loans.direct;

import lombok.Value;

import java.time.Instant;

@Value
public class TransactionDetails {
    Instant timestamp;
    UserId from;
    UserId to;
    Money amount;
}
