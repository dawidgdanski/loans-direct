package loans.direct.offering;

import loans.direct.UserId;
import lombok.Value;

import java.time.Instant;

@Value
public class Rejected {
    OfferId id;
    UserId rejecting;
    Instant timestamp;
}
