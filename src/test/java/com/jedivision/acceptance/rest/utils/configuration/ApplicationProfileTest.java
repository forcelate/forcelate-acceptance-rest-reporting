package com.jedivision.acceptance.rest.utils.configuration;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import static com.jedivision.acceptance.rest.utils.configuration.ApplicationConstants.PROFILE_DEFAULT_VALUE;
import static com.jedivision.acceptance.rest.utils.configuration.ApplicationConstants.PROFILE_KEY;
import static com.jedivision.acceptance.rest.utils.test.JediRandomUtils.randomString;
import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader=AnnotationConfigContextLoader.class)
public class ApplicationProfileTest {

    @Configuration
    static class ContextConfiguration {

        @Bean
        ApplicationProfile applicationProfile() {
            return new ApplicationProfile();
        }
    }

    @Autowired private ApplicationProfile componentUnderTest;

    @Test
    public void getDefault() {
        // Act
        String result = componentUnderTest.get();

        // Assert
        assertEquals(PROFILE_DEFAULT_VALUE, result);
    }

    @Test
    public void get() {
        // Arrange
        String profile = randomString();
        System.setProperty(PROFILE_KEY, profile);
        componentUnderTest.init();

        // Act
        String result = componentUnderTest.get();

        // Assert
        assertEquals(profile, result);
    }
}
