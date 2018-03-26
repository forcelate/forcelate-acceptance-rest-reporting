package com.jedivision.acceptance.rest.utils.runner;

import com.jedivision.acceptance.rest.utils.domain.processing.CaseCall;
import com.jedivision.acceptance.rest.utils.domain.processing.CaseMapping;
import com.jedivision.acceptance.rest.utils.domain.processing.CaseStatus;
import com.jedivision.acceptance.rest.utils.test.JediCoberturaUtils;
import com.jedivision.acceptance.rest.utils.test.JediRandomUtils;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.Description;

import static com.jedivision.acceptance.rest.utils.runner.JunitDescriptionUtils.UNEXPECTED;
import static com.jedivision.acceptance.rest.utils.test.JediCoberturaUtils.classCoverageHook;
import static com.jedivision.acceptance.rest.utils.test.JediRandomUtils.randomEnum;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

public class JunitCaseUtilsTest {

    @BeforeClass
    public static void beforeClass() {
        classCoverageHook(JunitCaseUtils.class);
    }

    @Test
    public void getCaseCall() {
        // Arrange
        Description description = mock(Description.class);
        CaseStatus status = randomEnum(CaseStatus.class);
        String message = JediRandomUtils.randomString();

        // Act
        CaseCall caseCall = JunitCaseUtils.getCaseCall(description, status, message);

        // Assert
        assertEquals(UNEXPECTED, caseCall.getDescription());
        assertEquals(status, caseCall.getStatus());
        assertEquals(message, caseCall.getMessage());
    }

    @Test
    public void getCaseMethod() {
        // Arrange
        Description description = mock(Description.class);

        // Act
        CaseMapping caseMapping = JunitCaseUtils.getCaseMapping(description);

        // Assert
        assertEquals(UNEXPECTED, caseMapping.getEndpoint());
        assertEquals(UNEXPECTED, caseMapping.getHttpType());
    }
}
