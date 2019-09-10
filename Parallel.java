/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.Scanner;
import java.util.concurrent.ForkJoinPool;

/**
 *
 * @author bhavm
 */
public class Parallel {
    static final ForkJoinPool jkp =new ForkJoinPool();
    
    public static void main(String[] args){
        Scanner in = new Scanner(System.in);
        System.out.println("Parallel programming: ");
        System.out.print("Enter 'input_file_name.txt' and 'output_file_name.txt' (separated by a space): ");
        String input = in.next();
        String output = in.next();
        CloudData cd = new CloudData();
        cd.readData(input);
        System.gc();
        long start = System.currentTimeMillis();
        Vector v = jkp.invoke(new MakeParallel(0, cd.dim(),cd));
        long diff = System.currentTimeMillis() - start;
        System.out.println("Runtime: " + diff + " milliseconds");
        cd.writeData(output, v);        
        
    }
}
