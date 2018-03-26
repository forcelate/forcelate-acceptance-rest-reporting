package com.jedivision.acceptance.rest.utils.runner;

import com.jedivision.acceptance.rest.utils.domain.processing.CaseStatus;
import com.jedivision.acceptance.rest.utils.runner.action.*;
import org.junit.Rule;
import org.junit.rules.TestRule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static com.jedivision.acceptance.rest.utils.configuration.ApplicationConstants.EMPTY;
import static com.jedivision.acceptance.rest.utils.holder.ExecutionsHolder.addExecution;
import static com.jedivision.acceptance.rest.utils.configuration.ApplicationConstants.UNEXPECTED;
import static com.jedivision.acceptance.rest.utils.domain.processing.CaseStatus.SUCCESS;

public abstract class AcceptanceAbstractRunner {
    private static List<FailedAction> failedActions = new LinkedList<>();

    static {
        failedActions.add(new EmptyAction());
        failedActions.add(new FailureAction());
        failedActions.add(new ServerDownAction());
        failedActions.add(new UnexpectedAction());
    }

    @Rule
    public TestRule watcher = new TestWatcher() {

        @Override
        protected void succeeded(Description description) {
            addExecution(description, SUCCESS, EMPTY);
        }

        @Override
        protected void failed(Throwable e, Description description) {
            Optional<FailedAction> action = failedActions.stream().filter(item -> item.select(e)).findFirst();
            if (action.isPresent()) {
                action.get().process(description);
            } else {
                addExecution(description, CaseStatus.UNEXPECTED, UNEXPECTED);
            }
        }
    };

}
