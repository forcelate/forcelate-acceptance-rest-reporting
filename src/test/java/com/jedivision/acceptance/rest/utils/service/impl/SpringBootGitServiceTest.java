package com.jedivision.acceptance.rest.utils.service.impl;

import com.jedivision.acceptance.rest.utils.domain.reporting.Git;
import com.jedivision.acceptance.rest.utils.service.GitService;
import com.jedivision.acceptance.rest.utils.support.RestAssureSupport;
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

import static com.jedivision.acceptance.rest.utils.configuration.ApplicationConstants.SPRING_BOOT_INFO_URL;
import static com.jedivision.acceptance.rest.utils.configuration.ApplicationConstants.UNCERTAIN;
import static com.jedivision.acceptance.rest.utils.test.JediRandomUtils.randomString;
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
    public void retrieveThrowConnectionException() throws ConnectException {
        // Arrange
        ConnectException exception = new ConnectException(randomString());
        when(restAssureSupport.getResponseJSON(SPRING_BOOT_INFO_URL)).thenThrow(exception);

        // Act
        Git git = serviceUnderTest.retrieve();

        // Assert
        assertEquals(UNCERTAIN, git.getBranch());
        assertEquals(UNCERTAIN, git.getCommitId());
        assertEquals(UNCERTAIN, git.getCommitTime());
        verify(restAssureSupport).getResponseJSON(eq(SPRING_BOOT_INFO_URL));
    }

    @Test
    public void retrieve() throws IOException {
        // Arrange
        try (InputStream is = SpringBootGitServiceTest.class.getResourceAsStream("/git.json")) {
            String json = IOUtils.toString(is);
            when(restAssureSupport.getResponseJSON(SPRING_BOOT_INFO_URL)).thenReturn(json);
        }

        // Act
        Git git = serviceUnderTest.retrieve();

        // Assert
        assertEquals("dev", git.getBranch());
        assertEquals("7cb511a", git.getCommitId());
        assertEquals("2016-07-30T10:42:26+0300", git.getCommitTime());
        verify(restAssureSupport).getResponseJSON(eq(SPRING_BOOT_INFO_URL));
    }
}
