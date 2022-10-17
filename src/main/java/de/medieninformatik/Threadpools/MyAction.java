package de.medieninformatik.Threadpools;

import java.util.Arrays;
import java.util.concurrent.RecursiveAction;

/**
 * @author Luca Spirka m29987
 * @author Sebastian Siebert m30116
 * @version 1.0 <br>
 * 2022-10-10 <br>
 * Programmierung 3
 * Testat 3
 * <p>
 * Diese Klasse sortiert einen Array vergleichbarer Elemente mit einem parallelen Merge Sort, der in den Einzelfeldern <br>
 * bei einer festgelegten Grenze auf einen linearen InsertionSort wechselt.<br>
 */
public class MyAction<T extends Comparable<? super T>> extends RecursiveAction {

    T[] array;  //Array
    final int start, end;   //Obere und untere Grenze
    static final int SWITCH_SIZE = 25;  //Groesse ab der ein Feld mit InsertionSort sortiert werden soll.

    MyAction(T[] array) {
        this(array, 0, array.length);
    }

    MyAction(T[] array, int start, int end) {
        this.array = array;
        this.start = start;
        this.end = end;
    }

    /**
     * The main computation performed by this task.
     */
    protected void compute() {
        if (end - start < 2 || end- start <= SWITCH_SIZE)
            insertionSort(start, end);  //Wenn Feld kleiner als SWITCH_SIZE sind, dann InsertionSort
        else {
            int mid = (start + end) /2; //Teilen
            MyAction<T> l = new MyAction<>(array, start, mid);  //neue Recursive Actions fuer die Teilbereiche
            MyAction<T> r = new  MyAction<>(array, mid, end);
            l.fork();   //Abspalten
            r.compute();    //Berechnen
            l.join();   //Zusammenfuegen der Actions
            merge(start, mid, end); //Zusammenfuegen der Teilbereiche im Array
        }
    }

    /**
     * Sortiert einen Teilarray mit einer Groesse <= SWITCH_SIZE mit InsertionSort.
     * @param start Startindex des Feldes.
     * @param end Endindex des Feldes.
     */
    void insertionSort(int start, int end) {
        //Insertion Sort aus Prog1
            for (int i = start; i < end; i++) {
                T einfuegeWert = array[i];
                int lochPos = i;
                while (lochPos > start && einfuegeWert.compareTo(array[lochPos - 1]) < 0) {
                    array[lochPos] = array[lochPos - 1];
                    lochPos--;
                }
                array[lochPos] = einfuegeWert;
            }
    }

    /**
     * Verbindet zwei Teilbereiche eines groesseren Feldes im Array zu einem neuen und sortiertem.
     * @param lo Untere Grenze des Feldes.
     * @param mid Mittelpunkt des Feldes, wo das Feld getrennt ist.
     * @param hi Obere Grenze des Feldes.
     */
    void merge(int lo, int mid, int hi) {
        //Zusammenfuegen von beiden Teilfeldern
        T[] temp = Arrays.copyOfRange(array, lo, mid);
        for (int i = 0, j = lo, k = mid; i < temp.length; j++)
            array[j] = (k == hi || temp[i].compareTo(array[k]) < 0) ?
                    temp[i++] : array[k++];
    }

}