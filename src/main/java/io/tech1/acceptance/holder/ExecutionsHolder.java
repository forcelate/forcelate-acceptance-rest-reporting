package io.tech1.acceptance.holder;

import io.tech1.acceptance.domain.processing.CaseCall;
import io.tech1.acceptance.domain.processing.CaseMapping;
import io.tech1.acceptance.domain.processing.CaseStatus;
import io.tech1.acceptance.runner.JunitCaseUtils;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.junit.runner.Description;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ExecutionsHolder {

    @Getter
    private static Map<CaseMapping, List<CaseCall>> executed = new HashMap<>();

    public static void addExecution(Description description, CaseStatus status, String message) {
        // get mapping, call from description
        CaseMapping mapping = JunitCaseUtils.getCaseMapping(description);
        CaseCall call = JunitCaseUtils.getCaseCall(description, status, message);
        // add pair [mappings, call]
        if (executed.containsKey(mapping)) {
            executed.get(mapping).add(call);
        } else {
            List<CaseCall> calls = new ArrayList<>();
            calls.add(call);
            executed.put(mapping, calls);
        }
    }
}
