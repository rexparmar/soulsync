package com.sc.soulsync.dto;

public class MoodEntryRequest {
    private String mood;
    private String journalText;
    private boolean saved;


    public MoodEntryRequest(){};

    public MoodEntryRequest(String mood, String journalText){
        this.mood=mood;
        this.journalText=journalText;
    }

    public void setMood(String mood){
        this.mood=mood;
    }

    public String getMood(){
        return mood;
    }

    public void setJournalText(String journalText){
        this.journalText=journalText;
    }

    public String getJournalText(){
        return journalText;
    }

    public boolean isSaved() {
        return saved;
    }

    public void setSaved(boolean saved) {
        this.saved = saved;
    }
}
