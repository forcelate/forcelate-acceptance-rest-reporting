package io.tech1.acceptance.test;

import io.tech1.acceptance.domain.processing.CaseAnnotation;
import io.tech1.acceptance.domain.processing.CaseCall;
import io.tech1.acceptance.domain.processing.CaseStatus;
import io.tech1.acceptance.domain.processing.Execution;
import org.junit.runner.Description;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static io.tech1.acceptance.test.RandomUtils.randomString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AcceptanceUtils {

    public static Execution randomExecutionByStatusCount(CaseStatus status, int count) {
        return Execution.builder()
                .calls(IntStream.range(0, count)
                        .mapToObj(item -> CaseCall.builder()
                                .description(randomString())
                                .status(status)
                                .message(randomString())
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }

    public static Execution randomExecutionByStatuses(CaseStatus status1, CaseStatus status2) {
        return Execution.builder()
                .calls(Arrays.asList(
                        CaseCall.builder()
                                .description(randomString())
                                .status(status1)
                                .message(randomString())
                                .build(),
                        CaseCall.builder()
                                .description(randomString())
                                .status(status2)
                                .message(randomString())
                                .build()
                ))
                .build();
    }

    public static Description description(String endpoint, String httpType) {
        Description description = mock(Description.class);
        CaseAnnotation caseAnnotation = mock(CaseAnnotation.class);
        when(caseAnnotation.endpoint()).thenReturn(endpoint);
        when(caseAnnotation.httpType()).thenReturn(httpType);
        when(description.getAnnotation(CaseAnnotation.class)).thenReturn(caseAnnotation);
        return description;
    }
}
