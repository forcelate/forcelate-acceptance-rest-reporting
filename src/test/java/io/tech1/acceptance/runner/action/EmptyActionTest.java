package io.tech1.acceptance.runner.action;

import io.tech1.acceptance.domain.processing.CaseCall;
import io.tech1.acceptance.domain.processing.CaseMapping;
import io.tech1.acceptance.domain.processing.CaseStatus;
import io.tech1.acceptance.holder.ExecutionsHolder;
import io.tech1.acceptance.configuration.ApplicationErrorMessages;
import io.tech1.acceptance.test.RandomUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.Description;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class EmptyActionTest extends AbstractActionRunner{

    @Autowired private Throwable e;

    @Autowired private EmptyAction actionUnderTest;

    @Test
    public void selectFalse() {
        // Arrange
        when(e.getMessage()).thenReturn(RandomUtils.randomString());

        // Act
        boolean select = actionUnderTest.select(e);

        // Assert
        assertFalse(select);
    }

    @Test
    public void selectTrue() {
        // Arrange
        when(e.getMessage()).thenReturn(RandomUtils.randomString() + ApplicationErrorMessages.EMPTY_RESPONSE + RandomUtils.randomString());

        // Act
        boolean select = actionUnderTest.select(e);

        // Assert
        assertTrue(select);
    }

    @Test
    public void process() {
        // Arrange
        Description description =  mock(Description.class);
        when(e.getMessage()).thenReturn(RandomUtils.randomString() + ApplicationErrorMessages.EMPTY_RESPONSE + RandomUtils.randomString());

        // Act
        actionUnderTest.select(e);
        actionUnderTest.process(description);

        // Assert
        Optional<Map.Entry<CaseMapping, List<CaseCall>>> optional = ExecutionsHolder.getExecuted().
                entrySet().stream().findFirst();
        if (optional.isPresent()) {
            CaseCall call = optional.get().getValue().get(0);
            Assert.assertEquals(CaseStatus.EMPTY, call.getStatus());
        }
        ExecutionsHolder.getExecuted().clear();
    }
}
