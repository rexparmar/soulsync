package com.sc.soulsync.controller;

import com.sc.soulsync.dto.MoodEntryRequest;
import com.sc.soulsync.dto.MoodEntryResponse;
import com.sc.soulsync.model.User;
import com.sc.soulsync.repository.MoodRepository;
import com.sc.soulsync.repository.UserRepository;
import com.sc.soulsync.service.MoodService;
import com.sc.soulsync.service.QuoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/mood")
public class MoodController {
    @Autowired
    private MoodRepository moodRepo;
    @Autowired
    private MoodService moodService;
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private QuoteService quoteService;


    @PostMapping
    public MoodEntryResponse addMood(@RequestBody MoodEntryRequest moodEntryRequest){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        System.out.println("Authenticated user: " +
                SecurityContextHolder.getContext().getAuthentication());
        return moodService.addMood(moodEntryRequest,email);
    }

    @GetMapping()
    public List<MoodEntryResponse> getMoods(String userEmail){
        userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        return moodService.getMoodEntries(userEmail);
    }

    @GetMapping("/stats")
    public Map<String, Integer> getMoodStats(){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return moodService.getMoodStats(email);
    }

    @GetMapping("/stats/weekly")
    public Map<LocalDate, Map<String, Integer>> getWeeklyStats(){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return moodService.getWeeklystats(email);
    }

    @GetMapping("/stats/streak")
    public Map<String, Integer> getStreak(){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return moodService.getMoodStreakStats(email);
    }

    @GetMapping("/stats/frequent")
    public Map<String, Object> getMostFrequentMood() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return moodService.getMostFrequentMood(email);
    }

    @GetMapping("/suggestions")
    public Map<String, String> getQuote(@RequestParam String mood) {
        return quoteService.getQuoteForMood(mood);
    }

    @GetMapping("/stats/dashboard")
    public Map<String, Object> getDashboardStats() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return moodService.getDashboardStats(email);
    }

    @GetMapping("/saved")
    public List<MoodEntryResponse> getSaved(String userEmail){
        userEmail=SecurityContextHolder.getContext().getAuthentication().getName();
        return moodService.getSavedMood(userEmail);
    }

    @PutMapping("/save/{moodId}")
    public Map<String,String> saveMood(@PathVariable Long moodId) throws Exception{
        String userEmail=SecurityContextHolder.getContext().getAuthentication().getName();
        return moodService.saveMoodEntry(moodId,userEmail);
    }


    @PutMapping("/unsave/{moodId}")
    public Map<String,String> unSaveMood(@PathVariable Long moodId) throws Exception{
        String userEmail=SecurityContextHolder.getContext().getAuthentication().getName();
        return moodService.unSaveMoodEntry(moodId,userEmail);
    }


}
