package com.forcelate.acceptance.runner.action;

import org.junit.runner.Description;

import static com.forcelate.acceptance.configuration.ApplicationErrorMessages.STATUS_CODE_500;
import static com.forcelate.acceptance.configuration.ApplicationErrorMessages.WRONG_RESPONSE_BODY;
import static com.forcelate.acceptance.domain.processing.CaseStatus.FAILURE;

public class FailureAction implements FailedAction {

    private String message;

    @Override
    public boolean select(Throwable e) {
        message = e.getMessage();
        return e.getMessage().contains(STATUS_CODE_500) || e.getMessage().contains(WRONG_RESPONSE_BODY);
    }

    @Override
    public void process(Description description) {
        if (message.contains(STATUS_CODE_500)) {
            process(description, FAILURE, STATUS_CODE_500);
        } else {
            process(description, FAILURE, WRONG_RESPONSE_BODY);
        }

    }
}
