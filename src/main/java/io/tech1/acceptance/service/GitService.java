package io.tech1.acceptance.service;

import io.tech1.acceptance.domain.processing.FrameworkType;
import io.tech1.acceptance.domain.reporting.Git;

@FunctionalInterface
public interface GitService {
    Git retrieve(FrameworkType frameworkType);
}
