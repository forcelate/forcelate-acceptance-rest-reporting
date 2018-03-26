package com.forcelate.acceptance.report;

import com.forcelate.acceptance.configuration.ApplicationProperties;
import com.forcelate.acceptance.domain.processing.CaseCall;
import com.forcelate.acceptance.holder.ExecutionsHolder;
import com.forcelate.acceptance.test.AcceptanceUtils;
import com.forcelate.acceptance.domain.processing.CaseMapping;
import com.forcelate.acceptance.domain.processing.CaseStatus;
import com.forcelate.acceptance.domain.processing.Execution;
import com.forcelate.acceptance.domain.reporting.Environment;
import com.forcelate.acceptance.domain.reporting.Git;
import com.forcelate.acceptance.domain.reporting.MappingsPair;
import com.forcelate.acceptance.domain.reporting.Report;
import com.forcelate.acceptance.exception.AcceptanceException;
import com.forcelate.acceptance.helpers.CountUtils;
import com.forcelate.acceptance.helpers.SortUtils;
import com.forcelate.acceptance.service.GitService;
import com.forcelate.acceptance.service.MappingsService;
import org.junit.After;
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

import static com.forcelate.acceptance.domain.processing.CaseStatus.*;
import static com.forcelate.acceptance.test.RandomUtils.*;
import static com.forcelate.acceptance.test.ReflectionUtils.fieldHook;
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
        fieldHook(ExecutionsHolder.class, new HashMap<CaseMapping, List<CaseCall>>(), "executed");
    }

    @After
    public void after() {
        verifyNoMoreInteractions(mappingsService, gitService, sortUtils, countUtils, applicationProperties);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void formatReport() throws AcceptanceException {
        // Arrange
        String endpoint1 = "/api/method1";
        String httpType1 = "GET";
        String endpoint2 = "/api/method2";
        String httpType2 = "GET";
        String endpoint3 = "/api/method3";
        String httpType3 = "GET";
        ExecutionsHolder.addExecution(AcceptanceUtils.description(endpoint1, httpType1), SUCCESS, randomString());
        ExecutionsHolder.addExecution(AcceptanceUtils.description(endpoint2, httpType2), FAILURE, randomString());
        List<CaseMapping> mappings = new ArrayList<>();
        mappings.add(CaseMapping.builder().endpoint(endpoint1).httpType(httpType1).build());
        mappings.add(CaseMapping.builder().endpoint(endpoint2).httpType(httpType2).build());
        mappings.add(CaseMapping.builder().endpoint(endpoint3).httpType(httpType3).build());
        boolean available = randomBoolean();
        MappingsPair mappingsPair = MappingsPair.builder().available(available).mappings(mappings).build();
        when(mappingsService.retrieve()).thenReturn(mappingsPair);
        Git git = mock(Git.class);
        when(gitService.retrieve()).thenReturn(git);
        Map<CaseStatus, Long> count = mock(Map.class);
        when(countUtils.executionsStatuses(anyList())).thenReturn(count);
        String baseURI = randomString();
        int port = randomInteger();
        String basePath = randomString();
        when(applicationProperties.restAssureBaseURI()).thenReturn(baseURI);
        when(applicationProperties.restAssurePort()).thenReturn(port);
        when(applicationProperties.restAssureBasePath()).thenReturn(basePath);
        Environment environment = Environment.builder()
                .baseURI(baseURI)
                .port(port)
                .basePath(basePath)
                .build();

        // Act
        Report report = serviceUnderTest.formatReport();

        // Assert
        assertEquals(git, report.getGit());
        assertEquals(environment, report.getEnvironment());
        assertEquals(available, report.isMappingsAvailability());
        assertEquals(count, report.getCount());
        List<Execution> executions = report.getExecutions();
        assertEquals(3, executions.size());
        Execution execution1 = findExecution(executions, endpoint1);
        assertEquals(1, execution1.getCalls().size());
        assertEquals(SUCCESS, execution1.getCalls().get(0).getStatus());
        Execution execution2 = findExecution(executions, endpoint2);
        assertEquals(1, execution2.getCalls().size());
        assertEquals(FAILURE, execution2.getCalls().get(0).getStatus());
        Execution execution3 = findExecution(executions, endpoint3);
        assertEquals(1, execution3.getCalls().size());
        assertEquals(MISSING, execution3.getCalls().get(0).getStatus());
        verify(sortUtils, times(2)).callsByStatus(anyList());
        verify(mappingsService).retrieve();
        verify(sortUtils).byEndpoint(anyList());
        verify(sortUtils).executionsByStatus(anyList());
        verify(applicationProperties).restAssureBaseURI();
        verify(applicationProperties).restAssurePort();
        verify(applicationProperties).restAssureBasePath();
        verify(gitService).retrieve();
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
