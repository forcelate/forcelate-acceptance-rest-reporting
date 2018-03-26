package com.forcelate.acceptance.runner.action;

import com.forcelate.acceptance.domain.processing.CaseCall;
import com.forcelate.acceptance.domain.processing.CaseMapping;
import com.forcelate.acceptance.domain.processing.CaseStatus;
import com.forcelate.acceptance.holder.ExecutionsHolder;
import org.junit.Test;
import org.junit.runner.Description;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.forcelate.acceptance.configuration.ApplicationErrorMessages.EMPTY_RESPONSE;
import static com.forcelate.acceptance.test.RandomUtils.randomString;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class EmptyActionTest extends AbstractActionRunner{

    @Autowired private Throwable e;

    @Autowired private EmptyAction actionUnderTest;

    @Test
    public void selectFalse() {
        // Arrange
        when(e.getMessage()).thenReturn(randomString());

        // Act
        boolean select = actionUnderTest.select(e);

        // Assert
        assertFalse(select);
    }

    @Test
    public void selectTrue() {
        // Arrange
        when(e.getMessage()).thenReturn(randomString() + EMPTY_RESPONSE + randomString());

        // Act
        boolean select = actionUnderTest.select(e);

        // Assert
        assertTrue(select);
    }

    @Test
    public void process() {
        // Arrange
        Description description =  mock(Description.class);
        when(e.getMessage()).thenReturn(randomString() + EMPTY_RESPONSE + randomString());

        // Act
        actionUnderTest.select(e);
        actionUnderTest.process(description);

        // Assert
        Optional<Map.Entry<CaseMapping, List<CaseCall>>> optional = ExecutionsHolder.getExecuted().
                entrySet().stream().findFirst();
        if (optional.isPresent()) {
            CaseCall call = optional.get().getValue().get(0);
            assertEquals(CaseStatus.EMPTY, call.getStatus());
        }
        ExecutionsHolder.getExecuted().clear();
    }
}
