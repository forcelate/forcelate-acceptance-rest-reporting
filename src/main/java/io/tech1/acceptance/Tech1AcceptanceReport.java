package io.tech1.acceptance;

import io.tech1.acceptance.domain.processing.FrameworkType;
import io.tech1.acceptance.exception.AcceptanceException;
import io.tech1.acceptance.helpers.FreemarkerUtils;
import io.tech1.acceptance.report.ReportService;
import io.tech1.acceptance.configuration.ApplicationBeans;
import io.tech1.acceptance.domain.reporting.Report;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Tech1AcceptanceReport {

    public static void execute(FrameworkType frameworkType) throws AcceptanceException {
        ApplicationContext context = new AnnotationConfigApplicationContext(ApplicationBeans.class);
        ReportService reportService = context.getBean(ReportService.class);
        FreemarkerUtils freemarkerUtils = context.getBean(FreemarkerUtils.class);
        Report report = reportService.formatReport(frameworkType);
        freemarkerUtils.generateReport(report);
    }
}
