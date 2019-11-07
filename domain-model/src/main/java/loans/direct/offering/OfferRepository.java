package loans.direct.offering;

public interface OfferRepository {
    OfferNegotiation get(OfferId id);

    void save(OfferNegotiation negotiation);
}
