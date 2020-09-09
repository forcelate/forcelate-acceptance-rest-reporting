package io.tech1.acceptance.service;

import io.tech1.acceptance.domain.processing.FrameworkType;
import io.tech1.acceptance.domain.reporting.MappingsPair;

@FunctionalInterface
public interface MappingsService {
    MappingsPair retrieve(FrameworkType frameworkType);
}
