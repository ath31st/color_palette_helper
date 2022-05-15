package com.example.second_tlg_bot;

public enum ModesEnum {
    COMPLEMENTARY("complementary"),
    ANALOGOUS("analogous"),
    MONOCHROMATIC("monochromatic"),
    TRIADIC("triadic"),
    TETRADIC("tetradic");

    private final String mode;

    ModesEnum(String mode) {
        this.mode = mode;
    }

    public String getMode() {
        return mode;
    }
}
