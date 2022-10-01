package org.vmk.dep508.cer;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;

public class Main {
    public static void main(String[] args) {
        // Создаем экземпляры валют.
        Currency usd = Currency.getInstance("USD");
        Currency gbp = Currency.getInstance("GBP");

        // Создаем экземпляры валют с их значениями.
        Money usdMoney = new Money(usd, new BigDecimal("100"));
        Money tenDollars = new Money(usd, new BigDecimal("10"));
        Money tenPound = new Money(gbp, new BigDecimal("10"));

        // Конвертируем фунты в доллары с заданным коэффициентом.
        CurrencyExchangeRate poundToUsd = new CurrencyExchangeRate(new BigDecimal("1.5"), gbp, usd);

        // Тест сложения.
        usdMoney = usdMoney.add(tenDollars);
        System.out.println(usdMoney.getAmount());
        System.out.println(usdMoney.getAmount().equals(new BigDecimal("110").setScale(2, RoundingMode.HALF_UP)));

        try {
            // Пытаемся производить операции с разными валютами -> ловим исключение.
            usdMoney = usdMoney.subtract(tenPound);
        } catch(DifferentCurrenciesException ex) {
            System.out.println("DifferentCurrenciesException thrown");
        }

        // Тест вычитания.
        usdMoney = usdMoney.subtract(poundToUsd.convert(tenPound));
        System.out.println(usdMoney.getAmount());
        System.out.println(usdMoney.getAmount().equals(new BigDecimal("95").setScale(2, RoundingMode.HALF_UP)));

        // Тест умножения.
        usdMoney = usdMoney.multiply(new BigDecimal("2"));
        System.out.println(usdMoney.getAmount());
        System.out.println(usdMoney.getAmount().equals(new BigDecimal("190").setScale(2, RoundingMode.HALF_UP)));

        // Тест деления.
        usdMoney = usdMoney.devide(new BigDecimal("3"));
        System.out.println(usdMoney.getAmount());
        System.out.println(usdMoney.getAmount().equals(new BigDecimal("63.333333").setScale(2, RoundingMode.HALF_UP)));
    }
}
