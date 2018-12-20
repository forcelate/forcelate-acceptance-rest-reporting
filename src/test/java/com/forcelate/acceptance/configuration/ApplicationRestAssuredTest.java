package com.forcelate.acceptance.configuration;

import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import static com.forcelate.acceptance.test.RandomUtils.randomInteger;
import static com.forcelate.acceptance.test.RandomUtils.randomString;
import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader=AnnotationConfigContextLoader.class)
public class ApplicationRestAssuredTest {

    @Configuration
    static class ContextConfiguration {

        @Bean
        ApplicationProperties applicationProperties() { return mock(ApplicationProperties.class); }

        @Bean
        ApplicationRestAssured applicationRestAssure() {
            return new ApplicationRestAssured(applicationProperties());
        }
    }

    @Autowired private ApplicationProperties applicationProperties;

    @Autowired private ApplicationRestAssured componentUnderTest;

    @Before
    public void before() {
        reset(applicationProperties);
    }

    @Test
    public void init() {
        // Arrange
        String baseURI = randomString();
        int port = randomInteger();
        String basePath = randomString();
        when(applicationProperties.restAssureBaseURI()).thenReturn(baseURI);
        when(applicationProperties.restAssurePort()).thenReturn(port);
        when(applicationProperties.restAssureBasePath()).thenReturn(basePath);

        // Act
        componentUnderTest.init();

        // Assert
        assertEquals(baseURI, RestAssured.baseURI);
        assertEquals(port, RestAssured.port);
        assertEquals(basePath, RestAssured.basePath);
    }
}
