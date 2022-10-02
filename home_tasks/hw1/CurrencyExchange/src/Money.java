import jdk.nashorn.internal.runtime.regexp.joni.exception.ValueException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;

public class Money {
    private final Currency currency;
    private final BigDecimal amount;

    public Money(Currency currency, BigDecimal amount) {
        this.currency = currency;
        this.amount = amount.setScale(this.currency.getDefaultFractionDigits(), RoundingMode.HALF_UP);
    }

    public Currency getCurrency() {
        return currency;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Money add(Money m) {
        // Недопустимо складывать разные валюты
        if (this.currency != m.getCurrency()) {
            throw new DifferentCurrenciesException("You can not add money of different currencies.");
        }
        return new Money(this.currency, this.amount.add(m.getAmount()));
    }

    public Money subtract(Money m) {
        // Недопустимо вычитать разные валюты
        if (this.currency != m.getCurrency()) {
            throw new DifferentCurrenciesException("You can not substract money of different currencies.");
        }
        return new Money(this.currency, this.amount.subtract(m.getAmount()));
    }

    public Money multiply(BigDecimal ratio) {
        if (ratio.compareTo(new BigDecimal(0)) < 0) {
            throw new ValueException("Multiplier cannot be less than zero.");
        }
        return new Money(this.currency, this.amount.multiply(ratio));
    }

    public Money devide(BigDecimal ratio) {
        if (ratio.compareTo(new BigDecimal(0)) <= 0) {
            throw new ValueException("Divisor can not be less or equal then zero.");
        }
        return new Money(this.currency, this.amount.divide(ratio, RoundingMode.HALF_UP));
    }
}
