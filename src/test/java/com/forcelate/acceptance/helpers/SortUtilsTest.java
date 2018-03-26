package com.forcelate.acceptance.helpers;

import com.forcelate.acceptance.domain.processing.CaseCall;
import com.forcelate.acceptance.domain.processing.CaseMapping;
import com.forcelate.acceptance.domain.processing.CaseStatus;
import com.forcelate.acceptance.domain.processing.Execution;
import com.forcelate.acceptance.test.JediRandomUtils;
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
                .description(JediRandomUtils.randomString())
                .status(CaseStatus.SUCCESS)
                .build());
        calls.add(CaseCall.builder()
                .description(JediRandomUtils.randomString())
                .status(CaseStatus.FAILURE)
                .build());

        // Act
        utilsUnderTest.callsByStatus(calls);

        // Assert
        Assert.assertEquals(calls.get(0).getStatus(), CaseStatus.FAILURE);
        Assert.assertEquals(calls.get(1).getStatus(), CaseStatus.SUCCESS);
    }

    @Test
    public void executionsByStatus() {
        // Arrange
        List<CaseCall> calls1 = new ArrayList<>();
        calls1.add(CaseCall.builder()
                .description(JediRandomUtils.randomString())
                .status(CaseStatus.SUCCESS)
                .build());
        List<CaseCall> calls2 = new ArrayList<>();
        calls2.add(CaseCall.builder()
                .description(JediRandomUtils.randomString())
                .status(CaseStatus.FAILURE)
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
