package com.sc.soulsync.service;

import com.sc.soulsync.model.Quote;
import com.sc.soulsync.repository.QuoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class QuoteService {
    @Autowired
    private QuoteRepository quoteRepo;

    public Map<String, String> getQuoteForMood(String moodType){
        List<Quote> quoteList = quoteRepo.findByMoodType(moodType);
            if (quoteList.isEmpty()) {
                Map<String, String> fallback = new HashMap<>();
                fallback.put("quote", "No quote found for this mood 😔");
                return fallback;
            }
        int random = (int) (Math.random() * quoteList.size());
        Quote randomQuote = quoteList.get(random);
        Map<String, String> response = new HashMap<>();
        response.put("mood", moodType);
        response.put("quote", randomQuote.getQuote());
        return response;
    }
}
