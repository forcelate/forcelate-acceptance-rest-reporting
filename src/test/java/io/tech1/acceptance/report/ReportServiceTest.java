package io.tech1.acceptance.report;

import io.tech1.acceptance.configuration.ApplicationProperties;
import io.tech1.acceptance.holder.ExecutionsHolder;
import io.tech1.acceptance.test.AcceptanceUtils;
import io.tech1.acceptance.domain.reporting.Environment;
import io.tech1.acceptance.domain.reporting.Git;
import io.tech1.acceptance.domain.reporting.MappingsPair;
import io.tech1.acceptance.domain.reporting.Report;
import io.tech1.acceptance.exception.AcceptanceException;
import io.tech1.acceptance.helpers.CountUtils;
import io.tech1.acceptance.helpers.SortUtils;
import io.tech1.acceptance.service.GitService;
import io.tech1.acceptance.service.MappingsService;
import io.tech1.acceptance.domain.processing.*;
import io.tech1.acceptance.test.RandomUtils;
import io.tech1.acceptance.test.ReflectionUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader=AnnotationConfigContextLoader.class)
public class ReportServiceTest {

    @Configuration
    static class ContextConfiguration {

        @Bean
        MappingsService mappingsService() {
            return mock(MappingsService.class);
        }

        @Bean
        GitService gitService() {
            return mock(GitService.class);
        }

        @Bean
        SortUtils sortUtils() {
            return mock(SortUtils.class);
        }

        @Bean
        CountUtils countUtils() {
            return mock(CountUtils.class);
        }

        @Bean
        ApplicationProperties applicationProperties() {
            return mock(ApplicationProperties.class);
        }

        @Bean
        ReportService reportService() {
            return new ReportService(mappingsService(), gitService(), sortUtils(), countUtils(), applicationProperties());
        }
    }

    @Autowired private MappingsService mappingsService;
    @Autowired private GitService gitService;
    @Autowired private SortUtils sortUtils;
    @Autowired private CountUtils countUtils;
    @Autowired private ApplicationProperties applicationProperties;

    @Autowired private ReportService serviceUnderTest;

    @Before
    public void before() throws Exception {
        reset(mappingsService, gitService, sortUtils, countUtils, applicationProperties);
        ReflectionUtils.fieldHook(ExecutionsHolder.class, new HashMap<CaseMapping, List<CaseCall>>(), "executed");
    }

