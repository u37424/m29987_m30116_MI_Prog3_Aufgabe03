package de.medieninformatik.Threadpools;

public class NormalInsertionSort {
    public static <T extends Comparable<? super T>> void sort(T[] array, int start, int end) {
        T temp;
        for (int i = 1; i < array.length; i++) {
            temp = array[i];
            int j = i;
            while (j > 0 && array[j-1].compareTo(temp) > 0) {
                array[j] = array[j - 1];
                j--;
            }
            array[j] = temp;
        }
    }
}
