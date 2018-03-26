package com.jedivision.acceptance.rest.utils.helpers;

import com.jedivision.acceptance.rest.utils.domain.processing.CaseCall;
import com.jedivision.acceptance.rest.utils.domain.processing.CaseStatus;
import com.jedivision.acceptance.rest.utils.domain.processing.Execution;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Stream;

import static com.jedivision.acceptance.rest.utils.configuration.ApplicationConstants.UNCERTAIN_COUNT;
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
                    statuses.put(status, UNCERTAIN_COUNT);
                }
            });
            return new TreeMap<>(statuses);
        }
    }
}
