package com.forcelate.acceptance.service.impl;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.forcelate.acceptance.domain.processing.CaseMapping;
import com.forcelate.acceptance.domain.processing.FrameworkType;
import com.forcelate.acceptance.domain.reporting.MappingsPair;
import com.forcelate.acceptance.dto.details.DetailsItems;
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

import static com.forcelate.acceptance.configuration.ApplicationConstants.SPRING_BOOT_MAPPING_URL_V1;
import static com.forcelate.acceptance.configuration.ApplicationConstants.SPRING_BOOT_MAPPING_URL_V2;

@Slf4j
@Service
public class SpringBootMappingsService implements MappingsService {
	private static final String MAPPING_VALUE_SEPARATOR = ",";
	private static final int ENDPOINT_POSITION = 0;
	private static final int HTTP_TYPE_POSITION = 1;

	private static Set<String> springBootFrameworkMappingsSpringBootV1 =
			new HashSet<>(Arrays.asList("/**", "/webjars/**", "/error", "/logfile",
					"/metrics", "/configprops", "/mappings", "/trace", "/autoconfig", "/env",
					"/health", "/beans", "/dump", "/info", "/api/api-docs", "/auditevents",
					"/flyway", "/heapdump", "/loggers", "/swagger-resources/configuration/security"));

	private static Set<String> springBootFrameworkMappingsSpringBootV2 =
			new HashSet<>(Arrays.asList("/**", "/webjars/**", "/error", "/logfile",
					"/metrics", "/configprops", "/mappings", "/trace", "/autoconfig", "/env",
					"/health", "/beans", "/dump", "/info", "/api/api-docs", "/auditevents",
					"/flyway", "/heapdump", "/loggers", "/swagger-resources/configuration/security",
					"/actuator","/actuator/mappings","/actuator/info","/actuator/health",
					"/actuator/health/{component}", "/actuator/health/{component}/{instance}"));

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
			ObjectMapper objectMapper = new ObjectMapper();
			List<CaseMapping> mappings;

			if (FrameworkType.SPRING_BOOT_V1.equals(frameworkType)) {
				JavaType stringType = objectMapper.getTypeFactory().constructType(String.class);
				JavaType innerMapType = objectMapper.getTypeFactory().constructMapType(Map.class, stringType, stringType);
				JavaType outerMapType = objectMapper.getTypeFactory().constructMapType(Map.class, stringType, innerMapType);

				Map<String, Map<String, String>> response = objectMapper.readValue(json, outerMapType);

				Set<String> skipMappings = response.keySet().stream()
						.filter(mapping -> springBootFrameworkMappingsSpringBootV1.stream().anyMatch(mapping::contains))
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
				Example example = objectMapper.readValue(json, Example.class);
				List<DispatcherServlet> dispatcherServlet = example.getContexts().getApplication().getMappings().getDispatcherServlets().getDispatcherServlet();
				mappings = dispatcherServlet.stream()
					.map(DispatcherServlet::getDetails)
					.filter(Objects::nonNull)
					.map(DetailsItems::getRequestMappingConditions)
					.filter(Objects::nonNull)
					.filter(r -> Objects.nonNull(r.getMethods()) && Objects.nonNull(r.getPatterns()) && !r.getPatterns().isEmpty() && !r.getMethods().isEmpty())
					.filter(mapping -> springBootFrameworkMappingsSpringBootV2.stream()
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
		} catch (NullPointerException e) {
			log.error("Spring version is null: {}", e);
			return MappingsPair.builder()
					.available(false)
					.mappings(Collections.emptyList())
					.build();
		}
	}
}
