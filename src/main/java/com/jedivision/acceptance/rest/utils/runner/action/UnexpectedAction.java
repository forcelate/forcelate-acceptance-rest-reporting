package com.jedivision.acceptance.rest.utils.runner.action;

import com.jedivision.acceptance.rest.utils.configuration.ApplicationConstants;
import com.jedivision.acceptance.rest.utils.domain.processing.CaseStatus;
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