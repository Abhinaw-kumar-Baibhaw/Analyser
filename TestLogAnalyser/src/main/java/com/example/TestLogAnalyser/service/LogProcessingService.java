package com.example.TestLogAnalyser.service;

import com.example.TestLogAnalyser.model.UserLog;
import com.example.TestLogAnalyser.repo.UserLogRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Service
public class LogProcessingService {

    @Autowired
    private UserLogRepository userLogRepository;

    private static final String LOG_PATTERN = "(\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}) (INFO|ERROR) User: (\\w+) (.*)";

    @Transactional
    public void processLogFile(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            Pattern pattern = Pattern.compile(LOG_PATTERN);
            while ((line = br.readLine()) != null) {
                Matcher matcher = pattern.matcher(line);
                if (matcher.matches()) {
                    String timestampStr = matcher.group(1);
                    String status = matcher.group(2);
                    String user = matcher.group(3);
                    String action = matcher.group(4);
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                    LocalDateTime timestamp = LocalDateTime.parse(timestampStr, formatter);
                    UserLog userLog = new UserLog();
                    userLog.setTimestamp(timestamp);
                    userLog.setUser(user);
                    userLog.setAction(action);
                    userLog.setStatus(status);
                    userLogRepository.save(userLog);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Scheduled(fixedRate = 5000)
    public void processLogs() {
        System.out.println("Processing logs at: " + java.time.LocalDateTime.now());
    }
}
