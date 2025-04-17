package com.sc.soulsync.service;

import com.sc.soulsync.dto.MoodEntryRequest;
import com.sc.soulsync.dto.MoodEntryResponse;
import com.sc.soulsync.model.Mood;
import com.sc.soulsync.model.User;
import com.sc.soulsync.repository.MoodRepository;
import com.sc.soulsync.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

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

    public Map<String, Integer> getMoodStreakStats(String userEmail){
        User user = userRepo.findByEmail(userEmail).orElseThrow(()->new UsernameNotFoundException("User not found!"));
        List<Mood> moodList = moodRepo.findByUser(user);
        Set<LocalDate> dates = new HashSet<>();
        for(Mood mood : moodList){
            dates.add(mood.getCreatedAt().toLocalDate());
        }
        List<LocalDate> sortedDates = new ArrayList<>(dates);
        Collections.sort(sortedDates);
        
        int currentStreak = 0;
        int maxStreak = 0;
        int streakCounter = 0;

        for(int i = 1; i<sortedDates.size();i++){
            LocalDate prev = sortedDates.get(i-1);
            LocalDate current = sortedDates.get(i);

            if(prev.plusDays(1).equals(current)){
                streakCounter++;
            }else{
                maxStreak = Math.max(maxStreak,streakCounter);
                streakCounter=1;
            }
        }
        maxStreak = Math.max(maxStreak, streakCounter);

        LocalDate today = LocalDate.now();

        while(dates.contains(today.minusDays(currentStreak))){
            currentStreak++;
        }

        Map<String, Integer> streaks = new HashMap<>();
        streaks.put("currentStreak", currentStreak);
        streaks.put("maxStreak", maxStreak);
        return streaks;
    }

    public Map<String, Object> getMostFrequentMood(String userEmail){
        User user = userRepo.findByEmail(userEmail).orElseThrow(()-> new UsernameNotFoundException("User not found!"));
        List<Mood> moods = moodRepo.findByUser(user);
        Map<String, Integer> moodCount = new HashMap<>();
        String topMood = null;
        int maxCount = 0;
        for (Mood mood : moods) {
            String moodType = mood.getMood();
            moodCount.put(moodType, moodCount.getOrDefault(moodType, 0) + 1);
        }

        for(Map.Entry<String, Integer> entry : moodCount.entrySet()){
            if(entry.getValue() > maxCount){
                maxCount = entry.getValue();
                topMood = entry.getKey();
            }
        }
        Map<String, Object> result = new HashMap<>();
        result.put("mostFrequentMood", topMood);
        result.put("count", maxCount);
        return result;
    }
}
