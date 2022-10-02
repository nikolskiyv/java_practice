import java.math.BigDecimal;
import java.util.Currency;

public class CurrencyExchangeRate {
    // Коэффициент конвертации.
    private final BigDecimal rate;

    private final Currency from, to;

    public CurrencyExchangeRate(BigDecimal rate, Currency from, Currency to) {
        // Коэффициент конвертации должен быть больше 0.
        if (rate.compareTo(new BigDecimal(0)) <= 0) {
            throw new IncorrectExchangeRateException("Rate must be must be greater than zero.");
        }

        this.rate = rate;
        this.from = from;
        this.to = to;
    }

    public Money convert(Money m) {
        // Проверяем, что переданной валюте соответствует rate.
        if (!this.from.getCurrencyCode().equals(m.getCurrency().getCurrencyCode())) {
            throw new DifferentCurrenciesException("Invalid currency.");
        }
        return new Money(this.to, m.getAmount().multiply(this.rate));
    }
}
