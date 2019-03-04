package com.forcelate.acceptance.service.impl;

import com.forcelate.acceptance.domain.processing.CaseMapping;
import com.forcelate.acceptance.domain.processing.FrameworkType;
import com.forcelate.acceptance.domain.reporting.MappingsPair;
import com.forcelate.acceptance.helpers.BracketsUtils;
import com.forcelate.acceptance.service.MappingsService;
import com.forcelate.acceptance.support.RestAssureSupport;
import org.apache.commons.io.IOUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.io.IOException;
import java.io.InputStream;
import java.net.ConnectException;
import java.util.List;

import static com.forcelate.acceptance.test.RandomUtils.randomString;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader=AnnotationConfigContextLoader.class)
public class SpringBootMappingsServiceTest {
    private static final String SPRING_BOOT_MAPPING_URL = "/actuator/mappings";

    @Configuration
    static class ContextConfiguration {

        @Bean
        RestAssureSupport restAssureSupport() {
            return mock(RestAssureSupport.class);
        }

        @Bean
        BracketsUtils bracketsUtils() {
            return new BracketsUtils();
        }

        @Bean
        MappingsService mappingsService() {
            return new SpringBootMappingsService(bracketsUtils(), restAssureSupport());
        }

    }

    @Autowired private RestAssureSupport restAssureSupport;
    @Autowired private BracketsUtils bracketsUtils;

    @Autowired private MappingsService serviceUnderTest;

    @Before
    public void before() {
        reset(restAssureSupport);
    }

    @After
    public void after() {
        verifyNoMoreInteractions(restAssureSupport);
    }

    @Test
    public void retrieveThrowConnectionException() throws ConnectException {
        // Arrange
        ConnectException exception = new ConnectException(randomString());
        when(restAssureSupport.getResponseJSON(SPRING_BOOT_MAPPING_URL)).thenThrow(exception);

        // Act
        MappingsPair pair = serviceUnderTest.retrieve(FrameworkType.SPRING_BOOT_V2);

        // Assert
        assertFalse(pair.isAvailable());
        assertTrue(pair.getMappings().size() == 0);
        verify(restAssureSupport).getResponseJSON(eq(SPRING_BOOT_MAPPING_URL));
    }

    @Test
    public void retrieve() throws IOException {
        // Arrange
        try (InputStream is = SpringBootGitServiceTest.class.getResourceAsStream("/mappings.json")) {
            String json = IOUtils.toString(is);
            when(restAssureSupport.getResponseJSON(SPRING_BOOT_MAPPING_URL)).thenReturn(json);
        }

        // Act
        MappingsPair pair = serviceUnderTest.retrieve(FrameworkType.SPRING_BOOT_V2);

        // Assert
        assertTrue(pair.isAvailable());
        List<CaseMapping> mappings = pair.getMappings();
        assertTrue(mappings.size() == 4);
        assertTrue(mappings.contains(
                CaseMapping.builder()
                        .endpoint("/constants/languages")
                        .httpType("GET")
                        .build()));
        assertTrue(mappings.contains(
                CaseMapping.builder()
                        .endpoint("/constants/author/types")
                        .httpType("GET")
                        .build()));
        assertTrue(mappings.contains(
                CaseMapping.builder()
                        .endpoint("/constants/quote/types")
                        .httpType("GET")
                        .build()));
        assertTrue(mappings.contains(
                CaseMapping.builder()
                        .endpoint("/constants/quote/templates")
                        .httpType("GET")
                        .build()));
        verify(restAssureSupport).getResponseJSON(eq(SPRING_BOOT_MAPPING_URL));
    }
}
