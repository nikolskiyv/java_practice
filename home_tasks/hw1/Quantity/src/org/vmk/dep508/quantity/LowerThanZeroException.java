package org.vmk.dep508.quantity;

public class LowerThanZeroException extends Exception {
    public LowerThanZeroException() { super("Number is lower than zero."); }
}