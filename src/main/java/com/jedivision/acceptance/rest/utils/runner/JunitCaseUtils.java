package com.jedivision.acceptance.rest.utils.runner;

import com.jedivision.acceptance.rest.utils.domain.processing.CaseCall;
import com.jedivision.acceptance.rest.utils.domain.processing.CaseMapping;
import com.jedivision.acceptance.rest.utils.domain.processing.CaseStatus;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.junit.runner.Description;

import static com.jedivision.acceptance.rest.utils.runner.JunitDescriptionUtils.getDescription;
import static com.jedivision.acceptance.rest.utils.runner.JunitDescriptionUtils.getEndpoint;
import static com.jedivision.acceptance.rest.utils.runner.JunitDescriptionUtils.getHttpType;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class JunitCaseUtils {

    public static CaseCall getCaseCall(Description description, CaseStatus status, String message) {
        return CaseCall.builder()
                .description(getDescription(description))
                .status(status)
                .message(message)
                .build();
    }

    public static CaseMapping getCaseMapping(Description description) {
        return CaseMapping.builder()
                .endpoint(getEndpoint(description))
                .httpType(getHttpType(description))
                .build();
    }
}
