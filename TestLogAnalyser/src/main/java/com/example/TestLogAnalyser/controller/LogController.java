package com.example.TestLogAnalyser.controller;

import com.example.TestLogAnalyser.model.UserLog;
import com.example.TestLogAnalyser.repo.UserLogRepository;
import com.example.TestLogAnalyser.service.LogProcessingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/logs")
public class LogController {

    @Autowired
    private UserLogRepository userLogRepository;

    @Autowired
    private LogProcessingService logProcessingService;

    @GetMapping("/recent")
    public List<UserLog> getRecentLogs(@RequestParam String user) {
        return userLogRepository.findTop5ByUserOrderByTimestampDesc(user);
    }

    @GetMapping("/errors")
    public List<UserLog> getErrorLogs() {
        return userLogRepository.findByStatus("ERROR");
    }

    @GetMapping("/top-users")
    public List<String> getTopUsers() {
        LocalDateTime timestamp = LocalDateTime.now().minusHours(24);
        List<UserLog> logs = userLogRepository.findByTimestampAfterAndStatus(timestamp, "INFO");
        return logs.stream()
                .map(UserLog::getUser)
                .distinct()
                .limit(3)
                .toList();
    }

    @GetMapping("/failed-logins")
    public List<String> getFailedLoginAttempts() {
        List<Object[]> result = userLogRepository.countFailedLoginAttempts();
        return result.stream()
                .map(r -> "User: " + r[0] + ", Failed Attempts: " + r[1])
                .collect(Collectors.toList());
    }

    @GetMapping("/most-active-users")
    public List<String> getMostActiveUsers() {
        LocalDateTime timestamp = LocalDateTime.now().minusHours(24);
        List<Object[]> result = userLogRepository.findMostActiveUsers(timestamp);
        return result.stream()
                .map(r -> "User: " + r[0] + ", Activity Count: " + r[1])
                .collect(Collectors.toList());
    }


    @PostMapping("/process-log-file")
    public String processLogFile(@RequestParam String filePath) {
        logProcessingService.processLogFile(filePath);
        return "Log file processed successfully";
    }
}
