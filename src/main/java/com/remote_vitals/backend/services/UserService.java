package com.remote_vitals.backend.services;

import com.remote_vitals.entities.StaticDataClass;
import com.remote_vitals.repositories.DoctorUpdateDto;
import com.remote_vitals.repositories.PatientUpdateDto;
import com.remote_vitals.repositories.UserUpdateDto;
import com.remote_vitals.entities.Doctor;
import com.remote_vitals.entities.Patient;
import com.remote_vitals.entities.User;
import com.remote_vitals.repositories.DoctorRepository;
import com.remote_vitals.repositories.PatientRepository;
import com.remote_vitals.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    // Beans
    private final UserRepository userRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    //Constructor
    public UserService(
            UserRepository userRepository,
            PatientRepository patientRepository,
            DoctorRepository doctorRepository
    ) {
        this.userRepository = userRepository;
        this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;
    }
    // Methods
    public Optional<User> getCurrentUser(){
        return userRepository.findById(StaticDataClass.currentUserId);
    }
    @Transactional
    public void updateUser(UserUpdateDto userUpdateDto) {
        userRepository.findById(StaticDataClass.currentUserId).ifPresent(user -> {
            if (userUpdateDto.getPhoneNumber() != null)
                user.setPhoneNumber(userUpdateDto.getPhoneNumber());
            if (userUpdateDto.getPnVisibility() != null)
                user.setPnVisibility(userUpdateDto.getPnVisibility());
            if (userUpdateDto.getEmail() != null)
                user.setEmail(userUpdateDto.getEmail());
            if (userUpdateDto.getEVisibility() != null)
                user.setEVisibility(userUpdateDto.getEVisibility());
            if (userUpdateDto.getEVisibility() != null)
                user.setEVisibility(userUpdateDto.getEVisibility());
            if (userUpdateDto.getPassword() != null)
                user.setPassword(userUpdateDto.getPassword());

            if (user instanceof Patient) {
                if (!(((PatientUpdateDto) userUpdateDto).getMedicalHistory() == null || ((PatientUpdateDto) userUpdateDto).getMedicalHistory().isEmpty()))
                    ((Patient) user).setMedicalHistory(((PatientUpdateDto) userUpdateDto).getMedicalHistory());
                if (!(((PatientUpdateDto) userUpdateDto).getNextOfKinEmail() == null || ((PatientUpdateDto) userUpdateDto).getNextOfKinEmail().isEmpty()))
                    ((Patient) user).setNextOfKinEmail(((PatientUpdateDto) userUpdateDto).getNextOfKinEmail());
            }
            if (user instanceof Doctor) {
                if (!(((DoctorUpdateDto) userUpdateDto).getQualificationString() == null || ((DoctorUpdateDto) userUpdateDto).getQualificationString().isEmpty()))
                    ((Doctor) user).setQualificationString(((DoctorUpdateDto) userUpdateDto).getQualificationString());
                if (!(((DoctorUpdateDto) userUpdateDto).getDescription() == null || ((DoctorUpdateDto) userUpdateDto).getDescription().isEmpty()))
                    ((Doctor) user).setDescription(((DoctorUpdateDto) userUpdateDto).getDescription());
            }
            // Same will be for Admin if we need to update its specific fields
        });
        userRepository.findById(StaticDataClass.currentUserId).orElseThrow(() -> new RuntimeException("Current User Id Is Invalid"));
    }


    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }

}