    @After
    public void after() {
        verifyNoMoreInteractions(mappingsService, gitService, sortUtils, countUtils, applicationProperties);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void formatReportSpringBootV1() throws AcceptanceException {
        // Arrange
        String endpoint1 = "/api/method1";
        String httpType1 = "GET";
        String endpoint2 = "/api/method2";
        String httpType2 = "GET";
        String endpoint3 = "/api/method3";
        String httpType3 = "GET";
        ExecutionsHolder.addExecution(AcceptanceUtils.description(endpoint1, httpType1), CaseStatus.SUCCESS, RandomUtils.randomString());
        ExecutionsHolder.addExecution(AcceptanceUtils.description(endpoint2, httpType2), CaseStatus.FAILURE, RandomUtils.randomString());
        List<CaseMapping> mappings = new ArrayList<>();
        mappings.add(CaseMapping.builder().endpoint(endpoint1).httpType(httpType1).build());
        mappings.add(CaseMapping.builder().endpoint(endpoint2).httpType(httpType2).build());
        mappings.add(CaseMapping.builder().endpoint(endpoint3).httpType(httpType3).build());
        boolean available = RandomUtils.randomBoolean();
        MappingsPair mappingsPair = MappingsPair.builder().available(available).mappings(mappings).build();
        when(mappingsService.retrieve(FrameworkType.SPRING_BOOT_V1)).thenReturn(mappingsPair);
        Git git = mock(Git.class);
        when(gitService.retrieve(FrameworkType.SPRING_BOOT_V1)).thenReturn(git);
        Map<CaseStatus, Long> count = mock(Map.class);
        when(countUtils.executionsStatuses(anyList())).thenReturn(count);
        String baseURI = RandomUtils.randomString();
        int port = RandomUtils.randomInteger();
        String basePath = RandomUtils.randomString();
        when(applicationProperties.restAssureBaseURI()).thenReturn(baseURI);
        when(applicationProperties.restAssurePort()).thenReturn(port);
        when(applicationProperties.restAssureBasePath()).thenReturn(basePath);
        Environment environment = Environment.builder()
                .baseURI(baseURI)
                .port(port)
                .basePath(basePath)
                .build();

        // Act
        Report report = serviceUnderTest.formatReport(FrameworkType.SPRING_BOOT_V1);

        // Assert
        assertEquals(git, report.getGit());
        assertEquals(environment, report.getEnvironment());
        assertEquals(available, report.isMappingsAvailability());
        assertEquals(count, report.getCount());
        List<Execution> executions = report.getExecutions();
        assertEquals(3, executions.size());
        Execution execution1 = findExecution(executions, endpoint1);
        assertEquals(1, execution1.getCalls().size());
        Assert.assertEquals(CaseStatus.SUCCESS, execution1.getCalls().get(0).getStatus());
        Execution execution2 = findExecution(executions, endpoint2);
        assertEquals(1, execution2.getCalls().size());
        Assert.assertEquals(CaseStatus.FAILURE, execution2.getCalls().get(0).getStatus());
        Execution execution3 = findExecution(executions, endpoint3);
        assertEquals(1, execution3.getCalls().size());
        Assert.assertEquals(CaseStatus.MISSING, execution3.getCalls().get(0).getStatus());
        verify(sortUtils, times(2)).callsByStatus(anyList());
        verify(mappingsService).retrieve(FrameworkType.SPRING_BOOT_V1);
        verify(sortUtils).byEndpoint(anyList());
        verify(sortUtils).executionsByStatus(anyList());
        verify(applicationProperties).restAssureBaseURI();
        verify(applicationProperties).restAssurePort();
        verify(applicationProperties).restAssureBasePath();
        verify(gitService).retrieve(FrameworkType.SPRING_BOOT_V1);
        verify(countUtils).executionsStatuses(anyList());
    }

    @Test
    @SuppressWarnings("unchecked")
    public void formatReportSpringBootV2() throws AcceptanceException {
        // Arrange
        String endpoint1 = "/api/method1";
        String httpType1 = "GET";
        String endpoint2 = "/api/method2";
        String httpType2 = "GET";
        String endpoint3 = "/api/method3";
        String httpType3 = "GET";
        ExecutionsHolder.addExecution(AcceptanceUtils.description(endpoint1, httpType1), CaseStatus.SUCCESS, RandomUtils.randomString());
        ExecutionsHolder.addExecution(AcceptanceUtils.description(endpoint2, httpType2), CaseStatus.FAILURE, RandomUtils.randomString());
        List<CaseMapping> mappings = new ArrayList<>();
        mappings.add(CaseMapping.builder().endpoint(endpoint1).httpType(httpType1).build());
        mappings.add(CaseMapping.builder().endpoint(endpoint2).httpType(httpType2).build());
        mappings.add(CaseMapping.builder().endpoint(endpoint3).httpType(httpType3).build());
        boolean available = RandomUtils.randomBoolean();
        MappingsPair mappingsPair = MappingsPair.builder().available(available).mappings(mappings).build();
        when(mappingsService.retrieve(FrameworkType.SPRING_BOOT_V2)).thenReturn(mappingsPair);
        Git git = mock(Git.class);
        when(gitService.retrieve(FrameworkType.SPRING_BOOT_V2)).thenReturn(git);
        Map<CaseStatus, Long> count = mock(Map.class);
        when(countUtils.executionsStatuses(anyList())).thenReturn(count);
        String baseURI = RandomUtils.randomString();
        int port = RandomUtils.randomInteger();
        String basePath = RandomUtils.randomString();
        when(applicationProperties.restAssureBaseURI()).thenReturn(baseURI);
        when(applicationProperties.restAssurePort()).thenReturn(port);
        when(applicationProperties.restAssureBasePath()).thenReturn(basePath);
        Environment environment = Environment.builder()
                .baseURI(baseURI)
                .port(port)
                .basePath(basePath)
                .build();

        // Act
        Report report = serviceUnderTest.formatReport(FrameworkType.SPRING_BOOT_V2);

        // Assert
        assertEquals(git, report.getGit());
        assertEquals(environment, report.getEnvironment());
        assertEquals(available, report.isMappingsAvailability());
        assertEquals(count, report.getCount());
        List<Execution> executions = report.getExecutions();
        assertEquals(3, executions.size());
        Execution execution1 = findExecution(executions, endpoint1);
        assertEquals(1, execution1.getCalls().size());
        Assert.assertEquals(CaseStatus.SUCCESS, execution1.getCalls().get(0).getStatus());
        Execution execution2 = findExecution(executions, endpoint2);
        assertEquals(1, execution2.getCalls().size());
        Assert.assertEquals(CaseStatus.FAILURE, execution2.getCalls().get(0).getStatus());
        Execution execution3 = findExecution(executions, endpoint3);
        assertEquals(1, execution3.getCalls().size());
        Assert.assertEquals(CaseStatus.MISSING, execution3.getCalls().get(0).getStatus());
        verify(sortUtils, times(2)).callsByStatus(anyList());
        verify(mappingsService).retrieve(FrameworkType.SPRING_BOOT_V2);
        verify(sortUtils).byEndpoint(anyList());
        verify(sortUtils).executionsByStatus(anyList());
        verify(applicationProperties).restAssureBaseURI();
        verify(applicationProperties).restAssurePort();
        verify(applicationProperties).restAssureBasePath();
        verify(gitService).retrieve(FrameworkType.SPRING_BOOT_V2);
        verify(countUtils).executionsStatuses(anyList());
    }

    private Execution findExecution(List<Execution> executions, String endpoint) {
        Optional<Execution> execution = executions.stream()
                .filter(item -> endpoint.equals(item.getMapping().getEndpoint()))
                .findFirst();
        if (execution.isPresent()) {
            return execution.get();
        } else {
            throw new RuntimeException("No execution by endpoint...Please check Assert setup");
        }
    }
}
