package com.example.TestLogAnalyser.repo;

import com.example.TestLogAnalyser.model.UserLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface UserLogRepository extends JpaRepository<UserLog, Long> {

    List<UserLog> findTop5ByUserOrderByTimestampDesc(String user);

    List<UserLog> findByStatus(String status);

    List<UserLog> findByTimestampAfterAndStatus(LocalDateTime timestamp, String status);

    @Query("SELECT u.user, COUNT(u) FROM UserLog u WHERE u.timestamp > :timestamp AND u.status = 'INFO' GROUP BY u.user ORDER BY COUNT(u) DESC")
    List<Object[]> findMostActiveUsers(@Param("timestamp") LocalDateTime timestamp);

    @Query("SELECT u.user, COUNT(u) FROM UserLog u WHERE u.status = 'ERROR' GROUP BY u.user")
    List<Object[]> countFailedLoginAttempts();
}
