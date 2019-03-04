package com.forcelate.acceptance.service;

import com.forcelate.acceptance.domain.processing.FrameworkType;
import com.forcelate.acceptance.domain.reporting.MappingsPair;

@FunctionalInterface
public interface MappingsService {
    MappingsPair retrieve(FrameworkType frameworkType);
}
