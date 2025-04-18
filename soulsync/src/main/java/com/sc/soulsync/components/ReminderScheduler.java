package com.sc.soulsync.components;

import com.sc.soulsync.model.User;
import com.sc.soulsync.repository.UserRepository;
import com.sc.soulsync.service.EmailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.List;

@Component
public class ReminderScheduler {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private EmailSenderService emailSenderService;

    @Scheduled(fixedRate = 60000) // runs every 1 minute
    public void checkAndSendReminders() {
        LocalTime now = LocalTime.now().withSecond(0).withNano(0);

        List<User> users = userRepo.findAll();
        for (User user : users) {
            if (user.isReminderEnabled() && user.getReminderTime() != null) {
                if (now.equals(user.getReminderTime().withSecond(0).withNano(0))) {
                    emailSenderService.sendReminderEmail(
                            user.getEmail(),
                            "Don't forget to log your mood today!",
                            "Hi " + user.getName() + ",\n\nTake a moment to log your mood and reflect. 🌿\n\n– SoulSync"
                    );
                }
            }
        }
    }
}
