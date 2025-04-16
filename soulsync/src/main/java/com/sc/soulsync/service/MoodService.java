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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
}
