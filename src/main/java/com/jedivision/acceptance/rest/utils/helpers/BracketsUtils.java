package com.jedivision.acceptance.rest.utils.helpers;

import org.springframework.stereotype.Component;

@Component
public class BracketsUtils {
    private static final String LEFT = "[";
    private static final String RIGHT = "]";

    public String between(String str) {
        return str.substring(str.indexOf(LEFT) + 1, str.indexOf(RIGHT));
    }
}
