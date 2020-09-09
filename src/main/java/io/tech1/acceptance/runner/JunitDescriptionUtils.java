package io.tech1.acceptance.runner;

import io.tech1.acceptance.domain.processing.CaseAnnotation;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.junit.runner.Description;

import java.util.Optional;
import java.util.function.Function;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
final class JunitDescriptionUtils {
    static final String UNEXPECTED = "Unexpected. Please process...";

    static String getEndpoint(Description description) {
        return byFunc(description, CaseAnnotation::endpoint);
    }

    static String getHttpType(Description description) {
        return byFunc(description, CaseAnnotation::httpType);
    }

    static String getDescription(Description description) {
        return byFunc(description, CaseAnnotation::description);
    }

    static Optional<CaseAnnotation> accCaseAnnotation(Description description) {
        return Optional.ofNullable(description.getAnnotation(CaseAnnotation.class));
    }

    static String byFunc(Description description, Function<CaseAnnotation, String> func) {
        Optional<CaseAnnotation> accCaseAnnotation = accCaseAnnotation(description);
        if (accCaseAnnotation.isPresent()) {
            return func.apply(accCaseAnnotation.get());
        } else {
            return UNEXPECTED;
        }
    }
}
