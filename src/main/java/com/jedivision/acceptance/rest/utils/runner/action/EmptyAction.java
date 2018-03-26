package com.jedivision.acceptance.rest.utils.runner.action;

import org.junit.runner.Description;

import static com.jedivision.acceptance.rest.utils.configuration.ApplicationErrorMessages.EMPTY_RESPONSE;
import static com.jedivision.acceptance.rest.utils.domain.processing.CaseStatus.EMPTY;

public class EmptyAction implements FailedAction {

    @Override
    public boolean select(Throwable e) {
        return e.getMessage().contains(EMPTY_RESPONSE);
    }

    @Override
    public void process(Description description) {
        process(description, EMPTY, EMPTY_RESPONSE);
    }
}
