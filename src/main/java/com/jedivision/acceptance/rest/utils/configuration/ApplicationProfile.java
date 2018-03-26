package com.jedivision.acceptance.rest.utils.configuration;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import static com.jedivision.acceptance.rest.utils.configuration.ApplicationConstants.PROFILE_DEFAULT_VALUE;
import static com.jedivision.acceptance.rest.utils.configuration.ApplicationConstants.PROFILE_KEY;

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
