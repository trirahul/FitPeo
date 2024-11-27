package com.fitpeo.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class to handle operations related to test data.
 */
public class TestDataUtils {

    /**
     * Reads data from a CSV file and returns it as a list of string arrays.
     *
     * @param filePath the path to the CSV file.
     * @return a list where each element is an array of strings, representing a row from the CSV file.
     */
    public static List<String[]> readCsvData(String filePath) {
        List<String[]> records = new ArrayList<>(); // List to store CSV rows as string arrays

        // Try-with-resources to ensure the BufferedReader is closed automatically
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;

            // Read the file line by line
            while ((line = br.readLine()) != null) {
                String[] values = line.split(","); // Split the line into values by commas
                records.add(values); // Add the array of values to the list
            }
        } catch (Exception e) {
            e.printStackTrace(); // Print the stack trace for any exception
        }

        return records; // Return the list of records
    }
}
