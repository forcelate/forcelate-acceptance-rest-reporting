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

import static com.forcelate.acceptance.test.RandomUtils.randomString;
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
        when(e.getMessage()).thenReturn(randomString());

        // Act
        boolean select = actionUnderTest.select(e);

        // Assert
        assertTrue(select);
    }

    @Test
    public void processWhenWrongResponseBody() {
        // Arrange
        Description description =  mock(Description.class);
        when(e.getMessage()).thenReturn(randomString());

        // Act
        actionUnderTest.select(e);
        actionUnderTest.process(description);

        // Assert
        Optional<Map.Entry<CaseMapping, List<CaseCall>>> optional = ExecutionsHolder.getExecuted().
                entrySet().stream().findFirst();
        if (optional.isPresent()) {
            CaseCall call = optional.get().getValue().get(0);
            assertEquals(CaseStatus.UNEXPECTED, call.getStatus());
        }
    }
}