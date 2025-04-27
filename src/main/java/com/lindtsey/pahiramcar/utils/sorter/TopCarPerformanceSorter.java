package com.lindtsey.pahiramcar.utils.sorter;

import com.lindtsey.pahiramcar.reports.CarPerformance;

import java.util.List;

public class TopCarPerformanceSorter {
    public static void mergeSortBookings(List<CarPerformance> carPerformances) {
        if (carPerformances == null || carPerformances.size() <= 1) {
            return; // Already sorted or empty list
        }
        mergeSort(carPerformances, 0, carPerformances.size() - 1);
    }

    private static void mergeSort(List<CarPerformance> carPerformances, int left, int right) {
        if (left < right) {
            int mid = left + (right - left) / 2;

            // Recursively sort the left and right halves
            mergeSort(carPerformances, left, mid);
            mergeSort(carPerformances, mid + 1, right);

            // Merge the sorted halves
            merge(carPerformances, left, mid, right);
        }
    }

    private static void merge(List<CarPerformance> carPerformances, int left, int mid, int right) {
        int n1 = mid - left + 1; // Size of the left subarray
        int n2 = right - mid;   // Size of the right subarray

        // Create temporary arrays
        CarPerformance[] leftArray = new CarPerformance[n1];
        CarPerformance[] rightArray = new CarPerformance[n2];

        // Copy data to temporary arrays
        for (int i = 0; i < n1; i++) {
            leftArray[i] = carPerformances.get(left + i);
        }
        for (int j = 0; j < n2; j++) {
            rightArray[j] = carPerformances.get(mid + 1 + j);
        }

        // Merge the temporary arrays back into the original list
        int i = 0, j = 0, k = left;
        while (i < n1 && j < n2) {
            if (compareCarPerformances(leftArray[i], rightArray[j]) <= 0) {
                carPerformances.set(k, leftArray[i]);
                i++;
            } else {
                carPerformances.set(k, rightArray[j]);
                j++;
            }
            k++;
        }

        // Copy remaining elements of leftArray, if any
        while (i < n1) {
            carPerformances.set(k, leftArray[i]);
            i++;
            k++;
        }

        // Copy remaining elements of rightArray, if any
        while (j < n2) {
            carPerformances.set(k, rightArray[j]);
            j++;
            k++;
        }
    }

    private static int compareCarPerformances(CarPerformance cp1, CarPerformance cp2) {
        // Primary sort: Compare by booking
        return Long.compare(cp2.getTotalBookings(), cp1.getTotalBookings());
    }
}
