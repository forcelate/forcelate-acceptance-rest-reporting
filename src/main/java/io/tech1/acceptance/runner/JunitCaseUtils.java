package io.tech1.acceptance.runner;

import io.tech1.acceptance.domain.processing.CaseCall;
import io.tech1.acceptance.domain.processing.CaseMapping;
import io.tech1.acceptance.domain.processing.CaseStatus;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.junit.runner.Description;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class JunitCaseUtils {

    public static CaseCall getCaseCall(Description description, CaseStatus status, String message) {
        return CaseCall.builder()
                .description(JunitDescriptionUtils.getDescription(description))
                .status(status)
                .message(message)
                .build();
    }

    public static CaseMapping getCaseMapping(Description description) {
        return CaseMapping.builder()
                .endpoint(JunitDescriptionUtils.getEndpoint(description))
                .httpType(JunitDescriptionUtils.getHttpType(description))
                .build();
    }
}
