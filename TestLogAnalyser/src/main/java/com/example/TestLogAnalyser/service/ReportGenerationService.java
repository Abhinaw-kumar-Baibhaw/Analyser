package com.example.TestLogAnalyser.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class ReportGenerationService {

    @Scheduled(fixedRate = 5000)
    public void generateDailyReport() {
        String reportContent = generateReportContent();
        String reportFileName = "daily-summary-" + getCurrentDate() + ".txt";
        File reportDirectory = new File("C:\\Users\\abhin\\OneDrive\\Desktop\\TestResult\\invoices");
        if (!reportDirectory.exists()) {
            reportDirectory.mkdirs();
        }
        File reportFile = new File(reportDirectory, reportFileName);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(reportFile))) {
            writer.write(reportContent);
            System.out.println("Report generated successfully: " + reportFile.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Failed to generate report: " + e.getMessage());
        }
    }

    private String generateReportContent() {
        StringBuilder reportContent = new StringBuilder();
        reportContent.append("Daily Summary Report\n");
        reportContent.append("Generated on: ").append(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))).append("\n");
        reportContent.append("\n--- Summary of Logs ---\n");
        reportContent.append("Total Logs: 1000\n");
        reportContent.append("Errors: 50\n");
        reportContent.append("Info: 950\n");
        return reportContent.toString();
    }

    private String getCurrentDate() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }
}

