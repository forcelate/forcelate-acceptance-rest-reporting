package com.forcelate.acceptance.report;

import com.forcelate.acceptance.configuration.ApplicationProperties;
import com.forcelate.acceptance.domain.processing.CaseCall;
import com.forcelate.acceptance.holder.ExecutionsHolder;
import com.forcelate.acceptance.domain.processing.CaseMapping;
import com.forcelate.acceptance.domain.processing.CaseStatus;
import com.forcelate.acceptance.domain.processing.Execution;
import com.forcelate.acceptance.domain.reporting.Environment;
import com.forcelate.acceptance.domain.reporting.MappingsPair;
import com.forcelate.acceptance.domain.reporting.Report;
import com.forcelate.acceptance.helpers.CountUtils;
import com.forcelate.acceptance.helpers.SortUtils;
import com.forcelate.acceptance.service.GitService;
import com.forcelate.acceptance.service.MappingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.forcelate.acceptance.configuration.ApplicationConstants.EMPTY;
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

    public Report formatReport() {
        // format executions
        List<Execution> executions = ExecutionsHolder.getExecuted().keySet().stream()
                .map(mapping -> {
                    List<CaseCall> calls = ExecutionsHolder.getExecuted().get(mapping);
                    sortUtils.callsByStatus(calls);
                    return new Execution(mapping, calls);
                })
                .collect(toList());
        // format mappings (add MISSING)
        MappingsPair mappingsPair = mappingsService.retrieve();
        List<CaseMapping> mappings = mappingsPair.getMappings();
        mappings.removeAll(new ArrayList<>(ExecutionsHolder.getExecuted().keySet()));
        mappings.forEach(item -> {
            CaseCall call = CaseCall.builder()
                    .description(EMPTY)
                    .status(CaseStatus.MISSING)
                    .message(EMPTY)
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
                .git(gitService.retrieve())
                .environment(environment)
                .mappingsAvailability(mappingsPair.isAvailable())
                .count(countUtils.executionsStatuses(executions))
                .executions(executions)
                .build();
    }
}
