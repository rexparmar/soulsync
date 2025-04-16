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

import java.util.List;

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
}
