package ru.nsu;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalInt;

/**
 * Class for managing student grade book.
 */
public class StudentGradeBook {

    final String studentName;
    boolean isPaid;
    List<CourseGrade> grades;

    /**
     * Constructs student grade book.
     *
     * @param studentName student's name.
     * @param isPaid      is student on paid form.
     */
    public StudentGradeBook(String studentName, boolean isPaid) {
        this.studentName = studentName;
        this.isPaid = isPaid;
        this.grades = new ArrayList<>();
    }

    /**
     * Adds grade to student's grade book.
     *
     * @param grade grade to be added.
     */
    public void addGrade(String course, Integer semester, ControlType type, Grade grade) {
        grades.add(new CourseGrade(course, semester, type, grade));
    }

    /**
     * Gets the number of current semester.
     *
     * @return current semester number.
     */
    public int getCurrentSemester() {
        return grades.stream().mapToInt(CourseGrade::getSemester).max().orElse(0);
    }

    /**
     * Calculates average grade of the student.
     *
     * @return an average of all student's grades.
     */
    public double calculateAverageGrade() {
        if (grades.isEmpty()) {
            return 0.0;
        }
        int sum = 0;
        for (CourseGrade grade : grades) {
            sum += grade.getCourseGrade().getGradeValue();
        }
        return (double) sum / grades.size();
    }

    /**
     * Calculates ability for the student to transfer to budget.
     *
     * @return {@code true} if student can transfer to budget else {@code false}.
     */
    public boolean canTransferToBudget() {
        if (!isPaid) {
            return false;
        }
        long satisfactoryCount = grades.stream()
            .filter(grade -> grade.getSemester() >= getCurrentSemester() - 2)
            .filter(grade -> grade.getCourseGrade() == Grade.SATISFACTORY
                && grade.getControlType() == ControlType.EXAM)
            .count();

        return satisfactoryCount == 0;
    }


    /**
     * Gets grades for latest semester.
     *
     * @return a list of grades for latest semester.
     */
    public List<CourseGrade> getGradesForLastSemester() {
        OptionalInt lastSemesterOpt = grades.stream()
            .mapToInt(CourseGrade::getSemester)
            .max();

        if (lastSemesterOpt.isPresent()) {
            int lastSemester = lastSemesterOpt.getAsInt();

            return grades.stream()
                .filter(grade -> grade.getSemester() == lastSemester).toList();
        }
        return new ArrayList<>();
    }


    /**
     * Calculates ability for the student to get honors diploma.
     *
     * @return {@code true} if student can get honors diploma else {@code false}.
     */
    public boolean canGetHonorsDiploma() {
        if (grades.isEmpty()) {
            return false;
        }

        List<CourseGrade> lastGrades = getGradesForLastSemester();
        long excellentCount = lastGrades.stream()
            .filter(grade -> grade.getCourseGrade() == Grade.EXCELLENT)
            .count();

        boolean hasNoSatisfactory = lastGrades.stream()
            .noneMatch(grade -> grade.getCourseGrade() == Grade.SATISFACTORY);

        boolean thesisExcellent = lastGrades.stream()
            .anyMatch(grade -> grade.getControlType() == ControlType.THESIS
                && grade.getCourseGrade() == Grade.EXCELLENT);

        double excellentPercentage = (double) excellentCount / lastGrades.size();

        return excellentPercentage >= 0.75 && hasNoSatisfactory && thesisExcellent;
    }


    /**
     * Calculates ability for student to get increased scholarship.
     *
     * @return {@code true} if student can get increased scholarship else {@code false}.
     */
    public boolean canGetIncreasedScholarship() {

        int currentSemester = getCurrentSemester();
        return grades.stream().filter(grade -> grade.getSemester() == currentSemester
                && grade.getControlType() == ControlType.EXAM)
            .allMatch(grade -> grade.getCourseGrade() == Grade.EXCELLENT);
    }
}