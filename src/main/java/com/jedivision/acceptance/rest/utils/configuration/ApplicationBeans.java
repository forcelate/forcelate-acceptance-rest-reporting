package com.jedivision.acceptance.rest.utils.configuration;

import freemarker.template.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import static freemarker.template.Configuration.VERSION_2_3_23;

@org.springframework.context.annotation.Configuration
@ComponentScan("com.jedivision.acceptance.rest.utils")
public class ApplicationBeans {
    private static final String ENCODING = "UTF-8";
    private static final String REPORT_PATH = "/templates";

    @Bean
    public Configuration configuration() {
        Configuration configuration = new Configuration(VERSION_2_3_23);
        configuration.setDefaultEncoding(ENCODING);
        configuration.setClassForTemplateLoading(ApplicationBeans.class, REPORT_PATH);
        return configuration;
    }
}
