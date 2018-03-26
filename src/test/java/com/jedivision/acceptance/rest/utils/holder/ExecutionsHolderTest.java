package com.jedivision.acceptance.rest.utils.holder;

import com.jedivision.acceptance.rest.utils.domain.processing.CaseCall;
import com.jedivision.acceptance.rest.utils.domain.processing.CaseMapping;
import com.jedivision.acceptance.rest.utils.domain.processing.CaseStatus;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.Description;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.jedivision.acceptance.rest.utils.test.AccRandomUtils.description;
import static com.jedivision.acceptance.rest.utils.test.JediCoberturaUtils.classCoverageHook;
import static com.jedivision.acceptance.rest.utils.test.JediRandomUtils.randomEnum;
import static com.jedivision.acceptance.rest.utils.test.JediRandomUtils.randomString;
import static com.jedivision.acceptance.rest.utils.test.ReflectionUtils.fieldHook;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

public class ExecutionsHolderTest {

    @BeforeClass
    public static void beforeClass() {
        classCoverageHook(ExecutionsHolder.class);
    }

    @Before
    public void before() throws Exception {
        fieldHook(ExecutionsHolder.class, new HashMap<CaseMapping, List<CaseCall>>(), "executed");
    }

    @Test
    public void addExecutionNotExists() {
        // Arrange
        Description description = mock(Description.class);
        CaseStatus status = randomEnum(CaseStatus.class);
        String message = randomString();

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
        String endpoint = randomString();
        String httpType = randomString();
        Description description1 = description(endpoint, httpType);
        Description description2 = description(endpoint, httpType);
        CaseStatus status = randomEnum(CaseStatus.class);
        String message = randomString();

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
