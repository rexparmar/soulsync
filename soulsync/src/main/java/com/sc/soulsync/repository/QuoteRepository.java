package com.sc.soulsync.repository;

import com.sc.soulsync.model.Quote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuoteRepository extends JpaRepository<Quote, Long> {
    List<Quote> findByMoodType(String moodType);
}
