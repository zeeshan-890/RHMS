package com.remote_vitals.backend.services;

import com.remote_vitals.entities.User;
import com.remote_vitals.repositories.UserRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class SignUpService {
    /******************** Beans ********************/
    private final UserRepository userRepository;

    /******************** Constructor ********************/
    public SignUpService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    /******************** Service Methods ********************/
    public int signUp(User userDetails) {
        try {
            userRepository.save(userDetails);
        } catch(DataIntegrityViolationException ex){
            return -1;
        }
        return 0;
    }
}
