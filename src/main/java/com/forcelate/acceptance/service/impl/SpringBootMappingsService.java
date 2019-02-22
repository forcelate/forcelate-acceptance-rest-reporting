package com.forcelate.acceptance.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.forcelate.acceptance.domain.processing.CaseMapping;
import com.forcelate.acceptance.domain.reporting.MappingsPair;
import com.forcelate.acceptance.dto.Details.DetailsItems;
import com.forcelate.acceptance.dto.DispatcherServlet;
import com.forcelate.acceptance.dto.Example;
import com.forcelate.acceptance.helpers.BracketsUtils;
import com.forcelate.acceptance.service.MappingsService;
import com.forcelate.acceptance.support.RestAssureSupport;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.ConnectException;
import java.util.*;
import java.util.stream.Collectors;

import static com.forcelate.acceptance.configuration.ApplicationConstants.SPRING_BOOT_MAPPING_URL;

@Slf4j
@Service
public class SpringBootMappingsService implements MappingsService {

	private static Set<String> springBootFrameworkMappings =
			new HashSet<>(Arrays.asList("/**", "/webjars/**", "/error", "/metrics", "/logfile",
					"/metrics", "/configprops", "/mappings", "/trace", "/autoconfig", "/env",
					"/health", "/beans", "/dump", "/info", "/api/api-docs", "/auditevents",
					"/flyway", "/heapdump", "/loggers", "/swagger-resources/configuration/security",
					"/actuator","/actuator/mappings","/actuator/info","/actuator/health"));


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
			String json = restAssureSupport.getResponseJSON(SPRING_BOOT_MAPPING_URL);

			ObjectMapper objectMapper = new ObjectMapper();
			Example example = objectMapper.readValue(json, Example.class);
			List<DispatcherServlet> dispatcherServlet = example.getContexts().getApplication().getMappings().getDispatcherServlets().getDispatcherServlet();
			List<CaseMapping> mappings = dispatcherServlet.stream()
					.map(DispatcherServlet::getDetails)
					.filter(Objects::nonNull)
					.map(DetailsItems::getRequestMappingConditions)
					.filter(Objects::nonNull)
					.filter(r -> Objects.nonNull(r.getMethods()) && Objects.nonNull(r.getPatterns()) && !r.getPatterns().isEmpty() && !r.getMethods().isEmpty())
					.filter(mapping -> springBootFrameworkMappings.stream()
							.noneMatch(p -> mapping.getPatterns()
									.contains(p)))
					.map(requestMappingConditions -> {
						List<String> conditionsMethods = requestMappingConditions.getMethods();
						List<String> patterns = requestMappingConditions.getPatterns();

						return CaseMapping.builder()
								.endpoint(patterns.get(0))
								.httpType(conditionsMethods.get(0))
								.build();
					})
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
		} catch (IOException e) {
			log.error("Could not generate report: {}", e);
			return MappingsPair.builder()
					.available(false)
					.mappings(Collections.emptyList())
					.build();
		}
	}
}
