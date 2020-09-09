package io.tech1.acceptance.helpers;

import io.tech1.acceptance.domain.reporting.Report;
import io.tech1.acceptance.exception.AcceptanceException;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import io.tech1.acceptance.configuration.ApplicationConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class FreemarkerUtils {
    private final Configuration configuration;

    @Autowired
    public FreemarkerUtils(Configuration configuration) {
        this.configuration = configuration;
    }

    public void generateReport(Report report) throws AcceptanceException {
        try {
            Template template = configuration.getTemplate(ApplicationConstants.REPORT_TEMPLATE);
            Map<String, Object> input = new HashMap<>();
            input.put("report", report);

            try (Writer writer = new FileWriter(new File(ApplicationConstants.REPORT_HTML))) {
                template.process(input, writer);
            }
        } catch (IOException e) {
            log.error("Problem with getting freemarker template {}", e);
            throw new AcceptanceException(e.getMessage());
        } catch (TemplateException e) {
            log.error("Problem with processing freemarker template {}", e);
            throw new AcceptanceException(e.getMessage());
        }
    }
}
