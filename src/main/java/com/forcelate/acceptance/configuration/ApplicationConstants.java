package com.forcelate.acceptance.configuration;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ApplicationConstants {

    public static final String EMPTY = "";
    public static final String UNEXPECTED = "Unexpected";
    public static final String UNCERTAIN = "Uncertain";
    public static final long UNCERTAIN_COUNT = 0;

    public static final String SPRING_BOOT_INFO_URL = "info";
    public static final String SPRING_BOOT_MAPPING_URL = "mappings";

    public static final String REPORT_TEMPLATE = "jedi-springboot-acc-report.ftl";
    public static final String REPORT_HTML = "target/jedi-springboot-acc-report.html";

    public static final String PROFILE_DEFAULT_VALUE = "dev";
    public static final String PROFILE_KEY = "environment";
}
