package com.forcelate.acceptance.configuration;

import com.forcelate.acceptance.test.RandomUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

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
        Assert.assertEquals(ApplicationConstants.PROFILE_DEFAULT_VALUE, result);
    }

    @Test
    public void get() {
        // Arrange
        String profile = RandomUtils.randomString();
        System.setProperty(ApplicationConstants.PROFILE_KEY, profile);
        componentUnderTest.init();

        // Act
        String result = componentUnderTest.get();

        // Assert
        assertEquals(profile, result);
    }
}
