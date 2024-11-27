package ru.nsu;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalInt;
import java.util.stream.Collectors;

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
        if (!isPaid || getCurrentSemester() < 2) {
            return false;
        }
        long satisfactoryCount = grades.stream()
            .filter(grade -> grade.getSemester() >= getCurrentSemester() - 2)
            .filter(grade -> grade.getCourseGrade() == Grade.SATISFACTORY
                && grade.getControlType() == ControlType.EXAM)
            .count();

        boolean hasFail = grades.stream().anyMatch(grade -> grade.getCourseGrade() == Grade.FAIL);

        return satisfactoryCount == 0 && !hasFail;
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
                .filter(grade -> grade.getSemester() == lastSemester).collect(toList());
        }
        return new ArrayList<>();
    }

    /**
     * Gets grades from diploma supplement.
     *
     * @return grades from diploma supplement.
     */
    private List<CourseGrade> getDiplomaSupplementGrades() {
        return new ArrayList<>(grades.stream()
            .collect(Collectors.toMap(
                CourseGrade::getCourse,
                grade -> grade,
                (existing, replacement) -> replacement
            )).values());
    }


    /**
     * Calculates ability for the student to get honors diploma.
     *
     * @return {@code true} if student can get honors diploma else {@code false}.
     */
    public boolean canGetHonorsDiploma() {
        if (grades.isEmpty()) {
            return true;
        }

        List<CourseGrade> lastGrades = getDiplomaSupplementGrades();
        long excellentCount = lastGrades.stream()
            .filter(grade -> grade.getCourseGrade() == Grade.EXCELLENT)
            .count();

        boolean hasNoSatisfactoryAndFail = lastGrades.stream()
            .noneMatch(grade -> grade.getCourseGrade() == Grade.SATISFACTORY
                && grade.getCourseGrade() == Grade.FAIL);

        boolean thesisExcellent = lastGrades.stream()
            .anyMatch(grade -> grade.getControlType() == ControlType.THESIS
                && grade.getCourseGrade() == Grade.EXCELLENT);

        double excellentPercentage = (double) excellentCount / lastGrades.size();

        return excellentPercentage >= 0.75 && hasNoSatisfactoryAndFail && thesisExcellent;
    }


    /**
     * Calculates ability for student to get increased scholarship.
     *
     * @return {@code true} if student can get increased scholarship else {@code false}.
     */
    public boolean canGetIncreasedScholarship() {
        List<CourseGrade> lastGrades = getGradesForLastSemester();
        return lastGrades.stream().filter(grade -> grade.getControlType() == ControlType.EXAM)
            .allMatch(grade -> grade.getCourseGrade() == Grade.EXCELLENT);
    }
}