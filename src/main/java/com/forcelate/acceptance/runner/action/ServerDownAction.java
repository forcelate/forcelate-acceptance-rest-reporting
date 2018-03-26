package com.forcelate.acceptance.runner.action;

import org.junit.runner.Description;

import static com.forcelate.acceptance.configuration.ApplicationErrorMessages.CONNECTION_LOST_MESSAGE;
import static com.forcelate.acceptance.configuration.ApplicationErrorMessages.SERVER_DOWN_MESSAGE;
import static com.forcelate.acceptance.domain.processing.CaseStatus.SERVER_DOWN;

public class ServerDownAction implements FailedAction {

    @Override
    public boolean select(Throwable e) {
        return e.getMessage().contains(SERVER_DOWN_MESSAGE) || e.getMessage().contains(CONNECTION_LOST_MESSAGE);
    }

    @Override
    public void process(Description description) {
        process(description, SERVER_DOWN, SERVER_DOWN_MESSAGE);
    }
}
