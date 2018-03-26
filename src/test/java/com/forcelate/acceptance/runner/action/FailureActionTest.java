package com.forcelate.acceptance.runner.action;

import com.forcelate.acceptance.configuration.ApplicationErrorMessages;
import com.forcelate.acceptance.domain.processing.CaseCall;
import com.forcelate.acceptance.domain.processing.CaseMapping;
import com.forcelate.acceptance.domain.processing.CaseStatus;
import com.forcelate.acceptance.holder.ExecutionsHolder;
import com.forcelate.acceptance.test.JediRandomUtils;
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

public class FailureActionTest extends AbstractActionRunner{

    @Autowired private Throwable e;

    @Autowired private FailureAction actionUnderTest;

    @Test
    public void selectFalse() {
        // Arrange
        when(e.getMessage()).thenReturn(JediRandomUtils.randomString());

        // Act
        boolean select = actionUnderTest.select(e);

        // Assert
        assertFalse(select);
    }

    @Test
    public void selectTrueWhenStatusCode500() {
        // Arrange
        when(e.getMessage()).thenReturn(JediRandomUtils.randomString() + ApplicationErrorMessages.STATUS_CODE_500 + JediRandomUtils.randomString());

        // Act
        boolean select = actionUnderTest.select(e);

        // Assert
        assertTrue(select);
    }

    @Test
    public void selectTrueWhenWrongResponseBody() {
        // Arrange
        when(e.getMessage()).thenReturn(JediRandomUtils.randomString() + ApplicationErrorMessages.WRONG_RESPONSE_BODY + JediRandomUtils.randomString());

        // Act
        boolean select = actionUnderTest.select(e);

        // Assert
        assertTrue(select);
    }

    @Test
    public void processWhenStatusCode500() {
        // Arrange
        Description description =  mock(Description.class);
        when(e.getMessage()).thenReturn(JediRandomUtils.randomString() + ApplicationErrorMessages.STATUS_CODE_500 + JediRandomUtils.randomString());

        // Act
        actionUnderTest.select(e);
        actionUnderTest.process(description);

        // Assert
        Optional<Map.Entry<CaseMapping, List<CaseCall>>> optional = ExecutionsHolder.getExecuted().
                entrySet().stream().findFirst();
        if (optional.isPresent()) {
            CaseCall call = optional.get().getValue().get(0);
            Assert.assertEquals(CaseStatus.FAILURE, call.getStatus());
        }
    }

    @Test
    public void processWhenWrongResponseBody() {
        // Arrange
        Description description =  mock(Description.class);
        when(e.getMessage()).thenReturn(JediRandomUtils.randomString() + ApplicationErrorMessages.WRONG_RESPONSE_BODY + JediRandomUtils.randomString());

        // Act
        actionUnderTest.select(e);
        actionUnderTest.process(description);

        // Assert
        Optional<Map.Entry<CaseMapping, List<CaseCall>>> optional = ExecutionsHolder.getExecuted().
                entrySet().stream().findFirst();
        if (optional.isPresent()) {
            CaseCall call = optional.get().getValue().get(0);
            assertEquals(CaseStatus.FAILURE, call.getStatus());
        }
    }
}
