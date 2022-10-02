import com.sun.corba.se.impl.io.TypeMismatchException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class QuantityTest {
    private Quantity KG10, G100, T15, M10, CM100, KM15;
    private Quantity res;

    @Before
    public void setUp() throws Exception {
        KG10 = new Quantity(new BigDecimal("10"), UnitOfMeasure.KG);
        G100 = new Quantity(new BigDecimal("100"), UnitOfMeasure.G);
        T15 = new Quantity(new BigDecimal("15"), UnitOfMeasure.T);
        M10 = new Quantity(new BigDecimal("10"), UnitOfMeasure.M);
        CM100 = new Quantity(new BigDecimal("100"), UnitOfMeasure.CM);
        KM15 = new Quantity(new BigDecimal("15"), UnitOfMeasure.KM);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void add() throws Exception {
        res = KG10.add(G100);
        assertEquals(res.getValue(), new BigDecimal("10.1"));

        res = KG10.add(T15);
        assertEquals(res.getValue(), new BigDecimal("15010"));

        try {
            KG10.add(M10);
        } catch (TypeMismatchException e) {
            assertNotEquals("", e.getMessage());
        }

        res = CM100.add(KM15);
        assertEquals(res.getValue(), new BigDecimal("15001"));

    }

    @Test
    public void subtract() throws Exception {
        res = KG10.subtract(G100);
        assertEquals(res.getValue(), new BigDecimal("9.9"));

        res = T15.subtract(G100);
        assertEquals(res.getValue(), new BigDecimal("14999.9"));

        try {
            KG10.subtract(M10);
        } catch (TypeMismatchException e) {
            assertNotEquals("", e.getMessage());
        }

        try {
            G100.subtract(T15);
        } catch (LowerThanZeroException e) {
            assertNotEquals("", e.getMessage());
        }
    }

    @Test
    public void multiply() throws Exception {
        res = KG10.multiply(new BigDecimal("0"));
        assertEquals(res.getValue(), new BigDecimal("0"));

        res = KG10.multiply(new BigDecimal("2"));
        assertEquals(res.getValue(), new BigDecimal("20"));

        try {
            G100.multiply(new BigDecimal("-1"));
        } catch (LowerThanZeroException e) {
            assertNotEquals("", e.getMessage());
        }
    }

    @Test
    public void divide() throws Exception {
        res = KG10.divide(new BigDecimal("3"));
        assertEquals(res.getValue(), new BigDecimal("3.333"));

        try {
            G100.divide(new BigDecimal("0"));
        } catch (ZeroDivisionException e) {
            assertNotEquals("", e.getMessage());
        }

        try {
            G100.divide(new BigDecimal("-1"));
        } catch (LowerThanZeroException e) {
            assertNotEquals("", e.getMessage());
        }
    }

}