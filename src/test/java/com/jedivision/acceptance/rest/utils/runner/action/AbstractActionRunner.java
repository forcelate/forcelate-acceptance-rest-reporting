package com.jedivision.acceptance.rest.utils.runner.action;

import com.jedivision.acceptance.rest.utils.holder.ExecutionsHolder;
import org.junit.After;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import static org.mockito.Mockito.mock;

@SuppressWarnings("ThrowableResultOfMethodCallIgnored")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader=AnnotationConfigContextLoader.class)
public abstract class AbstractActionRunner {

    @Configuration
    static class ContextConfiguration {

        @Bean
        EmptyAction emptyAction() {
            return new EmptyAction();
        }

        @Bean
        FailureAction failureAction() {
            return new FailureAction();
        }

        @Bean
        ServerDownAction serverDownAction() {
            return new ServerDownAction();
        }

        @Bean
        UnexpectedAction unexpectedAction() {
            return new UnexpectedAction();
        }

        @Bean
        Throwable e() {return mock(Throwable.class);}

    }

    @After
    public void after() {
        ExecutionsHolder.getExecuted().clear();
    }

}
