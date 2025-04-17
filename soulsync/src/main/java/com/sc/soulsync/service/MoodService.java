package com.sc.soulsync.service;

import com.sc.soulsync.dto.MoodEntryRequest;
import com.sc.soulsync.dto.MoodEntryResponse;
import com.sc.soulsync.model.Mood;
import com.sc.soulsync.model.User;
import com.sc.soulsync.repository.MoodRepository;
import com.sc.soulsync.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MoodService {
    @Autowired
    private MoodRepository moodRepo;
    @Autowired
    private UserRepository userRepo;

    public MoodEntryResponse addMood(MoodEntryRequest moodEntryRequest, String userEmail){
        User user = userRepo.findByEmail(userEmail).orElseThrow(()-> new UsernameNotFoundException("user not found!"));
        Mood mood = new Mood();
        mood.setMood(moodEntryRequest.getMood());
        mood.setJournalText(moodEntryRequest.getJournalText());
        mood.setCreatedAt(LocalDateTime.now());
        mood.setUser(user);
        moodRepo.save(mood);
        return new MoodEntryResponse(moodEntryRequest.getMood(),moodEntryRequest.getJournalText(),LocalDateTime.now());
    }

    public List<MoodEntryResponse> getMoodEntries(String email){
        User user = userRepo.findByEmail(email).orElseThrow(()-> new UsernameNotFoundException("User not found!"));
        List<Mood> moodList=moodRepo.findByUser(user);
        List<MoodEntryResponse> responseList = new ArrayList<>();
        for(Mood mood : moodList){
            MoodEntryResponse res = new MoodEntryResponse(mood.getMood(),mood.getJournalText(),mood.getCreatedAt());
            responseList.add(res);
        }
        return responseList;
    }

    public Map<String, Integer> getMoodStats(String userEmail){
        User user = userRepo.findByEmail(userEmail).orElseThrow(()->new UsernameNotFoundException("User not found with this email."));
        List<Mood> moodList = moodRepo.findByUser(user);
        Map<String, Integer> moodCounts = new HashMap<>();
        for(Mood mood : moodList){
            String moodType = mood.getMood();
            if(moodList.contains(moodType)){
                moodCounts.put(moodType,moodCounts.get(moodType)+1);
            }else{
                moodCounts.put(moodType,1);
            }
        }
        return moodCounts;
    }

    public Map<LocalDate, Map<String,Integer>> getWeeklystats(String userEmail){
        User user = userRepo.findByEmail(userEmail).orElseThrow(()-> new UsernameNotFoundException("User not found with this email."));
        LocalDate today = LocalDate.now();
        LocalDate oneWeekAgo = today.minusDays(6);
        LocalDateTime start = oneWeekAgo.atStartOfDay();
        LocalDateTime end = today.atTime(LocalTime.MAX);
        List<Mood> moodList = moodRepo.findByUserAndCreatedAtBetween(user,start,end);

        HashMap<LocalDate, Map<String, Integer>> moodStats = new HashMap<>();
        for(Mood mood : moodList){
            LocalDate createdDate = mood.getCreatedAt().toLocalDate();
            String moodType = mood.getMood();
            moodStats.putIfAbsent(createdDate, new HashMap<>());
            Map<String,Integer> dailyMoodMap = moodStats.get(createdDate);
            dailyMoodMap.put(moodType,dailyMoodMap.getOrDefault(moodType,0)+1);
        }
        return moodStats;
    }
}
