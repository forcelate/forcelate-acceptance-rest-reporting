package io.tech1.acceptance.service.impl;

import io.tech1.acceptance.domain.processing.FrameworkType;
import io.tech1.acceptance.domain.reporting.Git;
import io.tech1.acceptance.service.GitService;
import io.tech1.acceptance.support.RestAssureSupport;
import io.tech1.acceptance.configuration.ApplicationConstants;
import io.tech1.acceptance.test.RandomUtils;
import org.apache.commons.io.IOUtils;
import org.junit.After;
import org.junit.Assert;
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
        ConnectException exception = new ConnectException(RandomUtils.randomString());
        when(restAssureSupport.getResponseJSON(ApplicationConstants.SPRING_BOOT_INFO_URL_V1)).thenThrow(exception);

        // Act
        Git git = serviceUnderTest.retrieve(FrameworkType.SPRING_BOOT_V1);

        // Assert
        Assert.assertEquals(ApplicationConstants.UNCERTAIN, git.getBranch());
        Assert.assertEquals(ApplicationConstants.UNCERTAIN, git.getCommitId());
        Assert.assertEquals(ApplicationConstants.UNCERTAIN, git.getCommitTime());
        verify(restAssureSupport).getResponseJSON(eq(ApplicationConstants.SPRING_BOOT_INFO_URL_V1));
    }

    @Test
    public void retrieveThrowConnectionExceptionSpringBootV2() throws ConnectException {
        // Arrange
        ConnectException exception = new ConnectException(RandomUtils.randomString());
        when(restAssureSupport.getResponseJSON(ApplicationConstants.SPRING_BOOT_INFO_URL_V2)).thenThrow(exception);

        // Act
        Git git = serviceUnderTest.retrieve(FrameworkType.SPRING_BOOT_V2);

        // Assert
        Assert.assertEquals(ApplicationConstants.UNCERTAIN, git.getBranch());
        Assert.assertEquals(ApplicationConstants.UNCERTAIN, git.getCommitId());
        Assert.assertEquals(ApplicationConstants.UNCERTAIN, git.getCommitTime());
        verify(restAssureSupport).getResponseJSON(eq(ApplicationConstants.SPRING_BOOT_INFO_URL_V2));
    }

    @Test
    public void retrieveSpringBootV1() throws IOException {
        // Arrange
        try (InputStream is = SpringBootGitServiceTest.class.getResourceAsStream("/git.json")) {
            String json = IOUtils.toString(is);
            when(restAssureSupport.getResponseJSON(ApplicationConstants.SPRING_BOOT_INFO_URL_V1)).thenReturn(json);
        }

        // Act
        Git git = serviceUnderTest.retrieve(FrameworkType.SPRING_BOOT_V1);

        // Assert
        assertEquals("dev", git.getBranch());
        assertEquals("7cb511a", git.getCommitId());
        assertEquals("2018-12-13T17:19:31Z", git.getCommitTime());
        verify(restAssureSupport).getResponseJSON(eq(ApplicationConstants.SPRING_BOOT_INFO_URL_V1));
    }

    @Test
    public void retrieveSpringBootV2() throws IOException {
        // Arrange
        try (InputStream is = SpringBootGitServiceTest.class.getResourceAsStream("/git.json")) {
            String json = IOUtils.toString(is);
            when(restAssureSupport.getResponseJSON(ApplicationConstants.SPRING_BOOT_INFO_URL_V2)).thenReturn(json);
        }

        // Act
        Git git = serviceUnderTest.retrieve(FrameworkType.SPRING_BOOT_V2);

        // Assert
        assertEquals("dev", git.getBranch());
        assertEquals("7cb511a", git.getCommitId());
        assertEquals("2018-12-13T17:19:31Z", git.getCommitTime());
        verify(restAssureSupport).getResponseJSON(eq(ApplicationConstants.SPRING_BOOT_INFO_URL_V2));
    }
}
