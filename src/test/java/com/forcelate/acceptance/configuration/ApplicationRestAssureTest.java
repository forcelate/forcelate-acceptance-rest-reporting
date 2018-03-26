package com.forcelate.acceptance.configuration;

import com.jayway.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import static com.forcelate.acceptance.test.JediRandomUtils.randomInteger;
import static com.forcelate.acceptance.test.JediRandomUtils.randomString;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader=AnnotationConfigContextLoader.class)
public class ApplicationRestAssureTest {

    @Configuration
    static class ContextConfiguration {

        @Bean
        ApplicationProperties applicationProperties() { return mock(ApplicationProperties.class); }

        @Bean
        ApplicationRestAssure applicationRestAssure() {
            return new ApplicationRestAssure(applicationProperties());
        }
    }

    @Autowired private ApplicationProperties applicationProperties;

    @Autowired private ApplicationRestAssure componentUnderTest;

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
