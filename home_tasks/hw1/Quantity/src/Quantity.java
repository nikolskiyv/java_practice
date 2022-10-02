import com.sun.corba.se.impl.io.TypeMismatchException;

import java.math.BigDecimal;
import java.math.RoundingMode;

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
                throw new TypeMismatchException(String.format("Operation cannot be performed on %s and %s", this.measure, other_measure));
            }
        }
        else {
            if (this.measure.getBase() != other_measure &&
                    this.measure.getBase() != other_measure.getBase()) {
                throw new TypeMismatchException(String.format("Operation cannot be performed on %s and %s", this.measure, other_measure));
            }
        }
    }

    private BigDecimal castValueToBaseType(Quantity quantity) {
        return quantity.value.multiply(quantity.measure.getCoeff());
    }

    public Quantity add(Quantity other) {
        // Проверяем, что типы можно складывать
        isMeasureValid(other.measure);

        BigDecimal new_value = new BigDecimal(
                castValueToBaseType(this).add(castValueToBaseType(other))
                        .stripTrailingZeros().toPlainString()
        );
        UnitOfMeasure new_measure = (this.measure.getBase() == null) ? this.measure : this.measure.getBase();
        return new Quantity(new_value, new_measure);
    }

    public Quantity subtract(Quantity other) throws LowerThanZeroException {
        // Проверяем, что типы можно вычитать
        isMeasureValid(other.measure);

        BigDecimal new_value = new BigDecimal(
                castValueToBaseType(this).subtract(castValueToBaseType(other))
                        .stripTrailingZeros().toPlainString()
        );

        if (new_value.compareTo(BigDecimal.ZERO) < 0) {
            throw new LowerThanZeroException();
        }

        UnitOfMeasure new_measure = (this.measure.getBase() == null) ? this.measure : this.measure.getBase();
        return new Quantity(new_value, new_measure);
    }

    public Quantity multiply(BigDecimal ratio) throws LowerThanZeroException {
        if (ratio.compareTo(BigDecimal.ZERO) < 0) {
            throw new LowerThanZeroException();
        }

        BigDecimal new_value = new BigDecimal(
                castValueToBaseType(this).multiply(ratio)
                        .stripTrailingZeros().toPlainString()
        );

        UnitOfMeasure new_measure = (this.measure.getBase() == null) ? this.measure : this.measure.getBase();
        return new Quantity(new_value, new_measure);
    }

    public Quantity divide(BigDecimal ratio) throws ZeroDivisionException, LowerThanZeroException {
        if (ratio.compareTo(BigDecimal.ZERO) == 0) {
            throw new ZeroDivisionException();
        }

        if (ratio.compareTo(BigDecimal.ZERO) < 0) {
            throw new LowerThanZeroException();
        }

        BigDecimal new_value = new BigDecimal(
                castValueToBaseType(this).divide(ratio, 3, RoundingMode.HALF_UP)
                        .stripTrailingZeros().toPlainString()
        );

        UnitOfMeasure new_measure = (this.measure.getBase() == null) ? this.measure : this.measure.getBase();
        return new Quantity(new_value, new_measure);
    }

    public String toString() {
        return value + " " + measure;
    }

}
