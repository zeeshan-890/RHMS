package com.remote_vitals.backend.services;

import com.remote_vitals.entities.CheckUp;
import com.remote_vitals.repositories.CheckUpRepository;
import com.remote_vitals.entities.Doctor;
import com.remote_vitals.entities.Patient;
import com.remote_vitals.entities.User;
import com.remote_vitals.repositories.DoctorRepository;
import com.remote_vitals.repositories.PatientRepository;
import com.remote_vitals.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class CheckUpService {
    // ----------------------------------------------------------------
    private final CheckUpRepository checkUpRepository;
    private final UserRepository userRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    // ----------------------------------------------------------------
    public CheckUpService(
            CheckUpRepository checkUpRepository,
            UserRepository userRepository,
            PatientRepository patientRepository,
            DoctorRepository doctorRepository
    ){
        this.checkUpRepository = checkUpRepository;
        this.userRepository = userRepository;
        this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;
    }
    // ----------------------------------------------------------------
    @Transactional
    public String submitCheckUp(
        Integer patientId,
        Integer doctorId,
        String prescription,
        String feedback
    ){
        if(patientId == null || doctorId == null) return "Invalid Arguments";
        if(patientRepository.findById(patientId).isEmpty()) return "Patient not found";
        if(doctorRepository.findById(doctorId).isEmpty()) return "Doctor not found";
        Patient patient = patientRepository.findById(patientId).get();
        Doctor doctor = doctorRepository.findById(doctorId).get();
        CheckUp checkUp = new CheckUp(
            prescription,feedback,
            LocalDateTime.now(),
            patient,doctor
        );
        if(patient.getCheckUps() == null) patient.setCheckUps(Arrays.asList(checkUp));
        else patient.getCheckUps().add(checkUp);
        if(doctor.getCheckUps() == null) doctor.setCheckUps(Arrays.asList(checkUp));
        else doctor.getCheckUps().add(checkUp);
        checkUpRepository.save(checkUp);
        return "CheckUp Info Submitted";
    }
    // ----------------------------------------------------------------
    public List<CheckUp> getAllCheckUpsOf(
            Integer userId,
            Class<? extends User> type
    ) {
        if(userId == null) return null;
        if(
                type != Patient.class &&
                type != Doctor.class
        ) return null;
        Optional<User> wrappedUser =  userRepository.findById(userId);
        if(wrappedUser.isEmpty()) return null;
        if(wrappedUser.get().getClass() != type) return null;

        if(type == Patient.class)
            if(((Patient) wrappedUser.get()).getCheckUps() == null)
                return new ArrayList<>();
            else
                return ((Patient) wrappedUser.get()).getCheckUps();
        else
            if(((Doctor) wrappedUser.get()).getCheckUps() == null)
                return new ArrayList<>();
            else
                return ((Doctor) wrappedUser.get()).getCheckUps();
    }
}
