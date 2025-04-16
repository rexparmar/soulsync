package com.sc.soulsync.repository;

import com.sc.soulsync.model.Mood;
import com.sc.soulsync.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MoodRepository extends JpaRepository<Mood,Long> {
    List<Mood> findByUser(User user);
}
