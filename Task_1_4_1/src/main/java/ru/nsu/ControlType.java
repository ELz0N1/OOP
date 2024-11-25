package ru.nsu;

/**
 * Class for managing for education control types.
 */
public enum ControlType {
    EXAM("Экзамен"),
    DIFFERENTIATED_CREDIT("Дифференцированный зачет"),
    THESIS("Квалификационная работа");

    final String name;

    /**
     * Constructs education control type.
     *
     * @param name name of control type.
     */
    ControlType(String name) {
        this.name = name;
    }

    /**
     * Returns control type name.
     *
     * @return name of education control type.
     */
    public String getName() {
        return name;
    }
}
