package com.forcelate.acceptance.domain.processing;

public enum FrameworkType {

    SPRING_BOOT_V1("spring boot v1"),
    SPRING_BOOT_V2("spring boot v2");

    private final String type;

    FrameworkType(String s) {
        type = s;
    }

    @Override
    public String toString() {
        return this.type;
    }
}
