package com.sc.soulsync.controller;

import com.sc.soulsync.dto.ReminderRequest;
import com.sc.soulsync.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/profile")
    public ResponseEntity<String> getProfile() {
        return ResponseEntity.ok("You accessed a protected route!");
    }

    @PutMapping("/reminder")
    public ResponseEntity<String> setReminder(@RequestBody ReminderRequest request) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        userService.updateReminder(email, request);
        return ResponseEntity.ok("Reminder settings updated!");
    }


}
