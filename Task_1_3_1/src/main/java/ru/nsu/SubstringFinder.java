package ru.nsu;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * A class for finding all occurrences of a given substring within a file.
 */
public class SubstringFinder {

    private static final int CHUNK_SIZE = 1024 * 1024; // 1MB chunk size

    /**
     * Searches for all occurrences of a given substring in a file and returns a list of starting
     * indices.
     *
     * @param fileName the name of file to search in.
     * @param pattern  the substring to search for in the file.
     * @return a list of starting indices of each occurrence of the substring.
     */
    public static List<Integer> find(String fileName, String pattern) throws IOException {
        List<Integer> indices = new ArrayList<>();
        try (FileInputStream fis = new FileInputStream(fileName);
            InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8)) {

            char[] buffer = new char[CHUNK_SIZE];
            int bytesRead;
            int offset = 0;

            while ((bytesRead = isr.read(buffer, 0, CHUNK_SIZE)) != -1) {
                String chunk = new String(buffer, 0, bytesRead);
                indices.addAll(findMatches(chunk, pattern, offset));
                offset += bytesRead;

                if (bytesRead == CHUNK_SIZE && !chunk.isEmpty()) {
                    int overlap = pattern.length() - 1;
                    if (overlap > 0) {
                        offset -= overlap;
                    }
                }
            }
        }
        return indices;
    }

    /**
     * Searches for all occurrences of a given substring in a text and returns a list of starting
     * indices.
     *
     * @param text    the text in which to search.
     * @param pattern the substring to search for in the file.
     * @param offset  offset from the beginning of the file.
     * @return a list with indices found in text.
     */
    private static List<Integer> findMatches(String text, String pattern, int offset) {
        List<Integer> indices = new ArrayList<>();
        int[] lps = computeLpsArray(pattern);
        if (lps != null) {
            int j = 0;
            for (int i = 0; i < text.length(); i++) {
                while (j > 0 && text.charAt(i) != pattern.charAt(j)) {
                    j = lps[j - 1];
                }
                if (text.charAt(i) == pattern.charAt(j)) {
                    j++;
                }
                if (j == pattern.length()) {
                    indices.add(offset + i - j + 1);
                    j = lps[j - 1];
                }
            }
        }
        return indices;
    }


    /**
     * Computes the longest proper prefix suffix (LPS) array for the pattern.
     *
     * @param pattern the substring for which to build the LPS array.
     * @return the LPS array.
     */
    private static int[] computeLpsArray(String pattern) {
        if (pattern.isEmpty()) {
            return null;
        }
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

