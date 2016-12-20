package org.tu.task12;

import java.util.concurrent.ThreadLocalRandom;

public class ArrayClass {
    private static final int ArraySize = 100;
    private int[] array = new int[ArraySize];

    public ArrayClass() {
        for (int i = 0; i < ArraySize; i++) {
            array[i] = ThreadLocalRandom.current().nextInt(0, 500);
        }
    }

    public synchronized void multiplyByTen() {
        for (int i = 0; i < ArraySize; i++) {
            array[i] *= 10;
        }
    }

    public synchronized void sort() {
        quickSort(this.array, 0, ArraySize - 1);
    }

    public void printArray() {
        for (int num : this.array) {
            System.out.println(num);
        }
    }

    private void quickSort(int[] arr, int low, int high) {
        if (arr == null || arr.length == 0) {
            return;
        }

        if (low >= high) {
            return;
        }

        // pick the pivot
        int middle = low + (high - low) / 2;
        int pivot = arr[middle];

        // make left < pivot and right > pivot
        int currentLow = low, currentHigh = high;
        while (currentLow <= currentHigh) {
            while (arr[currentLow] < pivot) {
                currentLow++;
            }

            while (arr[currentHigh] > pivot) {
                currentHigh--;
            }

            if (currentLow <= currentHigh) {
                int temp = arr[currentLow];
                arr[currentLow] = arr[currentHigh];
                arr[currentHigh] = temp;
                currentLow++;
                currentHigh--;
            }
        }

        // recursively sort two sub parts
        if (low < currentHigh) {
            quickSort(arr, low, currentHigh);
        }

        if (high > currentLow) {
            quickSort(arr, currentLow, high);
        }
    }
}
