package io.tech1.acceptance.runner.action;

import io.tech1.acceptance.domain.processing.CaseCall;
import io.tech1.acceptance.domain.processing.CaseMapping;
import io.tech1.acceptance.domain.processing.CaseStatus;
import io.tech1.acceptance.holder.ExecutionsHolder;
import io.tech1.acceptance.test.RandomUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.Description;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UnexpectedActionTest extends AbstractActionRunner{

    @Autowired private Throwable e;

    @Autowired private UnexpectedAction actionUnderTest;

    @Test
    public void selectTrue() {
        // Arrange
        when(e.getMessage()).thenReturn(RandomUtils.randomString());

        // Act
        boolean select = actionUnderTest.select(e);

        // Assert
        assertTrue(select);
    }

    @Test
    public void processWhenWrongResponseBody() {
        // Arrange
        Description description =  mock(Description.class);
        when(e.getMessage()).thenReturn(RandomUtils.randomString());

        // Act
        actionUnderTest.select(e);
        actionUnderTest.process(description);

        // Assert
        Optional<Map.Entry<CaseMapping, List<CaseCall>>> optional = ExecutionsHolder.getExecuted().
                entrySet().stream().findFirst();
        if (optional.isPresent()) {
            CaseCall call = optional.get().getValue().get(0);
            Assert.assertEquals(CaseStatus.UNEXPECTED, call.getStatus());
        }
    }
}