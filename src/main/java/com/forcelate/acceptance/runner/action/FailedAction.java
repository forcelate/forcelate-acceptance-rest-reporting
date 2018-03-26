package com.forcelate.acceptance.runner.action;

import com.forcelate.acceptance.domain.processing.CaseStatus;
import com.forcelate.acceptance.holder.ExecutionsHolder;
import org.junit.runner.Description;

public interface FailedAction {

    boolean select(Throwable e);

    void process(Description description);

    default void process(Description description, CaseStatus status, String message) {
        ExecutionsHolder.addExecution(description, status, message);
    }
}
