package com.forcelate.acceptance.configuration;

import com.jayway.restassured.RestAssured;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class ApplicationRestAssured {

    private final ApplicationProperties applicationProperties;

    @Autowired
    public ApplicationRestAssured(ApplicationProperties applicationProperties) {
        this.applicationProperties = applicationProperties;
    }

    @PostConstruct
    public void init() {
        RestAssured.useRelaxedHTTPSValidation();
        RestAssured.baseURI = applicationProperties.restAssureBaseURI();
        RestAssured.port = applicationProperties.restAssurePort();
        RestAssured.basePath = applicationProperties.restAssureBasePath();
    }
}
