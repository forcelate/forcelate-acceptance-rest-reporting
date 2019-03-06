package com.forcelate.acceptance.runner.action;

import com.forcelate.acceptance.configuration.ApplicationConstants;
import com.forcelate.acceptance.domain.processing.CaseStatus;
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