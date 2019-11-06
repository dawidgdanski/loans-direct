package loans.direct;

import lombok.Value;

@Value
public class Money {
    private static final Money ZERO = of(0);
    long amount;

    public static Money of(long amount) {
        return new Money(amount);
    }

    public static Money zero() {
        return ZERO;
    }

    public Money add(Money operand) {
        return Money.of(amount + operand.amount);
    }

    public Money reduce(Money other) {
        return of(Math.max(amount - other.amount, 0));
    }

    public Money subtract(Money operand) {
        return of(amount - operand.amount);
    }

    public boolean isNegative() {
        return amount < 0;
    }

    public Money negate() {
        return of(-amount);
    }

    @Override
    public String toString() {
        return String.valueOf(amount);
    }
}
