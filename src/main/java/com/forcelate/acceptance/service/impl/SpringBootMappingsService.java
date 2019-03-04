package com.forcelate.acceptance.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.forcelate.acceptance.domain.processing.CaseMapping;
import com.forcelate.acceptance.domain.processing.FrameworkType;
import com.forcelate.acceptance.domain.reporting.MappingsPair;
import com.forcelate.acceptance.dto.Details.DetailsItems;
import com.forcelate.acceptance.dto.DispatcherServlet;
import com.forcelate.acceptance.dto.Example;
import com.forcelate.acceptance.helpers.BracketsUtils;
import com.forcelate.acceptance.service.MappingsService;
import com.forcelate.acceptance.support.RestAssureSupport;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.ConnectException;
import java.util.*;
import java.util.stream.Collectors;

import static com.forcelate.acceptance.configuration.ApplicationConstants.SPRING_BOOT_MAPPING_URL_V1;
import static com.forcelate.acceptance.configuration.ApplicationConstants.SPRING_BOOT_MAPPING_URL_V2;

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
					"/flyway", "/heapdump", "/loggers", "/swagger-resources/configuration/security",
					"/actuator","/actuator/mappings","/actuator/info","/actuator/health"));

	private static final Gson gson = new Gson();
	private final BracketsUtils bracketsUtils;
	private final RestAssureSupport restAssureSupport;

	@Autowired
	public SpringBootMappingsService(BracketsUtils bracketsUtils, RestAssureSupport restAssureSupport) {
		this.bracketsUtils = bracketsUtils;
		this.restAssureSupport = restAssureSupport;
	}

	@Override
	public MappingsPair retrieve(FrameworkType frameworkType) {
		try {

			String json = restAssureSupport.getResponseJSON(frameworkType.equals(FrameworkType.SPRING_BOOT_V1)? SPRING_BOOT_MAPPING_URL_V1 : SPRING_BOOT_MAPPING_URL_V2);

			List<CaseMapping> mappings;
			if (frameworkType.equals(FrameworkType.SPRING_BOOT_V1)) {
				Type type = new TypeToken<Map<String, Map<String, String>>>() {}.getType();
				Map<String, Map<String, String>> response = gson.fromJson(json, type);

				Set<String> skipMappings = response.keySet().stream()
						.filter(mapping -> springBootFrameworkMappings.stream().anyMatch(mapping::contains))
						.collect(Collectors.toSet());
				response.keySet().removeAll(skipMappings);

				mappings = response.keySet().stream()
						.map(mapping -> mapping.split(MAPPING_VALUE_SEPARATOR))
						.filter(analyze -> analyze.length >= 2)
						.map(analyze -> CaseMapping.builder()
								.endpoint(bracketsUtils.between(analyze[ENDPOINT_POSITION]))
								.httpType(bracketsUtils.between(analyze[HTTP_TYPE_POSITION]))
								.build()
						)
						.collect(Collectors.toList());
			} else {
				ObjectMapper objectMapper = new ObjectMapper();
				Example example = objectMapper.readValue(json, Example.class);
				List<DispatcherServlet> dispatcherServlet = example.getContexts().getApplication().getMappings().getDispatcherServlets().getDispatcherServlet();
				mappings = dispatcherServlet.stream()
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
			}

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
