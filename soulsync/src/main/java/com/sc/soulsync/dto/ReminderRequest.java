package com.sc.soulsync.dto;

public class ReminderRequest {
    private String time;          // Example: "21:00"
    private boolean enabled;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
