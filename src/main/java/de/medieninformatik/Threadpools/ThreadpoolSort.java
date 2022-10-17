package de.medieninformatik.Threadpools;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
/**
 * @author Luca Spirka m29987
 * @author Sebastian Siebert m30116
 * @version 1.0 <br>
 * 2022-10-10 <br>
 * Programmierung 3
 * Testat 3
 * <p>
 * Diese Klasse sortiert einen Array vergleichbarer Elemente mithilfe von ForkJoinPool und parallelem Merge Sort / normalen Insertion Sort.<br>
 * Der sortierte Array wird auf Korrektheit ueberprueft.
 */
public class ThreadpoolSort {
    /**
     * Ein Array wird mit vergleichbaren Objekten gefuellt und Mithilfe von ForkJoinPool, MergeSort und Insertion Sort sortiert.<br>
     * Danach wird der Array auf eine korrekte Sortierung ueberprueft.<br>
     * @param args User argumente.
     */
    public static void main(String[] args) {
        //Setup Liste
        ComparableTestObject[] array = new ComparableTestObject[200];
        Random r = new Random();

        for (int i = 0; i < array.length; i++) {
            array[i] = new ComparableTestObject(r.nextInt(100));
        }

        //Setup Check
        ComparableTestObject[] check = Arrays.copyOf(array, array.length);
        Arrays.parallelSort(check);

        //Setup Sort
        final int nThreads = Runtime.getRuntime().availableProcessors();
        ForkJoinPool fjp = new ForkJoinPool(nThreads);
        RecursiveAction a = new MyAction<>(array);

        //Sort
        fjp.invoke(a);

        //Check Sort
        boolean cmp = check.length == array.length;
        for (int i = 0; cmp && i < check.length; ++i) {
            cmp = check[i].compareTo(array[i]) == 0;
            System.out.println("A " + array[i].getValue() + " B:" + check[i].getValue());
        }
        System.out.printf("Felder stimmen %süberein!", cmp ? "" : "nicht ");
    }
}
