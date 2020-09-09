package io.tech1.acceptance.configuration;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import static io.tech1.acceptance.configuration.ApplicationConstants.PROFILE_DEFAULT_VALUE;
import static io.tech1.acceptance.configuration.ApplicationConstants.PROFILE_KEY;

@Component
public class ApplicationProfile {
    private String currentProfile;

    @PostConstruct
    public void init() {
        currentProfile = System.getProperty(PROFILE_KEY, PROFILE_DEFAULT_VALUE);
    }

    public String get() {
        return currentProfile;
    }
}
