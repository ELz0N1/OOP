package ru.nsu;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * A class for finding all occurrences of a given substring within a file.
 */
public class SubstringFinder {

    /**
     * Searches for all occurrences of a given substring in a file and returns a list of starting
     * indices.
     *
     * @param fileName the name of file to search in.
     * @param pattern  the substring to search for in the file.
     * @return a list of starting indices of each occurrence of the substring.
     */
    public static List<Integer> find(String fileName, String pattern) {
        List<Integer> indices = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            int offset = 0;
            while ((line = reader.readLine()) != null) {
                int[] lps = computeLPSArray(pattern);
                int j = 0; // Index for pattern
                for (int i = 0; i < line.length(); i++) {
                    while (j > 0 && line.charAt(i) != pattern.charAt(j)) {
                        j = lps[j - 1];
                    }
                    if (line.charAt(i) == pattern.charAt(j)) {
                        j++;
                    }
                    if (j == pattern.length()) {
                        indices.add(offset + i - j + 1);
                        j = lps[j - 1]; // Move pattern back
                    }
                }
                offset += line.length() + 1;
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
        return indices;
    }


    /**
     * Computes the longest proper prefix suffix (LPS) array for the pattern.
     *
     * @param pattern the substring for which to build the LPS array.
     * @return the LPS array.
     */
    private static int[] computeLPSArray(String pattern) {
        int[] lps = new int[pattern.length()];
        int length = 0;
        lps[0] = 0;
        for (int i = 1; i < pattern.length(); i++) {
            while (length > 0 && pattern.charAt(i) != pattern.charAt(length)) {
                length = lps[length - 1];
            }
            if (pattern.charAt(i) == pattern.charAt(length)) {
                length++;
            }
            lps[i] = length;
        }
        return lps;
    }
}

