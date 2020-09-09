package io.tech1.acceptance.runner.action;

import io.tech1.acceptance.configuration.ApplicationErrorMessages;
import org.junit.runner.Description;

import static io.tech1.acceptance.domain.processing.CaseStatus.EMPTY;

public class EmptyAction implements FailedAction {

    @Override
    public boolean select(Throwable e) {
        return e.getMessage().contains(ApplicationErrorMessages.EMPTY_RESPONSE);
    }

    @Override
    public void process(Description description) {
        process(description, EMPTY, ApplicationErrorMessages.EMPTY_RESPONSE);
    }
}
