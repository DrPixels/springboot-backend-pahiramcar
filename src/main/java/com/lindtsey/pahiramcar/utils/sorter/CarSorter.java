package com.lindtsey.pahiramcar.utils.sorter;

import com.lindtsey.pahiramcar.car.Car;

import java.util.List;

public class CarSorter {

    public static void mergeSortBookings(List<Car> cars) {
        if (cars == null || cars.size() <= 1) {
            return; // Already sorted or empty list
        }
        mergeSort(cars, 0, cars.size() - 1);
    }

    private static void mergeSort(List<Car> cars, int left, int right) {
        if (left < right) {
            int mid = left + (right - left) / 2;

            // Recursively sort the left and right halves
            mergeSort(cars, left, mid);
            mergeSort(cars, mid + 1, right);

            // Merge the sorted halves
            merge(cars, left, mid, right);
        }
    }

    private static void merge(List<Car> cars, int left, int mid, int right) {
        int n1 = mid - left + 1; // Size of the left subarray
        int n2 = right - mid;   // Size of the right subarray

        // Create temporary arrays
        Car[] leftArray = new Car[n1];
        Car[] rightArray = new Car[n2];

        // Copy data to temporary arrays
        for (int i = 0; i < n1; i++) {
            leftArray[i] = cars.get(left + i);
        }
        for (int j = 0; j < n2; j++) {
            rightArray[j] = cars.get(mid + 1 + j);
        }

        // Merge the temporary arrays back into the original list
        int i = 0, j = 0, k = left;
        while (i < n1 && j < n2) {
            if (compareCars(leftArray[i], rightArray[j]) <= 0) {
                cars.set(k, leftArray[i]);
                i++;
            } else {
                cars.set(k, rightArray[j]);
                j++;
            }
            k++;
        }

        // Copy remaining elements of leftArray, if any
        while (i < n1) {
            cars.set(k, leftArray[i]);
            i++;
            k++;
        }

        // Copy remaining elements of rightArray, if any
        while (j < n2) {
            cars.set(k, rightArray[j]);
            j++;
            k++;
        }
    }

    private static int compareCars(Car c1, Car c2) {
        // Primary sort: Compare by carPrice
        int carPriceComparison = c1.getPricePerDay().compareTo(c2.getPricePerDay());
        if (carPriceComparison != 0) {
            return carPriceComparison;
        }

        // Secondary sort: Compare by seats
        int carSeatsComparison = Integer.compare(c1.getSeats(), c2.getSeats());
        if (carSeatsComparison != 0) {
            return carSeatsComparison;
        }

        // Third sort: Compare by status
        return Integer.compare(c1.getStatus().getOrder(), c2.getStatus().getOrder());
    }
}
