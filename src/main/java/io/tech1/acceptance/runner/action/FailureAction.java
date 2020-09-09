package io.tech1.acceptance.runner.action;

import io.tech1.acceptance.configuration.ApplicationErrorMessages;
import org.junit.runner.Description;

import static io.tech1.acceptance.domain.processing.CaseStatus.FAILURE;

public class FailureAction implements FailedAction {

    private String message;

    @Override
    public boolean select(Throwable e) {
        message = e.getMessage();
        return e.getMessage().contains(ApplicationErrorMessages.STATUS_CODE_500) || e.getMessage().contains(ApplicationErrorMessages.WRONG_RESPONSE_BODY);
    }

    @Override
    public void process(Description description) {
        if (message.contains(ApplicationErrorMessages.STATUS_CODE_500)) {
            process(description, FAILURE, ApplicationErrorMessages.STATUS_CODE_500);
        } else {
            process(description, FAILURE, ApplicationErrorMessages.WRONG_RESPONSE_BODY);
        }

    }
}
