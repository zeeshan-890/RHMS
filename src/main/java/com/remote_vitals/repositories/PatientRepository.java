package com.remote_vitals.repositories;

import com.remote_vitals.entities.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Integer> {
    Optional<Patient> findByEmail(String email);
    
    /**
     * Find all patients assigned to a specific doctor
     * 
     * @param doctorId the ID of the doctor
     * @return list of patients assigned to the doctor
     */
    @Query(value = "SELECT p.* FROM patient p " +
           "JOIN appointment a ON p.user_id = a.patient_id " +
           "WHERE a.doctor_id = :doctorId " +
           "GROUP BY p.user_id", nativeQuery = true)
    List<Patient> findByDoctorId(@Param("doctorId") Integer doctorId);
    
    /**
     * Find patients by blood group
     * 
     * @param bloodGroup the blood group to search for
     * @return list of patients with the specified blood group
     */
    List<Patient> findByBloodGroup(String bloodGroup);
    
    /**
     * Find patients by name (first name or last name containing the search term)
     * 
     * @param name the name to search for
     * @return list of patients matching the name search
     */
    List<Patient> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(
            String firstName, String lastName);
}
