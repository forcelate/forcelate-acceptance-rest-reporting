package io.tech1.acceptance.runner.action;

import io.tech1.acceptance.configuration.ApplicationErrorMessages;
import org.junit.runner.Description;

import static io.tech1.acceptance.domain.processing.CaseStatus.SERVER_DOWN;

public class ServerDownAction implements FailedAction {

    @Override
    public boolean select(Throwable e) {
        return e.getMessage().contains(ApplicationErrorMessages.SERVER_DOWN_MESSAGE) || e.getMessage().contains(ApplicationErrorMessages.CONNECTION_LOST_MESSAGE);
    }

    @Override
    public void process(Description description) {
        process(description, SERVER_DOWN, ApplicationErrorMessages.SERVER_DOWN_MESSAGE);
    }
}
