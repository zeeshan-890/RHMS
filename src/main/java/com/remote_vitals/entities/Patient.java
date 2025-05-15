// ---------------------- // BEGIN // --------------------- //
package com.remote_vitals.entities;

// imports
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.remote_vitals.repositories.Gender;
import com.remote_vitals.frontend.controllers.VitalReport;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * Patient entity class that extends the base User class.
 * Represents patients in the system with their medical information and relationships.
 * 
 * Lombok annotations:
 * @Data - Generates getters, setters, toString, equals, and hashCode
 * @NoArgsConstructor - Generates constructor with no parameters
 * @AllArgsConstructor - Generates constructor with all parameters
 * @EqualsAndHashCode - Generates equals and hashCode methods
 * @SuperBuilder - Enables builder pattern with inheritance support
 */
@Data
@ToString(exclude = {
        "appointments","checkUps","vitalReports"
})
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@SuperBuilder

/**
 * JPA annotations:
 * @Entity - Marks this class as a JPA entity
 * @Table - Specifies the table name for this entity
 * @PrimaryKeyJoinColumn - Specifies the foreign key column for the joined inheritance strategy
 */
@Entity
@Table(name = "patient")
@PrimaryKeyJoinColumn(name = "user_id", columnDefinition = "int")
public class Patient extends User {
    /******************** Attributes ********************/
    @Column(columnDefinition = "text")
    private String medicalHistory;

    /** Patient's blood group with validation pattern */
    @Column(name = "blood_group", length = 3)
    @Pattern(regexp = "^(A|B|AB|O)[+-]$", message = "Invalid blood group format")
    private String bloodGroup;

    /** Patient's date of birth with validation to ensure it's in the past */
    @Column(name = "date_of_birth", nullable = true)
    @Past(message = "Date of birth must be in the past")
    private LocalDate dateOfBirth;

    @Email
    private String nextOfKinEmail;
    /******************** Relationships ********************/
    /** List of appointments scheduled for this patient */
    @OneToMany(
            fetch = FetchType.EAGER,
            mappedBy = "patient",
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
            orphanRemoval = true
    )
    private List<Appointment> appointments;

    /** List of vital reports containing the patient's medical measurements */
    @OneToMany(
            fetch = FetchType.EAGER,
            mappedBy = "patient",
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
            orphanRemoval = true
    )
    private List<VitalReport> vitalReports;

    /** List of medical checkups performed on this patient */
    @OneToMany(
            fetch = FetchType.EAGER,
            mappedBy = "patient",
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
            orphanRemoval = true
    )
    private List<CheckUp> checkUps;

    /******************** Constructors ********************/
    /**
     * Constructor for creating a new patient with basic information
     * @param firstName First name of the patient
     * @param lastName Last name of the patient
     * @param gender Gender of the patient
     * @param phoneNumber Contact phone number
     * @param email Email address
     * @param password Account password
     * @param medicalHistory Medical History (optional)
     * @param bloodGroup Blood group (must match pattern A/B/AB/O with +/-)
     * @param dateOfBirth Date of birth (must be in the past)
     */
    public Patient(
            String firstName,
            String lastName,
            Gender gender,
            String phoneNumber,
            String email,
            String password,
            String medicalHistory,
            String bloodGroup,
            LocalDate dateOfBirth
    ) {
        super(firstName, lastName, gender, phoneNumber, email, password);
        this.medicalHistory = medicalHistory;
        this.bloodGroup = bloodGroup;
        this.dateOfBirth = dateOfBirth;
        this.appointments = new ArrayList<>();
        this.checkUps = new ArrayList<>();
        this.vitalReports = new ArrayList<>();
    }

    /**
     * Constructor for creating a new patient with basic information
     * @param firstName First name of the patient
     * @param lastName Last name of the patient
     * @param gender Gender of the patient
     * @param phoneNumber Contact phone number
     * @param email Email address
     * @param password Account password
     * @param medicalHistory Medical History (optional)
     * @param bloodGroup Blood group (must match pattern A/B/AB/O with +/-)
     * @param dateOfBirth Date of birth (must be in the past)
     * @param nextOfKinEmail email of the next of kin to the patient
     */
    public Patient(
            String firstName,
            String lastName,
            Gender gender,
            String phoneNumber,
            String email,
            String password,
            String medicalHistory,
            String bloodGroup,
            LocalDate dateOfBirth,
            String nextOfKinEmail
    ) {
        super(firstName, lastName, gender, phoneNumber, email, password);
        this.medicalHistory = medicalHistory;
        this.bloodGroup = bloodGroup;
        this.dateOfBirth = dateOfBirth;
        this.nextOfKinEmail = nextOfKinEmail;
        this.appointments = new ArrayList<>();
        this.checkUps = new ArrayList<>();
        this.vitalReports = new ArrayList<>();
    }

//     public Patient(String firstName, String lastName, Gender gender, String phoneNumber, String email, String password, String medicalHistory, String bloodGroup, LocalDate dateOfBirth) {
//         super(firstName, lastName, gender, phoneNumber, email, password);
//         this.medicalHistory = medicalHistory;
//         this.bloodGroup = bloodGroup;
//         this.dateOfBirth = dateOfBirth;
//         this.nextOfKinEmail = nextOfKinEmail;
//         this.appointments = new ArrayList<>();
//         this.vitalReports = new ArrayList<>();
//         this.checkUps = new ArrayList<>();
//     }
}
