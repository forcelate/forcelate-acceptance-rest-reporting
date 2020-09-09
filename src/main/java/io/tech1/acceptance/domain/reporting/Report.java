package io.tech1.acceptance.domain.reporting;

import io.tech1.acceptance.domain.processing.CaseStatus;
import io.tech1.acceptance.domain.processing.Execution;
import lombok.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
@ToString
public class Report {
    private Git git;
    private Environment environment;
    private boolean mappingsAvailability;
    private Map<CaseStatus, Long> count;
    private List<Execution> executions = Collections.emptyList();
}
