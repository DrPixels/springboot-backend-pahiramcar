package com.lindtsey.pahiramcar.utils.sorter;

import com.lindtsey.pahiramcar.reservations.Reservation;

import java.util.List;

public class ReservationSorter {

    public static void mergeSortReservations(List<Reservation> reservations) {
        if (reservations == null || reservations.size() <= 1) {
            return; // Already sorted or empty list
        }
        mergeSort(reservations, 0, reservations.size() - 1);
    }

    private static void mergeSort(List<Reservation> reservations, int left, int right) {
        if (left < right) {
            int mid = left + (right - left) / 2;

            // Recursively sort the left and right halves
            mergeSort(reservations, left, mid);
            mergeSort(reservations, mid + 1, right);

            // Merge the sorted halves
            merge(reservations, left, mid, right);
        }
    }

    private static void merge(List<Reservation> reservations, int left, int mid, int right) {
        int n1 = mid - left + 1; // Size of the left subarray
        int n2 = right - mid;   // Size of the right subarray

        // Create temporary arrays
        Reservation[] leftArray = new Reservation[n1];
        Reservation[] rightArray = new Reservation[n2];

        // Copy data to temporary arrays
        for (int i = 0; i < n1; i++) {
            leftArray[i] = reservations.get(left + i);
        }
        for (int j = 0; j < n2; j++) {
            rightArray[j] = reservations.get(mid + 1 + j);
        }

        // Merge the temporary arrays back into the original list
        int i = 0, j = 0, k = left;
        while (i < n1 && j < n2) {
            if (compareReservations(leftArray[i], rightArray[j]) <= 0) {
                reservations.set(k, leftArray[i]);
                i++;
            } else {
                reservations.set(k, rightArray[j]);
                j++;
            }
            k++;
        }

        // Copy remaining elements of leftArray, if any
        while (i < n1) {
            reservations.set(k, leftArray[i]);
            i++;
            k++;
        }

        // Copy remaining elements of rightArray, if any
        while (j < n2) {
            reservations.set(k, rightArray[j]);
            j++;
            k++;
        }
    }

    private static int compareReservations(Reservation r1, Reservation r2) {
        // Primary sort: Compare by startDateTime
        int dateTimeComparison = r2.getStartDateTime().compareTo(r1.getStartDateTime());
        if (dateTimeComparison != 0) {
            return dateTimeComparison;
        }

        // Secondary sort: Compare by status order
        return Integer.compare(r1.getStatus().getOrder(), r2.getStatus().getOrder());
    }
}
