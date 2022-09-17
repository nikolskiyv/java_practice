package org.vmk.dep508;

/**
 * Created by VPerov on 14.09.2018.
 */
public class StringTest {

    public static void main(String[] args) {

        //strings are interned
        String a1 = "test";
        String a2 = "test";
        System.out.println(a1 == a2);
        System.out.println(a1.equals(a2));

        //string created as object
        String a3 = new String("test").intern();

        System.out.println(a1 == a3);
        System.out.println(a1.equals(a3));




    }

}
