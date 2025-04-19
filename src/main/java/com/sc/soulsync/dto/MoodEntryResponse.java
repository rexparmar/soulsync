package com.sc.soulsync.dto;

import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

public class MoodEntryResponse {
    private String mood;
    private String journalText;
    private LocalDateTime createdAt;
    private String quote;



    private boolean saved;


    public MoodEntryResponse(){};
    
    public MoodEntryResponse(String mood, String journalText, LocalDateTime createdAt, String quote){
        this.mood=mood;
        this.journalText=journalText;
        this.createdAt=createdAt;
        this.quote=quote;
    }

    public MoodEntryResponse(String mood, String journalText, LocalDateTime createdAt, String quote, boolean saved){
        this.mood=mood;
        this.journalText=journalText;
        this.createdAt=createdAt;
        this.quote=quote;
        this.saved=saved;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getMood() {
        return mood;
    }

    public void setMood(String mood) {
        this.mood = mood;
    }

    public String getJournalText() {
        return journalText;
    }

    public void setJournalText(String journalText) {
        this.journalText = journalText;
    }

    public void setQuote(String quote){
        this.quote=quote;
    }

    public String getQuote(){
        return quote;
    }

    public boolean isSaved() {
        return saved;
    }

    public void setSaved(boolean saved) {
        this.saved = saved;
    }
}
