package com.jedivision.acceptance.rest.utils.runner.action;

import com.jedivision.acceptance.rest.utils.domain.processing.CaseStatus;
import org.junit.runner.Description;

import static com.jedivision.acceptance.rest.utils.holder.ExecutionsHolder.addExecution;

public interface FailedAction {

    boolean select(Throwable e);

    void process(Description description);

    default void process(Description description, CaseStatus status, String message) {
        addExecution(description, status, message);
    }
}
