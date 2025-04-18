package com.sc.soulsync.service;

import com.sc.soulsync.dto.AuthResponse;
import com.sc.soulsync.dto.LoginRequest;
import com.sc.soulsync.dto.RegisterRequest;
import com.sc.soulsync.dto.ReminderRequest;
import com.sc.soulsync.model.User;
import com.sc.soulsync.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtService jwtService;

    public AuthResponse register(RegisterRequest registerRequest) throws Exception{
        String userEmail =  registerRequest.getEmail();
        boolean isExist = userRepo.existsByEmail(userEmail);
        if(isExist){
            throw new Exception("User already exists with given email! Please use another email or login with this email.");
        }
        else{
            String password = passwordEncoder.encode(registerRequest.getPassword());
            User newUser = new User();
            newUser.setName(registerRequest.getName());
            newUser.setEmail(registerRequest.getEmail());
            newUser.setCreatedAt(LocalDate.now());
            newUser.setPasswordHash(password);
            userRepo.save(newUser);
            return new AuthResponse("Register Successful!",null);
        }
    }

    public AuthResponse login(LoginRequest loginRequest) throws Exception{
        String email = loginRequest.getEmail();
        User loginUser = userRepo.findByEmail(loginRequest.getEmail()).orElseThrow(()->
                new Exception("User not found with this email. Consider re-checking email or make sure you have registered account with us!"));
        if(passwordEncoder.matches(loginRequest.getPassword(), loginUser.getPasswordHash())){
            return new AuthResponse("Login Successful!",jwtService.generateToken(loginUser.getEmail()));
        }
        return new AuthResponse("Login Failed! Please check credentials once again. Make sure email and passwords are correct!");
    }

    public void updateReminder(String userEmail, ReminderRequest reminderRequest){
        User user = userRepo.findByEmail(userEmail).orElseThrow(()->
                new UsernameNotFoundException("User not found!"));
        user.setReminderTime(LocalTime.parse(reminderRequest.getTime()));
        user.setReminderEnabled(reminderRequest.isEnabled());
        userRepo.save(user);
    }
}
