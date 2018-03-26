package com.forcelate.acceptance.runner;

import com.forcelate.acceptance.domain.processing.CaseAnnotation;
import com.forcelate.acceptance.test.JediCoberturaUtils;
import com.forcelate.acceptance.test.JediRandomUtils;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.Description;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader=AnnotationConfigContextLoader.class)
public class JunitDescriptionUtilsTest {

    @Configuration
    static class ContextConfiguration {

        @Bean Description description() {return mock(Description.class);}

        @Bean
        CaseAnnotation annotation() {return mock(CaseAnnotation.class);}

    }

    private static final List<String> ATTRIBUTES = asList("endpoint", "httpType", "description");

    @Autowired private Description description;
    @Autowired private CaseAnnotation annotation;

    @BeforeClass
    public static void beforeClass() {
        JediCoberturaUtils.classCoverageHook(JunitDescriptionUtils.class);
    }

    @Test
    public void getEndpoint() throws Exception {
        // Arrange
        prepare(annotation, ATTRIBUTES.get(0));

        // Act
        String result = JunitDescriptionUtils.getEndpoint(description);

        // Assert
        assertEquals(ATTRIBUTES.get(0), result);
    }

    @Test
    public void getHttpType() throws Exception {
        // Arrange
        prepare(annotation, ATTRIBUTES.get(1));

        // Act
        String result = JunitDescriptionUtils.getHttpType(description);

        // Assert
        assertEquals(ATTRIBUTES.get(1), result);
    }

    @Test
    public void getDescription() throws Exception {
        // Arrange
        prepare(annotation, ATTRIBUTES.get(2));

        // Act
        String result = JunitDescriptionUtils.getDescription(description);

        // Assert
        assertEquals(ATTRIBUTES.get(2), result);
    }

    @Test
    public void accCaseAnnotation() {
        // Arrange
        when(description.getAnnotation(CaseAnnotation.class)).thenReturn(annotation);

        // Act
        Optional<CaseAnnotation> accCaseAnnotation = JunitDescriptionUtils.accCaseAnnotation(description);

        // Assert
        assertTrue(accCaseAnnotation.isPresent());
    }

    @Test
    public void byFuncNotPresent() {
        // Arrange
        String random = JediRandomUtils.randomString();
        when(description.getAnnotation(CaseAnnotation.class)).thenReturn(null);

        // Act
        String result = JunitDescriptionUtils.byFunc(description, function -> random);

        // Assert
        assertEquals(JunitDescriptionUtils.UNEXPECTED, result);
    }

    @Test
    public void byFunc() {
        // Arrange
        String random = JediRandomUtils.randomString();
        when(description.getAnnotation(CaseAnnotation.class)).thenReturn(annotation);

        // Act
        String result = JunitDescriptionUtils.byFunc(description, function -> random);

        // Assert
        assertEquals(random, result);
    }

    private void prepare(Object obj, String methodName) throws Exception {
        Method method = obj.getClass().getMethod(methodName);
        when(method.invoke(obj)).thenReturn(methodName);
        when(description.getAnnotation(CaseAnnotation.class)).thenReturn(annotation);
    }

}
