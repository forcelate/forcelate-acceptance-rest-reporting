package com.jedivision.acceptance.rest.utils;

import com.jedivision.acceptance.rest.utils.configuration.ApplicationBeans;
import com.jedivision.acceptance.rest.utils.domain.reporting.Report;
import com.jedivision.acceptance.rest.utils.exception.AcceptanceException;
import com.jedivision.acceptance.rest.utils.helpers.FreemarkerUtils;
import com.jedivision.acceptance.rest.utils.report.ReportService;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ForcelateAcceptanceReport {

    public static void execute() throws AcceptanceException {
        ApplicationContext context = new AnnotationConfigApplicationContext(ApplicationBeans.class);
        ReportService reportService = context.getBean(ReportService.class);
        FreemarkerUtils freemarkerUtils = context.getBean(FreemarkerUtils.class);
        Report report = reportService.formatReport();
        freemarkerUtils.generateReport(report);
    }
}
