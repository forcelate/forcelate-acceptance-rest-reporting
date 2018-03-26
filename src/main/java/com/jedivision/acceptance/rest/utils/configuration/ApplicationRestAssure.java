package com.jedivision.acceptance.rest.utils.configuration;

import com.jayway.restassured.RestAssured;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class ApplicationRestAssure {

    private final ApplicationProperties applicationProperties;

    @Autowired
    public ApplicationRestAssure(ApplicationProperties applicationProperties) {
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
