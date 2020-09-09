package io.tech1.acceptance.helpers;

import io.tech1.acceptance.domain.processing.CaseCall;
import io.tech1.acceptance.domain.processing.CaseStatus;
import io.tech1.acceptance.domain.processing.Execution;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

import static java.util.Comparator.comparing;

@Component
public class SortUtils {

    public void byEndpoint(List<Execution> executions) {
        executions.sort(comparing(item -> item.getMapping().getEndpoint()));
    }

    public void callsByStatus(List<CaseCall> calls) {
        calls.sort(comparing(CaseCall::getStatus));
    }

    public void executionsByStatus(List<Execution> executions) {
        executions.sort(comparing(execution -> {
            Optional<CaseCall> call  = execution.getCalls().stream().findFirst();
            if (call.isPresent()) {
                return call.get().getStatus();
            } else {
                return CaseStatus.UNEXPECTED;
            }
        }));
    }
}
