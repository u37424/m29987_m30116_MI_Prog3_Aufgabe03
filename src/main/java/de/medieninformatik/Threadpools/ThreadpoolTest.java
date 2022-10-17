package de.medieninformatik.Threadpools;

import java.util.Random;

public class ThreadpoolTest {
    public static void main(String[] args) {
        ComparableTestObject[] array = new ComparableTestObject[20];
        Random r = new Random();
        for (int i = 0; i < array.length; i++) {
            array[i] = new ComparableTestObject(r.nextInt());
        }
        for (ComparableTestObject i : array) {
            System.out.println(i.getValue());
        }
    }
}
