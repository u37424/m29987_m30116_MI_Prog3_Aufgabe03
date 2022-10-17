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

        //NormalMerge.mergeSort(array, 0, array.length-1);

        //Sort
        final int nThreads = Runtime.getRuntime().availableProcessors();
        ForkJoinPool fjp = new ForkJoinPool(nThreads);
        RecursiveAction a = new MyAction(array);
        fjp.invoke(a);

        for (ComparableTestObject i : array) {
            System.out.println(i.getValue());
        }
        for (int i = 1; i < array.length; i++) {
            if (!(array[i].compareTo(array[i - 1]) >= 0)) {
                System.out.println("Not sorted");
                break;
            }
        }
    }
}
