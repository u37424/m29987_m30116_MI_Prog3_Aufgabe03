package de.medieninformatik.Threadpools;

import java.util.Arrays;
import java.util.concurrent.RecursiveAction;

public class MyAction<T extends Comparable<? super T>> extends RecursiveAction {

    T[] array;
    final int start, end;
    static final int SWITCH_SIZE =10;

    MyAction(T[] array, int start, int end) {
        this.array = array;
        this.start = start;
        this.end = end;
    }

    MyAction(T[] array) {
        this(array, 0, array.length);
    }

    protected void compute() {
        if (end - start < 2 || end- start <= SWITCH_SIZE)
            insertionSort(start, end);
        else {
            int mid = (start + end) /2;
            MyAction<T> l = new MyAction<>(array, start, mid);
            MyAction<T> r = new  MyAction<>(array, mid, end);
            l.fork();
            r.compute();
            l.join();
            merge(start, mid, end);
        }
    }

    void insertionSort(int start, int end) {
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

    void merge(int lo, int mid, int hi) {
        T[] temp = Arrays.copyOfRange(array, lo, mid);
        for (int i = 0, j = lo, k = mid; i < temp.length; j++)
            array[j] = (k == hi || temp[i].compareTo(array[k]) < 0) ?
                    temp[i++] : array[k++];
    }

}