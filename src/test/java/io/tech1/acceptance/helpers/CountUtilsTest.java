package io.tech1.acceptance.helpers;

import io.tech1.acceptance.domain.processing.Execution;
import io.tech1.acceptance.test.AcceptanceUtils;
import io.tech1.acceptance.domain.processing.CaseStatus;
import io.tech1.acceptance.configuration.ApplicationConstants;
import io.tech1.acceptance.test.RandomUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader=AnnotationConfigContextLoader.class)
public class CountUtilsTest {

    @Configuration
    static class ContextConfiguration {

        @Bean
        CountUtils countUtils() {
            return new CountUtils();
        }
    }

    @Autowired
    private CountUtils utilsUnderTest;

    @Test
    public void executionsStatusesNull() {
        // Act
        Map<CaseStatus, Long> result = utilsUnderTest.executionsStatuses(null);

        // Assert
        assertTrue(result.size() == 0);
    }

    @Test
    public void executionsStatuses() {
        // Arrange
        int serverDowns = RandomUtils.randomSmallInteger();
        int failures = RandomUtils.randomSmallInteger();
        int unexpected = RandomUtils.randomSmallInteger();
        int missing = RandomUtils.randomSmallInteger();
        int success = RandomUtils.randomSmallInteger();
        List<Execution> executions = new ArrayList<>();
        // each status
        executions.add(AcceptanceUtils.randomExecutionByStatusCount(CaseStatus.SERVER_DOWN, serverDowns));
        executions.add(AcceptanceUtils.randomExecutionByStatusCount(CaseStatus.FAILURE, failures));
        executions.add(AcceptanceUtils.randomExecutionByStatusCount(CaseStatus.UNEXPECTED, unexpected));
        executions.add(AcceptanceUtils.randomExecutionByStatusCount(CaseStatus.MISSING, missing));
        executions.add(AcceptanceUtils.randomExecutionByStatusCount(CaseStatus.SUCCESS, success));
        // UNEXPECTED, SUCCESS status separate
        executions.add(AcceptanceUtils.randomExecutionByStatuses(CaseStatus.UNEXPECTED, CaseStatus.SUCCESS));

        // Act
        Map<CaseStatus, Long> result = utilsUnderTest.executionsStatuses(executions);

        // Assert
        assertEquals(serverDowns, result.get(CaseStatus.SERVER_DOWN).intValue());
        assertEquals(failures, result.get(CaseStatus.FAILURE).intValue());
        assertEquals(unexpected + 1, result.get(CaseStatus.UNEXPECTED).intValue());
        Assert.assertEquals(ApplicationConstants.UNCERTAIN_COUNT, result.get(CaseStatus.EMPTY).intValue());
        assertEquals(missing, result.get(CaseStatus.MISSING).intValue());
        assertEquals(success + 1, result.get(CaseStatus.SUCCESS).intValue());
        assertTrue(result instanceof TreeMap);
    }
}
