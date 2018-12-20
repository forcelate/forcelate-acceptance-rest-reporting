package com.forcelate.acceptance.service.impl;

import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.PathNotFoundException;
import com.forcelate.acceptance.domain.reporting.Git;
import com.forcelate.acceptance.service.GitService;
import com.forcelate.acceptance.support.RestAssureSupport;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.ConnectException;
import java.sql.Timestamp;

import static com.forcelate.acceptance.configuration.ApplicationConstants.SPRING_BOOT_INFO_URL;
import static com.forcelate.acceptance.configuration.ApplicationConstants.UNCERTAIN;

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
