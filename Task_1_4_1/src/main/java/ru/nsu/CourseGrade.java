package ru.nsu;

/**
 * Class for managing course grade.
 */
public class CourseGrade {

    private final String course;
    private final Integer semester;
    private final ControlType controlType;
    private final Grade grade;

    /**
     * Constructs course grade.
     *
     * @param course   name of course.
     * @param semester number of course semester.
     * @param type     type of course control.
     * @param grade    grade received for course.
     */
    CourseGrade(String course, Integer semester, ControlType type, Grade grade) {
        this.course = course;
        this.semester = semester;
        this.controlType = type;
        this.grade = grade;
    }

    /**
     * Gets course name.
     *
     * @return name of course.
     */
    public String getCourse() {
        return course;
    }

    /**
     * Gets number of course semester.
     *
     * @return number of semester.
     */
    public Integer getSemester() {
        return semester;
    }

    /**
     * Gets control type of course.
     *
     * @return course control type.
     */
    public ControlType getControlType() {
        return controlType;
    }

    /**
     * Gets grade received for the course.
     *
     * @return grade for the course.
     */
    public Grade getCourseGrade() {
        return grade;
    }
}
