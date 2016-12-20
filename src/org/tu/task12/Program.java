package org.tu.task12;

/**
 * Created by root on 12/21/16.
 */
public class Program {
    public static void main(String[] args) {
        ArrayClass arrayClass = new ArrayClass();

        Thread t1 = new Thread(() -> arrayClass.multiplyByTen());
        Thread t2 = new Thread(() -> arrayClass.sort());

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        arrayClass.printArray();
    }
}
