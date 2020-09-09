package io.tech1.acceptance.domain.processing;

public enum CaseStatus {
    SERVER_DOWN ("Server Down"),
    FAILURE ("Failure"),
    UNEXPECTED ("Unexpected"),
    EMPTY ("Empty"),
    MISSING ("Missing"),
    SUCCESS ("Success");

    private final String name;

    CaseStatus(String s) {
        name = s;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
