package com.jedivision.acceptance.rest.utils.runner.action;

import com.jedivision.acceptance.rest.utils.domain.processing.CaseCall;
import com.jedivision.acceptance.rest.utils.domain.processing.CaseMapping;
import com.jedivision.acceptance.rest.utils.domain.processing.CaseStatus;
import com.jedivision.acceptance.rest.utils.holder.ExecutionsHolder;
import org.junit.Test;
import org.junit.runner.Description;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.jedivision.acceptance.rest.utils.configuration.ApplicationErrorMessages.STATUS_CODE_500;
import static com.jedivision.acceptance.rest.utils.configuration.ApplicationErrorMessages.WRONG_RESPONSE_BODY;
import static com.jedivision.acceptance.rest.utils.test.JediRandomUtils.randomString;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FailureActionTest extends AbstractActionRunner{

    @Autowired private Throwable e;

    @Autowired private FailureAction actionUnderTest;

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
    public void selectTrueWhenStatusCode500() {
        // Arrange
        when(e.getMessage()).thenReturn(randomString() + STATUS_CODE_500 + randomString());

        // Act
        boolean select = actionUnderTest.select(e);

        // Assert
        assertTrue(select);
    }

    @Test
    public void selectTrueWhenWrongResponseBody() {
        // Arrange
        when(e.getMessage()).thenReturn(randomString() + WRONG_RESPONSE_BODY + randomString());

        // Act
        boolean select = actionUnderTest.select(e);

        // Assert
        assertTrue(select);
    }

    @Test
    public void processWhenStatusCode500() {
        // Arrange
        Description description =  mock(Description.class);
        when(e.getMessage()).thenReturn(randomString() + STATUS_CODE_500 + randomString());

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

    @Test
    public void processWhenWrongResponseBody() {
        // Arrange
        Description description =  mock(Description.class);
        when(e.getMessage()).thenReturn(randomString() + WRONG_RESPONSE_BODY + randomString());

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
