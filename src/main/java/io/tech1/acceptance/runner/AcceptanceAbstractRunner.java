package io.tech1.acceptance.runner;

import io.tech1.acceptance.domain.processing.CaseStatus;
import io.tech1.acceptance.configuration.ApplicationConstants;
import io.tech1.acceptance.runner.action.*;
import org.junit.Rule;
import org.junit.rules.TestRule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static io.tech1.acceptance.domain.processing.CaseStatus.SUCCESS;
import static io.tech1.acceptance.holder.ExecutionsHolder.addExecution;

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
            addExecution(description, SUCCESS, ApplicationConstants.EMPTY);
        }

        @Override
        protected void failed(Throwable e, Description description) {
            Optional<FailedAction> action = failedActions.stream().filter(item -> item.select(e)).findFirst();
            if (action.isPresent()) {
                action.get().process(description);
            } else {
                addExecution(description, CaseStatus.UNEXPECTED, ApplicationConstants.UNEXPECTED);
            }
        }
    };

}
