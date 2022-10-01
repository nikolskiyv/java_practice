package org.vmk.dep508.quantity;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class QuantityTest {
    private Quantity KG10, G100, T15, M10, CM100, KM15;
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
        System.out.println("tearDown");
    }

    @Test
    public void add() throws Exception {
        Quantity res;

        res = KG10.add(G100);
        assertEquals(res.getValue(), new BigDecimal("10.100"));

        res = KG10.add(T15);
        assertEquals(res.getValue(), new BigDecimal("15010.000"));

    }

    @Test
    public void subtract() throws Exception {
    }

    @Test
    public void multiply() throws Exception {
    }

    @Test
    public void devide() throws Exception {
    }

}