package io.tech1.acceptance.runner.action;

import io.tech1.acceptance.configuration.ApplicationConstants;
import io.tech1.acceptance.domain.processing.CaseStatus;
import org.junit.runner.Description;

public class UnexpectedAction implements FailedAction {

    @Override
    public boolean select(Throwable e) {
        return true;
    }

    @Override
    public void process(Description description) {
        process(description, CaseStatus.UNEXPECTED, ApplicationConstants.UNEXPECTED);
    }
}