package ru.nsu;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.api.Test;

class TestSubstringFinder {

    /**
     * Helper method that writes text to a file.
     */
    private void writeToFile(File file, String content) throws IOException {
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(content);
        }
    }

    @TempDir
    Path tempDir;

    private File testFile;

    @BeforeEach
    void setUp() {
        testFile = tempDir.resolve("testFile.txt").toFile();
    }

    @Test
    void testFindSubstring() throws IOException {
        writeToFile(testFile, "абракадабра");
        List<Integer> result = SubstringFinder.find(testFile.getAbsolutePath(), "бра");
        List<Integer> expected = List.of(1, 8);
        Assertions.assertEquals(expected, result);
    }

    @Test
    void testFindSubstringSingleOccurrence() throws IOException {
        writeToFile(testFile, "abrakadabra");
        List<Integer> result = SubstringFinder.find(testFile.getAbsolutePath(), "kad");
        List<Integer> expected = List.of(4);
        Assertions.assertEquals(expected, result);
    }

    @Test
    void testFindSubstringMultipleOccurrence() throws IOException {
        writeToFile(testFile, "aaaaaa");
        List<Integer> result = SubstringFinder.find(testFile.getAbsolutePath(), "a");
        List<Integer> expected = List.of(0, 1, 2, 3, 4, 5);
        Assertions.assertEquals(expected, result);
    }

    @Test
    void testFindSubstringAtStartAndEnd() throws IOException {
        writeToFile(testFile, "aba   aba");
        List<Integer> result = SubstringFinder.find(testFile.getAbsolutePath(), "aba");
        List<Integer> expected = List.of(0, 6);
        Assertions.assertEquals(expected, result);
    }


    @Test
    void testFindSubstringNonExisting() throws IOException {
        writeToFile(testFile, "Some string for test.");
        List<Integer> result = SubstringFinder.find(testFile.getAbsolutePath(), "qwerty");
        List<Integer> expected = List.of();
        Assertions.assertEquals(expected, result);
    }

    @Test
    void testFindSubstringInLargeFile() throws IOException {
        try (FileWriter writer = new FileWriter(testFile)) {
            for (int i = 0; i < 10000; i++) {
                writer.write("a");
            }
            writer.write("b");
        }

        List<Integer> result = SubstringFinder.find(testFile.getAbsolutePath(), "b");
        List<Integer> expected = List.of(10000);
        Assertions.assertEquals(expected, result);
    }

    @Test
    void testFindSubstringInEmptyFile() throws IOException {
        writeToFile(testFile, "");
        List<Integer> result = SubstringFinder.find(testFile.getAbsolutePath(), "blank");
        List<Integer> expected = List.of();
        Assertions.assertEquals(expected, result);
    }

    @Test
    void testFindSubstringLongerThanText() throws IOException {
        writeToFile(testFile, "short");
        List<Integer> result = SubstringFinder.find(testFile.getAbsolutePath(),
            "substringLongerThanText");
        List<Integer> expected = List.of();
        Assertions.assertEquals(expected, result);
    }
}