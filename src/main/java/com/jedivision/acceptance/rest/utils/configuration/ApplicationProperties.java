package com.jedivision.acceptance.rest.utils.configuration;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class ApplicationProperties {
    private static final String APPLICATION_PROPERTIES = "application-%s.properties";

    private Config config;

    private final ApplicationProfile applicationProfile;

    @Autowired
    public ApplicationProperties(ApplicationProfile applicationProfile) {
        this.applicationProfile = applicationProfile;
    }

    @PostConstruct
    public void init() {
        config = ConfigFactory.load(String.format(APPLICATION_PROPERTIES, applicationProfile.get()));
    }

    // Rest Assure
    public String restAssureBaseURI() {
        return config.getString("app.restassure.baseURI");
    }

    public int restAssurePort() {
        return config.getInt("app.restassure.port");
    }

    public String restAssureBasePath() {
        return config.getString("app.restassure.basePath");
    }

    public String oauthGrantType() {
        return config.getString("app.spring.oauth.grant_type");
    }

    public String oauthScope() {
        return config.getString("app.spring.oauth.scope");
    }

    public String oauthClientId() {
        return config.getString("app.spring.oauth.client_id");
    }

    public String oauthClientSecret() {
        return config.getString("app.spring.oauth.client_secret");
    }
}
