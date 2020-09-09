package io.tech1.acceptance.service.impl;

import io.tech1.acceptance.domain.processing.CaseMapping;
import io.tech1.acceptance.domain.processing.FrameworkType;
import io.tech1.acceptance.domain.reporting.MappingsPair;
import io.tech1.acceptance.helpers.BracketsUtils;
import io.tech1.acceptance.service.MappingsService;
import io.tech1.acceptance.support.RestAssureSupport;
import io.tech1.acceptance.test.RandomUtils;
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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader=AnnotationConfigContextLoader.class)
public class SpringBootMappingsServiceTest {
    private static final String SPRING_BOOT_MAPPING_URL_V1 = "/mappings";
    private static final String SPRING_BOOT_MAPPING_URL_V2 = "/actuator/mappings";

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
    public void retrieveThrowConnectionExceptionSpringBootV1() throws ConnectException {
        // Arrange
        ConnectException exception = new ConnectException(RandomUtils.randomString());
        when(restAssureSupport.getResponseJSON(SPRING_BOOT_MAPPING_URL_V1)).thenThrow(exception);

        // Act
        MappingsPair pair = serviceUnderTest.retrieve(FrameworkType.SPRING_BOOT_V1);

        // Assert
        assertFalse(pair.isAvailable());
        assertTrue(pair.getMappings().size() == 0);
        verify(restAssureSupport).getResponseJSON(eq(SPRING_BOOT_MAPPING_URL_V1));
    }

    @Test
    public void retrieveThrowConnectionExceptionSpringBootV2() throws ConnectException {
        // Arrange
        ConnectException exception = new ConnectException(RandomUtils.randomString());
        when(restAssureSupport.getResponseJSON(SPRING_BOOT_MAPPING_URL_V2)).thenThrow(exception);

        // Act
        MappingsPair pair = serviceUnderTest.retrieve(FrameworkType.SPRING_BOOT_V2);

        // Assert
        assertFalse(pair.isAvailable());
        assertTrue(pair.getMappings().size() == 0);
        verify(restAssureSupport).getResponseJSON(eq(SPRING_BOOT_MAPPING_URL_V2));
    }

    @Test
    public void retrieveSpringBootV1() throws IOException {
        // Arrange
        try (InputStream is = SpringBootGitServiceTest.class.getResourceAsStream("/mappingsSpringBootV1.json")) {
            String json = IOUtils.toString(is);
            when(restAssureSupport.getResponseJSON(SPRING_BOOT_MAPPING_URL_V1)).thenReturn(json);
        }

        // Act
        MappingsPair pair = serviceUnderTest.retrieve(FrameworkType.SPRING_BOOT_V1);

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
        verify(restAssureSupport).getResponseJSON(eq(SPRING_BOOT_MAPPING_URL_V1));
    }

    @Test
    public void retrieveSpringBootV2() throws IOException {
        // Arrange
        try (InputStream is = SpringBootGitServiceTest.class.getResourceAsStream("/mappingsSpringBootV2.json")) {
            String json = IOUtils.toString(is);
            when(restAssureSupport.getResponseJSON(SPRING_BOOT_MAPPING_URL_V2)).thenReturn(json);
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
        verify(restAssureSupport).getResponseJSON(eq(SPRING_BOOT_MAPPING_URL_V2));
    }
}
