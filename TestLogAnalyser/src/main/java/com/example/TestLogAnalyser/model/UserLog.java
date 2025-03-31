package com.example.TestLogAnalyser.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class UserLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime timestamp;

    private String user;

    private String action;

    private String status;

    public UserLog(){

    }

    public UserLog(Long id, LocalDateTime timestamp, String user, String action, String status) {
        this.id = id;
        this.timestamp = timestamp;
        this.user = user;
        this.action = action;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "UserLog{" +
                "id=" + id +
                ", timestamp=" + timestamp +
                ", user='" + user + '\'' +
                ", action='" + action + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}

