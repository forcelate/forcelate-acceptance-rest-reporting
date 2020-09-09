package io.tech1.acceptance.configuration;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ApplicationConstants {

    public static final String EMPTY = "";
    public static final String UNEXPECTED = "Unexpected";
    public static final String UNCERTAIN = "Uncertain";
    public static final long UNCERTAIN_COUNT = 0;

    public static final String SPRING_BOOT_INFO_URL_V1 = "/info";
    public static final String SPRING_BOOT_MAPPING_URL_V1 = "/mappings";

    public static final String SPRING_BOOT_INFO_URL_V2 = "/actuator/info";
    public static final String SPRING_BOOT_MAPPING_URL_V2 = "/actuator/mappings";

    public static final String REPORT_TEMPLATE = "tech1-acceptance-report.ftl";
    public static final String REPORT_HTML = "target/tech1-acceptance-report.html";

    public static final String PROFILE_DEFAULT_VALUE = "dev";
    public static final String PROFILE_KEY = "environment";
}
