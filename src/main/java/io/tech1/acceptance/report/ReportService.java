package io.tech1.acceptance.report;

import io.tech1.acceptance.configuration.ApplicationProperties;
import io.tech1.acceptance.domain.processing.*;
import io.tech1.acceptance.holder.ExecutionsHolder;
import io.tech1.acceptance.domain.reporting.Environment;
import io.tech1.acceptance.domain.reporting.MappingsPair;
import io.tech1.acceptance.domain.reporting.Report;
import io.tech1.acceptance.helpers.CountUtils;
import io.tech1.acceptance.helpers.SortUtils;
import io.tech1.acceptance.service.GitService;
import io.tech1.acceptance.service.MappingsService;
import io.tech1.acceptance.configuration.ApplicationConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
public class ReportService {
    private final MappingsService mappingsService;
    private final GitService gitService;
    private final SortUtils sortUtils;
    private final CountUtils countUtils;
    private final ApplicationProperties applicationProperties;

    @Autowired
    public ReportService(MappingsService mappingsService,
                         GitService gitService,
                         SortUtils sortUtils,
                         CountUtils countUtils,
                         ApplicationProperties applicationProperties) {
        this.mappingsService = mappingsService;
        this.gitService = gitService;
        this.sortUtils = sortUtils;
        this.countUtils = countUtils;
        this.applicationProperties = applicationProperties;
    }

    public Report formatReport(FrameworkType frameworkType) {
        // format executions
        List<Execution> executions = ExecutionsHolder.getExecuted().keySet().stream()
                .map(mapping -> {
                    List<CaseCall> calls = ExecutionsHolder.getExecuted().get(mapping);
                    sortUtils.callsByStatus(calls);
                    return new Execution(mapping, calls);
                })
                .collect(toList());
        // format mappings (add MISSING)
        MappingsPair mappingsPair = mappingsService.retrieve(frameworkType);
        List<CaseMapping> mappings = mappingsPair.getMappings();
        mappings.removeAll(new ArrayList<>(ExecutionsHolder.getExecuted().keySet()));
        mappings.forEach(item -> {
            CaseCall call = CaseCall.builder()
                    .description(ApplicationConstants.EMPTY)
                    .status(CaseStatus.MISSING)
                    .message(ApplicationConstants.EMPTY)
                    .build();
            executions.add(new Execution(item, Collections.singletonList(call)));
        });
        // sort executions
        sortUtils.byEndpoint(executions);
        sortUtils.executionsByStatus(executions);
        // environment
        Environment environment = Environment.builder()
                .baseURI(applicationProperties.restAssureBaseURI())
                .port(applicationProperties.restAssurePort())
                .basePath(applicationProperties.restAssureBasePath())
                .build();
        // format report
        return Report.builder()
                .git(gitService.retrieve(frameworkType))
                .environment(environment)
                .mappingsAvailability(mappingsPair.isAvailable())
                .count(countUtils.executionsStatuses(executions))
                .executions(executions)
                .build();
    }
}
