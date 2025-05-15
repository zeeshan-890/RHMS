package com.remote_vitals.backend.services;

import com.remote_vitals.entities.StaticDataClass;
import com.remote_vitals.entities.User;
import com.remote_vitals.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    //Beans
    private final UserRepository userRepository;

    // Constructor
    public LoginService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    /******************** Service Methods ********************/
    public void login(String email, String password, Class<? extends User> userClass) {
        if (email == null || email.isBlank())
            throw new RuntimeException("Email field is empty");
        if (password == null || password.isBlank())
            throw new RuntimeException("Password field is empty");

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));
        if(userClass != user.getClass())
            throw new RuntimeException("Invalid credentials");
        if (!user.getPassword().equals(password)) {
            throw new RuntimeException("Invalid credentials");
        }
        StaticDataClass.currentUserId = user.getId();
    }
}
