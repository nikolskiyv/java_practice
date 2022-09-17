package org.vmk.dep508;

/*
Multiline
comment
example
tet
*/

import java.util.Scanner;

public class Main {
	
	int a;
	public int getA(){return a;}
	
    public static void main(String[] args){

        //Iterating through input argumets
        for(String arg : args) {
            System.out.println(arg);
        }

        String greating = System.getProperty("org.vmk.dep508.greating");
        if(greating != null) {
            System.out.println(greating);
        } else {
			System.out.println("Nothing");
		}

        //reading from console input
        Scanner s = new Scanner(System.in);
        System.out.println("Enter something:");
        while(s.hasNext()) {
            String line = s.nextLine();
            System.out.println(line);
            if(line.equals("exit")) {
                break;
            }
        }
    }
}
