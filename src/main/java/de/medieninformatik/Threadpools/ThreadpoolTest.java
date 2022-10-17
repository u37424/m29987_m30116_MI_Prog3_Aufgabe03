package de.medieninformatik.Threadpools;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class ThreadpoolTest {
    public static void main(String[] args) {
        ComparableTestObject[] array = new ComparableTestObject[200];
        Random r = new Random();
        for (int i = 0; i < array.length; i++) {
            array[i] = new ComparableTestObject(r.nextInt(100));
        }

        ComparableTestObject[] check = Arrays.copyOf(array, array.length);

        //Sort
        final int nThreads = Runtime.getRuntime().availableProcessors();
        ForkJoinPool fjp = new ForkJoinPool(nThreads);
        RecursiveAction a = new MyAction(array);
        fjp.invoke(a);

        Arrays.parallelSort(check);
        boolean cmp = check.length == array.length;
        for (int i = 0; cmp && i < check.length; ++i) {
            cmp = check[i].compareTo(array[i]) == 0;
            System.out.println("A " + array[i].getValue() + " B:" + check[i].getValue());
        }
        System.out.printf(" Felder stimmen %s überein !", cmp ? "" : "nicht ");
    }
}
