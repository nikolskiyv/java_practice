package org.vmk.dep508.quantity;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.math.BigDecimal;

public class Quantity {
    private BigDecimal value;
    private UnitOfMeasure measure;

    public Quantity(BigDecimal value, UnitOfMeasure measure) {
        this.value = value;
        this.measure = measure;
    }

    public BigDecimal getValue() {
        return value;
    }

    public UnitOfMeasure getMeasure() {
        return measure;
    }

    public Quantity add(Quantity other) {
        throw new NotImplementedException();
    }

    public Quantity subtract(Quantity other) {
        throw new NotImplementedException();
    }

    public Quantity multiply(BigDecimal ratio) {
        throw new NotImplementedException();
    }

    public Quantity devide(BigDecimal ratio) {
        throw new NotImplementedException();
    }

    public String toString() {
        return value + " " + measure;
    }

}
