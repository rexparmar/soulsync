package com.sc.soulsync.repository;

import com.sc.soulsync.model.Mood;
import com.sc.soulsync.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;

public interface MoodRepository extends JpaRepository<Mood,Long> {
    List<Mood> findByUser(User user);
    List<Mood> findByUserAndCreatedAtBetween(User user, LocalDateTime start, LocalDateTime end);
    List<Mood> findByUserAndIsSavedTrue(User user);
}
