package com.lindtsey.pahiramcar.utils.sorter;

import com.lindtsey.pahiramcar.bookings.Booking;
import com.lindtsey.pahiramcar.reservations.Reservation;

import java.util.List;

public class BookingSorter {

    public static void mergeSortBookings(List<Booking> bookings) {
        if (bookings == null || bookings.size() <= 1) {
            return; // Already sorted or empty list
        }
        mergeSort(bookings, 0, bookings.size() - 1);
    }

    private static void mergeSort(List<Booking> bookings, int left, int right) {
        if (left < right) {
            int mid = left + (right - left) / 2;

            // Recursively sort the left and right halves
            mergeSort(bookings, left, mid);
            mergeSort(bookings, mid + 1, right);

            // Merge the sorted halves
            merge(bookings, left, mid, right);
        }
    }

    private static void merge(List<Booking> bookings, int left, int mid, int right) {
        int n1 = mid - left + 1; // Size of the left subarray
        int n2 = right - mid;   // Size of the right subarray

        // Create temporary arrays
        Booking[] leftArray = new Booking[n1];
        Booking[] rightArray = new Booking[n2];

        // Copy data to temporary arrays
        for (int i = 0; i < n1; i++) {
            leftArray[i] = bookings.get(left + i);
        }
        for (int j = 0; j < n2; j++) {
            rightArray[j] = bookings.get(mid + 1 + j);
        }

        // Merge the temporary arrays back into the original list
        int i = 0, j = 0, k = left;
        while (i < n1 && j < n2) {
            if (compareBookings(leftArray[i], rightArray[j]) <= 0) {
                bookings.set(k, leftArray[i]);
                i++;
            } else {
                bookings.set(k, rightArray[j]);
                j++;
            }
            k++;
        }

        // Copy remaining elements of leftArray, if any
        while (i < n1) {
            bookings.set(k, leftArray[i]);
            i++;
            k++;
        }

        // Copy remaining elements of rightArray, if any
        while (j < n2) {
            bookings.set(k, rightArray[j]);
            j++;
            k++;
        }
    }

    private static int compareBookings(Booking b1, Booking b2) {
        // Primary sort: Compare by startDateTime
        int dateTimeComparison = b2.getStartDateTime().compareTo(b1.getStartDateTime());
        if (dateTimeComparison != 0) {
            return dateTimeComparison;
        }

        // Secondary sort: Compare by status order
        return Integer.compare(b1.getStatus().getOrder(), b2.getStatus().getOrder());
    }
}
