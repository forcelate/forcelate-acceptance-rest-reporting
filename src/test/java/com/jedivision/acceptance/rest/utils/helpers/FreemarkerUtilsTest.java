package com.jedivision.acceptance.rest.utils.helpers;

import com.jedivision.acceptance.rest.utils.domain.reporting.Report;
import com.jedivision.acceptance.rest.utils.exception.AcceptanceException;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

import static com.jedivision.acceptance.rest.utils.configuration.ApplicationConstants.REPORT_TEMPLATE;
import static com.jedivision.acceptance.rest.utils.test.JediRandomUtils.randomString;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader=AnnotationConfigContextLoader.class)
public class FreemarkerUtilsTest {

    @Configuration
    static class ContextConfiguration {

        @Bean
        public freemarker.template.Configuration configuration() {
            return mock(freemarker.template.Configuration.class);
        }

        @Bean
        FreemarkerUtils freemarkerUtils() {
            return new FreemarkerUtils(configuration());
        }
    }

    @Autowired private freemarker.template.Configuration configuration;

    @Autowired private FreemarkerUtils utilsUnderTest;

    @Before
    public void before() {
        reset(configuration);
    }

    @After
    public void after() {
        verifyNoMoreInteractions(configuration);
    }

    @Test
    public void generateThrowIOException() throws IOException, AcceptanceException {
        // Arrange
        Report report = mock(Report.class);
        IOException exception = new IOException(randomString());
        when(configuration.getTemplate(REPORT_TEMPLATE)).thenThrow(exception);

        // Act
        assertThatThrownBy(() ->  utilsUnderTest.generateReport(report))
                .isInstanceOf(AcceptanceException.class);

        // Assert
        verify(configuration).getTemplate(eq(REPORT_TEMPLATE));
    }

    @Test
    public void generateThrowTemplateException() throws IOException, AcceptanceException, TemplateException {
        // Arrange
        Report report = mock(Report.class);
        Template template = mock(Template.class);
        TemplateException templateException = new TemplateException(randomString(), null);
        doThrow(templateException).when(template).process(any(Object.class), any(Writer.class));
        when(configuration.getTemplate(REPORT_TEMPLATE)).thenReturn(template);

        // Act
        assertThatThrownBy(() ->  utilsUnderTest.generateReport(report))
                .isInstanceOf(AcceptanceException.class);

        // Assert
        ArgumentCaptor<Map> input = ArgumentCaptor.forClass(Map.class);
        verify(template).process(input.capture(), any(Writer.class));
        assertTrue(input.getValue().containsKey("report"));
        assertEquals(report, input.getValue().get("report"));
        verify(configuration).getTemplate(eq(REPORT_TEMPLATE));
    }

    @Test
    public void generate() throws IOException, AcceptanceException, TemplateException {
        // Arrange
        Report report = mock(Report.class);
        Template template = mock(Template.class);
        when(configuration.getTemplate(REPORT_TEMPLATE)).thenReturn(template);

        // Act
        utilsUnderTest.generateReport(report);

        // Assert
        ArgumentCaptor<Map> input = ArgumentCaptor.forClass(Map.class);
        verify(template).process(input.capture(), any(Writer.class));
        assertTrue(input.getValue().containsKey("report"));
        assertEquals(report, input.getValue().get("report"));
        verify(configuration).getTemplate(eq(REPORT_TEMPLATE));
    }
}
