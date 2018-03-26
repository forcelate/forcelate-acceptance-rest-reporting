package com.forcelate.acceptance.holder;

import com.forcelate.acceptance.domain.processing.CaseCall;
import com.forcelate.acceptance.domain.processing.CaseMapping;
import com.forcelate.acceptance.domain.processing.CaseStatus;
import com.forcelate.acceptance.test.AccRandomUtils;
import com.forcelate.acceptance.test.JediCoberturaUtils;
import com.forcelate.acceptance.test.JediRandomUtils;
import com.forcelate.acceptance.test.ReflectionUtils;
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
        JediCoberturaUtils.classCoverageHook(ExecutionsHolder.class);
    }

    @Before
    public void before() throws Exception {
        ReflectionUtils.fieldHook(ExecutionsHolder.class, new HashMap<CaseMapping, List<CaseCall>>(), "executed");
    }

    @Test
    public void addExecutionNotExists() {
        // Arrange
        Description description = mock(Description.class);
        CaseStatus status = JediRandomUtils.randomEnum(CaseStatus.class);
        String message = JediRandomUtils.randomString();

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
        String endpoint = JediRandomUtils.randomString();
        String httpType = JediRandomUtils.randomString();
        Description description1 = AccRandomUtils.description(endpoint, httpType);
        Description description2 = AccRandomUtils.description(endpoint, httpType);
        CaseStatus status = JediRandomUtils.randomEnum(CaseStatus.class);
        String message = JediRandomUtils.randomString();

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
