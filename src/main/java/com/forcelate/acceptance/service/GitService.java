package com.forcelate.acceptance.service;

import com.forcelate.acceptance.domain.processing.FrameworkType;
import com.forcelate.acceptance.domain.reporting.Git;

@FunctionalInterface
public interface GitService {
    Git retrieve(FrameworkType frameworkType);
}
