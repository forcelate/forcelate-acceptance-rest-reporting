package com.forcelate.acceptance;

import com.forcelate.acceptance.domain.processing.FrameworkType;
import com.forcelate.acceptance.exception.AcceptanceException;
import com.forcelate.acceptance.helpers.FreemarkerUtils;
import com.forcelate.acceptance.report.ReportService;
import com.forcelate.acceptance.configuration.ApplicationBeans;
import com.forcelate.acceptance.domain.reporting.Report;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ForcelateAcceptanceReport {

    public static void execute(FrameworkType frameworkType) throws AcceptanceException {
        ApplicationContext context = new AnnotationConfigApplicationContext(ApplicationBeans.class);
        ReportService reportService = context.getBean(ReportService.class);
        FreemarkerUtils freemarkerUtils = context.getBean(FreemarkerUtils.class);
        Report report = reportService.formatReport(frameworkType);
        freemarkerUtils.generateReport(report);
    }
}
