package loans.direct.offering;

import loans.direct.users.UsersService;
import lombok.AllArgsConstructor;

import java.time.Clock;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
public class OfferEventSourcingRepository implements OfferRepository {

    private final Map<OfferId, List<Object>> events;
    private final Clock clock;
    private final UsersService users;

    public OfferNegotiation get(OfferId id) {
        Map<Class<?>, Optional<Object>> last = fetchLastEvens(id);

        return new OfferNegotiation(
                id,
                users.logged(),
                get(last, OfferCreated.class),
                get(last, TransactionRecorded.class),
                get(last, Rejected.class),
                clock,
                new ArrayList<>());
    }

    public void save(OfferNegotiation negotiation) {
        events.computeIfAbsent(negotiation.id, i -> new ArrayList<>())
                .addAll(negotiation.events);
    }

    private Map<Class<?>, Optional<Object>> fetchLastEvens(OfferId id) {
        return events.computeIfAbsent(id, i -> new ArrayList<>())
                .stream()
                .collect(Collectors.groupingBy(
                        Object::getClass,
                        Collectors.reducing(((first, second) -> second))
                ));
    }

    private <T> T get(Map<Class<?>, Optional<Object>> last, Class<T> type) {
        return type.cast(last.get(type).orElse(null));
    }
}
