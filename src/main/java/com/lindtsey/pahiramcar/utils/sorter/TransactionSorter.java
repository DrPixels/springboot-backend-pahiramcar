package com.lindtsey.pahiramcar.utils.sorter;

import com.lindtsey.pahiramcar.bookings.Booking;
import com.lindtsey.pahiramcar.transactions.Transaction;

import java.util.List;

public class TransactionSorter {

    public static void mergeSortTransactions(List<Transaction> transactions) {
        if (transactions == null || transactions.size() <= 1) {
            return; // Already sorted or empty list
        }
        mergeSort(transactions, 0, transactions.size() - 1);
    }

    private static void mergeSort(List<Transaction> transactions, int left, int right) {
        if (left < right) {
            int mid = left + (right - left) / 2;

            // Recursively sort the left and right halves
            mergeSort(transactions, left, mid);
            mergeSort(transactions, mid + 1, right);

            // Merge the sorted halves
            merge(transactions, left, mid, right);
        }
    }

    private static void merge(List<Transaction> transactions, int left, int mid, int right) {
        int n1 = mid - left + 1; // Size of the left subarray
        int n2 = right - mid;   // Size of the right subarray

        // Create temporary arrays
        Transaction[] leftArray = new Transaction[n1];
        Transaction[] rightArray = new Transaction[n2];

        // Copy data to temporary arrays
        for (int i = 0; i < n1; i++) {
            leftArray[i] = transactions.get(left + i);
        }
        for (int j = 0; j < n2; j++) {
            rightArray[j] = transactions.get(mid + 1 + j);
        }

        // Merge the temporary arrays back into the original list
        int i = 0, j = 0, k = left;
        while (i < n1 && j < n2) {
            if (compareTransactions(leftArray[i], rightArray[j]) <= 0) {
                transactions.set(k, leftArray[i]);
                i++;
            } else {
                transactions.set(k, rightArray[j]);
                j++;
            }
            k++;
        }

        // Copy remaining elements of leftArray, if any
        while (i < n1) {
            transactions.set(k, leftArray[i]);
            i++;
            k++;
        }

        // Copy remaining elements of rightArray, if any
        while (j < n2) {
            transactions.set(k, rightArray[j]);
            j++;
            k++;
        }
    }

    private static int compareTransactions(Transaction t1, Transaction t2) {
        // Primary sort: Compare by startDateTime
        return t2.getTransactionDateTime().compareTo(t1.getTransactionDateTime());

    }
}
