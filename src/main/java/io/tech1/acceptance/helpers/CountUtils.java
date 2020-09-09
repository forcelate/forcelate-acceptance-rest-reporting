package io.tech1.acceptance.helpers;

import io.tech1.acceptance.configuration.ApplicationConstants;
import io.tech1.acceptance.domain.processing.CaseCall;
import io.tech1.acceptance.domain.processing.CaseStatus;
import io.tech1.acceptance.domain.processing.Execution;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Stream;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

@Component
public class CountUtils {

    public Map<CaseStatus, Long> executionsStatuses(List<Execution> executions) {
        if (executions == null) {
            return Collections.emptyMap();
        } else {
            Map<CaseStatus, Long> statuses = executions.stream()
                    .map(Execution::getCalls)
                    .flatMap(List::stream)
                    .map(CaseCall::getStatus)
                    .collect(groupingBy(identity(), counting()));
            Stream.of(CaseStatus.values()).forEach(status -> {
                if (!statuses.containsKey(status)) {
                    statuses.put(status, ApplicationConstants.UNCERTAIN_COUNT);
                }
            });
            return new TreeMap<>(statuses);
        }
    }
}
