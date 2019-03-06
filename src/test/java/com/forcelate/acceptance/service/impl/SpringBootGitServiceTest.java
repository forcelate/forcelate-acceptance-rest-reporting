package com.forcelate.acceptance.service.impl;

import com.forcelate.acceptance.domain.processing.FrameworkType;
import com.forcelate.acceptance.domain.reporting.Git;
import com.forcelate.acceptance.service.GitService;
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

import static com.forcelate.acceptance.configuration.ApplicationConstants.*;
import static com.forcelate.acceptance.test.RandomUtils.randomString;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader=AnnotationConfigContextLoader.class)
public class SpringBootGitServiceTest {

    @Configuration
    static class ContextConfiguration {

        @Bean
        RestAssureSupport restAssureSupport() {
            return mock(RestAssureSupport.class);
        }

        @Bean
        GitService gitService() {
            return new SpringBootGitService(restAssureSupport());
        }

    }

    @Autowired private RestAssureSupport restAssureSupport;

    @Autowired private GitService serviceUnderTest;

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
        ConnectException exception = new ConnectException(randomString());
        when(restAssureSupport.getResponseJSON(SPRING_BOOT_INFO_URL_V1)).thenThrow(exception);

        // Act
        Git git = serviceUnderTest.retrieve(FrameworkType.SPRING_BOOT_V1);

        // Assert
        assertEquals(UNCERTAIN, git.getBranch());
        assertEquals(UNCERTAIN, git.getCommitId());
        assertEquals(UNCERTAIN, git.getCommitTime());
        verify(restAssureSupport).getResponseJSON(eq(SPRING_BOOT_INFO_URL_V1));
    }

    @Test
    public void retrieveThrowConnectionExceptionSpringBootV2() throws ConnectException {
        // Arrange
        ConnectException exception = new ConnectException(randomString());
        when(restAssureSupport.getResponseJSON(SPRING_BOOT_INFO_URL_V2)).thenThrow(exception);

        // Act
        Git git = serviceUnderTest.retrieve(FrameworkType.SPRING_BOOT_V2);

        // Assert
        assertEquals(UNCERTAIN, git.getBranch());
        assertEquals(UNCERTAIN, git.getCommitId());
        assertEquals(UNCERTAIN, git.getCommitTime());
        verify(restAssureSupport).getResponseJSON(eq(SPRING_BOOT_INFO_URL_V2));
    }

    @Test
    public void retrieveSpringBootV1() throws IOException {
        // Arrange
        try (InputStream is = SpringBootGitServiceTest.class.getResourceAsStream("/git.json")) {
            String json = IOUtils.toString(is);
            when(restAssureSupport.getResponseJSON(SPRING_BOOT_INFO_URL_V1)).thenReturn(json);
        }

        // Act
        Git git = serviceUnderTest.retrieve(FrameworkType.SPRING_BOOT_V1);

        // Assert
        assertEquals("dev", git.getBranch());
        assertEquals("7cb511a", git.getCommitId());
        assertEquals("2018-12-13T17:19:31Z", git.getCommitTime());
        verify(restAssureSupport).getResponseJSON(eq(SPRING_BOOT_INFO_URL_V1));
    }

    @Test
    public void retrieveSpringBootV2() throws IOException {
        // Arrange
        try (InputStream is = SpringBootGitServiceTest.class.getResourceAsStream("/git.json")) {
            String json = IOUtils.toString(is);
            when(restAssureSupport.getResponseJSON(SPRING_BOOT_INFO_URL_V2)).thenReturn(json);
        }

        // Act
        Git git = serviceUnderTest.retrieve(FrameworkType.SPRING_BOOT_V2);

        // Assert
        assertEquals("dev", git.getBranch());
        assertEquals("7cb511a", git.getCommitId());
        assertEquals("2018-12-13T17:19:31Z", git.getCommitTime());
        verify(restAssureSupport).getResponseJSON(eq(SPRING_BOOT_INFO_URL_V2));
    }
}
