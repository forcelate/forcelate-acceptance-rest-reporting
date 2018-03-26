package com.forcelate.acceptance.service;

import com.forcelate.acceptance.domain.reporting.MappingsPair;

@FunctionalInterface
public interface MappingsService {
    MappingsPair retrieve();
}
