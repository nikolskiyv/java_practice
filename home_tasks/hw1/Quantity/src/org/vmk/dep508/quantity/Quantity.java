package org.vmk.dep508.quantity;

import jdk.nashorn.internal.runtime.regexp.joni.exception.ValueException;
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

    private void isMeasureValid(UnitOfMeasure other_measure) {
        if (this.measure.getBase() == null) {
            if (this.measure != other_measure &&
                    this.measure != other_measure.getBase()) {
                throw new ValueException("err");
            }
        }
        else {
            if (this.measure.getBase() != other_measure &&
                    this.measure.getBase() != other_measure.getBase()) {
                throw new ValueException("err");
            }
        }
    }

    public Quantity add(Quantity other) {
        isMeasureValid(other.measure);
        BigDecimal new_value = new BigDecimal(String.valueOf(
                this.value.multiply(this.measure.getCoeff())
                        .add(
                                other.value.multiply(other.getMeasure().getCoeff())
                        )
        ));
        UnitOfMeasure new_measure = (this.measure.getBase() == null) ? this.measure : this.measure.getBase();
        return new Quantity(new_value, new_measure);
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
