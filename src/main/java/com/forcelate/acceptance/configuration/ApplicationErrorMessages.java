package com.forcelate.acceptance.configuration;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ApplicationErrorMessages {

    public static final String EMPTY_RESPONSE = "instance has 0 elements";
    public static final String STATUS_CODE_500 = "status code <500>";
    public static final String WRONG_RESPONSE_BODY = "Response body doesn't match expectation";
    public static final String SERVER_DOWN_MESSAGE = "Connection refused";
    public static final String CONNECTION_LOST_MESSAGE = "Failed to parse the JSON document";
}
