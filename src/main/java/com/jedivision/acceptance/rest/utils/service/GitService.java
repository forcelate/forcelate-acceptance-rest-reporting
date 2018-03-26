package com.jedivision.acceptance.rest.utils.service;

import com.jedivision.acceptance.rest.utils.domain.reporting.Git;

@FunctionalInterface
public interface GitService {
    Git retrieve();
}
