package com.sc.soulsync.controller;

import com.sc.soulsync.dto.MoodEntryRequest;
import com.sc.soulsync.dto.MoodEntryResponse;
import com.sc.soulsync.model.User;
import com.sc.soulsync.repository.MoodRepository;
import com.sc.soulsync.repository.UserRepository;
import com.sc.soulsync.service.MoodService;
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
    
    @PostMapping
    public MoodEntryResponse addMood(@RequestBody MoodEntryRequest moodEntryRequest){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        System.out.println("Authenticated user: " +
                SecurityContextHolder.getContext().getAuthentication());
        return moodService.addMood(moodEntryRequest,email);
    }

    @GetMapping("/{userId}")
    public List<MoodEntryResponse> getMoods(@PathVariable Long userId){
        User user = userRepo.findById(userId).orElseThrow(()->new UsernameNotFoundException("User not found!"));
        String userEmail = user.getEmail();
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
}
