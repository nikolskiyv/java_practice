package org.vmk.dep508.quantity;

import java.math.BigDecimal;

public enum UnitOfMeasure {
    KG(BigDecimal.ONE, null),  // килограмм
    G(new BigDecimal("0.001"), KG),  // грамм
    T(new BigDecimal("1000.000"), KG),  // тонна

    M(BigDecimal.ONE, null),  // метр
    KM(new BigDecimal("1000.000"), M),  // миллиметр
    CM(new BigDecimal("0.01"), M)  // сантиметр
    ;

    private BigDecimal coeff;
    private UnitOfMeasure base;

    UnitOfMeasure(BigDecimal coeff, UnitOfMeasure base) {
        this.coeff = coeff;
        this.base = base;
    }

    public BigDecimal getCoeff() {
        return coeff;
    }

    public UnitOfMeasure getBase() {
        return base;
    }
}
