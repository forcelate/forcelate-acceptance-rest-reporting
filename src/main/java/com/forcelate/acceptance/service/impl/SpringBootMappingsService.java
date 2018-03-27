package com.forcelate.acceptance.service.impl;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.forcelate.acceptance.domain.processing.CaseMapping;
import com.forcelate.acceptance.domain.reporting.MappingsPair;
import com.forcelate.acceptance.helpers.BracketsUtils;
import com.forcelate.acceptance.service.MappingsService;
import com.forcelate.acceptance.support.RestAssureSupport;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.net.ConnectException;
import java.util.*;
import java.util.stream.Collectors;

import static com.forcelate.acceptance.configuration.ApplicationConstants.SPRING_BOOT_MAPPING_URL;

@Slf4j
@Service
public class SpringBootMappingsService implements MappingsService {
    private static final String MAPPING_VALUE_SEPARATOR = ",";
    private static final int ENDPOINT_POSITION = 0;
    private static final int HTTP_TYPE_POSITION = 1;

    private static Set<String> springBootFrameworkMappings =
            new HashSet<>(Arrays.asList("/**", "/webjars/**", "/error", "/metrics", "/logfile",
                    "/metrics", "/configprops", "/mappings", "/trace", "/autoconfig", "/env",
                    "/health", "/beans", "/dump", "/info", "/api/api-docs", "/auditevents",
                    "/flyway", "/heapdump", "/loggers"));

    private static final Gson gson = new Gson();

    private final BracketsUtils bracketsUtils;
    private final RestAssureSupport restAssureSupport;

    @Autowired
    public SpringBootMappingsService(BracketsUtils bracketsUtils, RestAssureSupport restAssureSupport) {
        this.bracketsUtils = bracketsUtils;
        this.restAssureSupport = restAssureSupport;
    }

    @Override
    public MappingsPair retrieve() {
        try {
            Type type = new TypeToken<Map<String, Map<String, String>>>() {}.getType();
            String json = restAssureSupport.getResponseJSON(SPRING_BOOT_MAPPING_URL);
            Map<String, Map<String, String>> response = gson.fromJson(json, type);

            Set<String> skipMappings = response.keySet().stream()
                    .filter(mapping -> springBootFrameworkMappings.stream().anyMatch(mapping::contains))
                    .collect(Collectors.toSet());

            response.keySet().removeAll(skipMappings);

            List<CaseMapping> mappings = response.keySet().stream()
                    .map(mapping -> mapping.split(MAPPING_VALUE_SEPARATOR))
                    .filter(analyze -> analyze.length >= 2)
                    .map(analyze -> CaseMapping.builder()
                            .endpoint(bracketsUtils.between(analyze[ENDPOINT_POSITION]))
                            .httpType(bracketsUtils.between(analyze[HTTP_TYPE_POSITION]))
                            .build()
                    )
                    .collect(Collectors.toList());
            return MappingsPair.builder()
                    .available(true)
                    .mappings(mappings)
                    .build();
        } catch (ConnectException e) {
            log.error("Spring mappings endpoint is unavailable {}", e);
            return MappingsPair.builder()
                    .available(false)
                    .mappings(Collections.emptyList())
                    .build();
        }
    }
}
