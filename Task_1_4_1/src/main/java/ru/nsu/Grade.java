package ru.nsu;

/**
 * Class for managing grades of education ranking system.
 */
public enum Grade {
    EXCELLENT(5),
    GOOD(4),
    SATISFACTORY(3),
    FAIL(2);

    private final Integer value;

    /**
     * Constructs grade.
     *
     * @param val value of grade.
     */
    Grade(Integer val) {
        this.value = val;
    }

    /**
     * Gets value of grade.
     *
     * @return grade's value.
     */
    public Integer getGradeValue() {
        return value;
    }

}
