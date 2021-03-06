package io.tech1.acceptance.holder;

import io.tech1.acceptance.domain.processing.CaseCall;
import io.tech1.acceptance.domain.processing.CaseMapping;
import io.tech1.acceptance.domain.processing.CaseStatus;
import io.tech1.acceptance.test.AcceptanceUtils;
import io.tech1.acceptance.test.CoberturaUtils;
import io.tech1.acceptance.test.RandomUtils;
import io.tech1.acceptance.test.ReflectionUtils;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.Description;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

public class ExecutionsHolderTest {

    @BeforeClass
    public static void beforeClass() {
        CoberturaUtils.classCoverageHook(ExecutionsHolder.class);
    }

    @Before
    public void before() throws Exception {
        ReflectionUtils.fieldHook(ExecutionsHolder.class, new HashMap<CaseMapping, List<CaseCall>>(), "executed");
    }

    @Test
    public void addExecutionNotExists() {
        // Arrange
        Description description = mock(Description.class);
        CaseStatus status = RandomUtils.randomEnum(CaseStatus.class);
        String message = RandomUtils.randomString();

        // Act
        ExecutionsHolder.addExecution(description, status, message);

        // Assert
        Map<CaseMapping, List<CaseCall>> executed = ExecutionsHolder.getExecuted();
        CaseMapping mapping = executed.keySet().iterator().next();
        assertEquals(1, executed.size());
        assertEquals(1, executed.get(mapping).size());
    }

    @Test
    public void addExecutionExists() {
        // Arrange
        String endpoint = RandomUtils.randomString();
        String httpType = RandomUtils.randomString();
        Description description1 = AcceptanceUtils.description(endpoint, httpType);
        Description description2 = AcceptanceUtils.description(endpoint, httpType);
        CaseStatus status = RandomUtils.randomEnum(CaseStatus.class);
        String message = RandomUtils.randomString();

        // Act
        ExecutionsHolder.addExecution(description1, status, message);
        ExecutionsHolder.addExecution(description2, status, message);

        // Assert
        Map<CaseMapping, List<CaseCall>> executed = ExecutionsHolder.getExecuted();
        CaseMapping mapping = executed.keySet().iterator().next();
        assertEquals(1, executed.size());
        assertEquals(2, executed.get(mapping).size());
    }
}
