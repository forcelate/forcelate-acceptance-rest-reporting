package com.jedivision.acceptance.rest.utils.holder;

import com.jedivision.acceptance.rest.utils.domain.processing.CaseCall;
import com.jedivision.acceptance.rest.utils.domain.processing.CaseMapping;
import com.jedivision.acceptance.rest.utils.domain.processing.CaseStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.junit.runner.Description;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.jedivision.acceptance.rest.utils.runner.JunitCaseUtils.getCaseCall;
import static com.jedivision.acceptance.rest.utils.runner.JunitCaseUtils.getCaseMapping;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ExecutionsHolder {

    @Getter
    private static Map<CaseMapping, List<CaseCall>> executed = new HashMap<>();

    public static void addExecution(Description description, CaseStatus status, String message) {
        // get mapping, call from description
        CaseMapping mapping = getCaseMapping(description);
        CaseCall call = getCaseCall(description, status, message);
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
