package ru.nsu;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestStudentGradeBook {

    private StudentGradeBook gradeBook;

    @BeforeEach
    void setUp() {
        gradeBook = new StudentGradeBook("Ivan Ivanov", true);
        gradeBook.addGrade("Calculus", 1, ControlType.EXAM, Grade.EXCELLENT);
        gradeBook.addGrade("Theoretical mechanics", 1, ControlType.EXAM, Grade.GOOD);
        gradeBook.addGrade("OOP", 2, ControlType.DIFFERENTIATED_CREDIT, Grade.EXCELLENT);
    }

    @Test
    void testCalculateAverageGrade() {
        gradeBook.addGrade("Linear Algebra", 2, ControlType.EXAM, Grade.EXCELLENT);
        double average = gradeBook.calculateAverageGrade();
        Assertions.assertEquals(4.75, average);
    }

    @Test
    void testCanTransferToBudget() {
        gradeBook.addGrade("History", 3, ControlType.EXAM, Grade.GOOD);
        gradeBook.addGrade("Economics", 4, ControlType.EXAM, Grade.EXCELLENT);

        Assertions.assertTrue(gradeBook.canTransferToBudget());
    }

    @Test
    void testCanGetHonorsDiplomaTrue() {
        gradeBook.addGrade("Thesis", 8, ControlType.THESIS, Grade.EXCELLENT);
        Assertions.assertTrue(gradeBook.canGetHonorsDiploma());
    }

    @Test
    void testCanGetHonorsDiplomaFalse() {
        gradeBook.addGrade("English", 2, ControlType.EXAM, Grade.SATISFACTORY);
        Assertions.assertFalse(gradeBook.canGetHonorsDiploma());
    }

    @Test
    void testCanGetIncreasedScholarship() {
        gradeBook.addGrade("History", 2, ControlType.EXAM, Grade.EXCELLENT);
        gradeBook.addGrade("Economics", 2, ControlType.EXAM, Grade.EXCELLENT);

        Assertions.assertTrue(gradeBook.canGetIncreasedScholarship(),
            "Студент должен иметь возможность получения повышенной стипендии");
    }

    @Test
    void testGetCurrentSemester() {
        Assertions.assertEquals(2, gradeBook.getCurrentSemester());
    }

    @Test
    void testFailGrade() {
        gradeBook.addGrade("Mathematical logic", 2, ControlType.EXAM, Grade.FAIL);
        Assertions.assertFalse(gradeBook.canGetIncreasedScholarship());
        Assertions.assertFalse(gradeBook.canTransferToBudget());
        Assertions.assertFalse(gradeBook.canGetHonorsDiploma());
    }
}



