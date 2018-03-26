package com.jedivision.acceptance.rest.utils.service.impl;

import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.PathNotFoundException;
import com.jedivision.acceptance.rest.utils.domain.reporting.Git;
import com.jedivision.acceptance.rest.utils.service.GitService;
import com.jedivision.acceptance.rest.utils.support.RestAssureSupport;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.ConnectException;

import static com.jedivision.acceptance.rest.utils.configuration.ApplicationConstants.SPRING_BOOT_INFO_URL;
import static com.jedivision.acceptance.rest.utils.configuration.ApplicationConstants.UNCERTAIN;

@Slf4j
@Service
public class SpringBootGitService implements GitService {
    private final RestAssureSupport restAssureSupport;

    @Autowired
    public SpringBootGitService(RestAssureSupport restAssureSupport) {
        this.restAssureSupport = restAssureSupport;
    }

    public Git retrieve() {
        try {
            String json = restAssureSupport.getResponseJSON(SPRING_BOOT_INFO_URL);
            return Git.builder()
                    .branch(JsonPath.read(json, "$.git.branch"))
                    .commitId(JsonPath.read(json, "$.git.commit.id"))
                    .commitTime(JsonPath.read(json, "$.git.commit.time"))
                    .build();
        } catch (ConnectException | PathNotFoundException e) {
            log.error("Spring git endpoint is unavailable {}", e);
            return Git.builder()
                    .branch(UNCERTAIN)
                    .commitId(UNCERTAIN)
                    .commitTime(UNCERTAIN)
                    .build();
        }
    }
}
