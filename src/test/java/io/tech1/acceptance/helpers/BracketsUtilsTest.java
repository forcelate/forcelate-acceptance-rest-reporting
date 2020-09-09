package io.tech1.acceptance.helpers;

import io.tech1.acceptance.test.RandomUtils;
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
public class BracketsUtilsTest {

    @Configuration
    static class ContextConfiguration {

        @Bean
        BracketsUtils bracketsUtils() {
            return new BracketsUtils();
        }

    }

    @Autowired
    private BracketsUtils utilsUnderTest;

    @Test
    public void between() {
        // Arrange
        String random = RandomUtils.randomString();
        String str = "[" + random + "]";

        // Act
        String between = utilsUnderTest.between(str);

        // Assert
        assertEquals(random, between);
    }
}
