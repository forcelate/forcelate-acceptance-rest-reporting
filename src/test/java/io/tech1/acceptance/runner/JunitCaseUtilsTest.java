package io.tech1.acceptance.runner;

import io.tech1.acceptance.domain.processing.CaseCall;
import io.tech1.acceptance.domain.processing.CaseMapping;
import io.tech1.acceptance.domain.processing.CaseStatus;
import io.tech1.acceptance.test.CoberturaUtils;
import io.tech1.acceptance.test.RandomUtils;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.Description;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

public class JunitCaseUtilsTest {

    @BeforeClass
    public static void beforeClass() {
        CoberturaUtils.classCoverageHook(JunitCaseUtils.class);
    }

    @Test
    public void getCaseCall() {
        // Arrange
        Description description = mock(Description.class);
        CaseStatus status = RandomUtils.randomEnum(CaseStatus.class);
        String message = RandomUtils.randomString();

        // Act
        CaseCall caseCall = JunitCaseUtils.getCaseCall(description, status, message);

        // Assert
        Assert.assertEquals(JunitDescriptionUtils.UNEXPECTED, caseCall.getDescription());
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
        Assert.assertEquals(JunitDescriptionUtils.UNEXPECTED, caseMapping.getEndpoint());
        Assert.assertEquals(JunitDescriptionUtils.UNEXPECTED, caseMapping.getHttpType());
    }
}
