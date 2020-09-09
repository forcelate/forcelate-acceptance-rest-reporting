package io.tech1.acceptance.runner.action;

import io.tech1.acceptance.domain.processing.CaseStatus;
import io.tech1.acceptance.holder.ExecutionsHolder;
import org.junit.runner.Description;

public interface FailedAction {

    boolean select(Throwable e);

    void process(Description description);

    default void process(Description description, CaseStatus status, String message) {
        ExecutionsHolder.addExecution(description, status, message);
    }
}
