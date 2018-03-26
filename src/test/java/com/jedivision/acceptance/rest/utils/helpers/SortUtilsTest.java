package com.jedivision.acceptance.rest.utils.helpers;

import com.jedivision.acceptance.rest.utils.domain.processing.CaseCall;
import com.jedivision.acceptance.rest.utils.domain.processing.CaseMapping;
import com.jedivision.acceptance.rest.utils.domain.processing.Execution;
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

import static com.jedivision.acceptance.rest.utils.domain.processing.CaseStatus.FAILURE;
import static com.jedivision.acceptance.rest.utils.domain.processing.CaseStatus.SUCCESS;
import static com.jedivision.acceptance.rest.utils.test.JediRandomUtils.randomString;
import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader=AnnotationConfigContextLoader.class)
public class SortUtilsTest {

    @Configuration
    static class ContextConfiguration {

        @Bean
        SortUtils sortUtils() {
            return new SortUtils();
        }
    }

    @Autowired
    private SortUtils utilsUnderTest;

    @Test
    public void byEndpoint() {
        // Arrange
        List<Execution> executions = new ArrayList<>();
        executions.add(Execution.builder()
                .mapping(CaseMapping.builder()
                        .endpoint("/bbb")
                        .build())
                .build());
        executions.add(Execution.builder()
                .mapping(CaseMapping.builder()
                        .endpoint("/aaa")
                        .build())
                .build());
        executions.add(Execution.builder()
                .mapping(CaseMapping.builder()
                        .endpoint("/ccc")
                        .build())
                .build());

        // Act
        utilsUnderTest.byEndpoint(executions);

        // Assert
        assertEquals(executions.get(0).getMapping().getEndpoint(), "/aaa");
        assertEquals(executions.get(1).getMapping().getEndpoint(), "/bbb");
        assertEquals(executions.get(2).getMapping().getEndpoint(), "/ccc");
    }

    @Test
    public void callsByStatus() {
        // Arrange
        List<CaseCall> calls = new ArrayList<>();
        calls.add(CaseCall.builder()
                .description(randomString())
                .status(SUCCESS)
                .build());
        calls.add(CaseCall.builder()
                .description(randomString())
                .status(FAILURE)
                .build());

        // Act
        utilsUnderTest.callsByStatus(calls);

        // Assert
        assertEquals(calls.get(0).getStatus(), FAILURE);
        assertEquals(calls.get(1).getStatus(), SUCCESS);
    }

    @Test
    public void executionsByStatus() {
        // Arrange
        List<CaseCall> calls1 = new ArrayList<>();
        calls1.add(CaseCall.builder()
                .description(randomString())
                .status(SUCCESS)
                .build());
        List<CaseCall> calls2 = new ArrayList<>();
        calls2.add(CaseCall.builder()
                .description(randomString())
                .status(FAILURE)
                .build());

        List<Execution> executions = new ArrayList<>();
        executions.add(Execution.builder()
                .mapping(CaseMapping.builder()
                        .endpoint("/aaa")
                        .build())
                .calls(calls1)
                .build());
        executions.add(Execution.builder()
                .mapping(CaseMapping.builder()
                        .endpoint("/bbb")
                        .build())
                .calls(calls2)
                .build());

        // Act
        utilsUnderTest.executionsByStatus(executions);

        // Assert
        assertEquals(executions.get(0).getMapping().getEndpoint(), "/bbb");
        assertEquals(executions.get(1).getMapping().getEndpoint(), "/aaa");
    }
}
