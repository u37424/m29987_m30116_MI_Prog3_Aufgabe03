package de.medieninformatik.Threadpools;

import java.util.ArrayList;
import java.util.concurrent.RecursiveAction;

public class MyAction<T extends Comparable<? super T>> extends RecursiveAction {

    private final int SWITCH_SIZE = 25;

    private volatile T[] array;
    private final int start;
    private final int end;

    public MyAction(T[] array) {
        this(array, 0, array.length - 1);
    }

    public MyAction(T[] array, int start, int end) {
        this.array = array;
        this.start = start;
        this.end = end;
    }

    /**
     * The main computation performed by this task.
     */
    @Override
    protected void compute() { // base case
        if (start < end) {
            // find the middle point
            int middle = (start + end) / 2;
            if ((end + 1) - start <= SWITCH_SIZE) {
                NormalInsertionSort.sort(array, start, end);
            } else {
                MyAction l = new MyAction<>(array, start, middle); // sort first half
                MyAction r = new MyAction<>(array, middle +1, end);  // sort second half
                r.fork();
                l.compute();
                r.join();
                merge(array, start, middle, end);
            }

            // if (!((end+1) - start <= SWITCH_SIZE)) merge(array, start, middle, end);
        }
    }

    static <T extends Comparable<? super T>> void merge(T[] array, int start, int middle, int end) {
        ArrayList<T> leftArray = new ArrayList<>(middle - start + 1);
        ArrayList<T> rightArray = new ArrayList<>(end - middle);
        // fill in left array
        int len = middle - start + 1;
        for (int i = 0; i < len; ++i)
            leftArray.add(i, array[start + i]);

        // fill in right array
        len = end - middle;
        for (int i = 0; i < len; ++i)
            rightArray.add(i, array[middle + 1 + i]);

        /* Merge the temp arrays */

        // initial indexes of first and second subarrays
        int leftIndex = 0, rightIndex = 0;

        // the index we will start at when adding the subarrays back into the main array
        int currentIndex = start;

        // compare each index of the subarrays adding the lowest value to the currentIndex
        while (leftIndex < leftArray.size() && rightIndex < rightArray.size()) {
            if (leftArray.get(leftIndex).compareTo(rightArray.get(rightIndex)) <= 0) {
                array[currentIndex] = leftArray.get(leftIndex);
                leftIndex++;
            } else {
                array[currentIndex] = rightArray.get(rightIndex);
                rightIndex++;
            }
            currentIndex++;
        }

        // copy remaining elements of leftArray[] if any
        while (leftIndex < leftArray.size()) array[currentIndex++] = leftArray.get(leftIndex++);

        // copy remaining elements of rightArray[] if any
        while (rightIndex < rightArray.size()) array[currentIndex++] = rightArray.get(rightIndex++);
    }
}
