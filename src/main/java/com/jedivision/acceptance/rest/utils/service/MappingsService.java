package com.jedivision.acceptance.rest.utils.service;

import com.jedivision.acceptance.rest.utils.domain.reporting.MappingsPair;

@FunctionalInterface
public interface MappingsService {
    MappingsPair retrieve();
}
