// ---------------------- // BEGIN // --------------------- //
package com.remote_vitals.entities;

// imports
import java.util.ArrayList;
import java.util.List;

import com.remote_vitals.repositories.Gender;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * Doctor entity class that extends the base User class.
 * Represents medical professionals in the system with their specialties and relationships.
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
        "appointments","checkUps"
})
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@SuperBuilder

/**
 * JPA annotations:
 * @Entity - Marks this class as a JPA entity
 * @PrimaryKeyJoinColumn - Specifies the foreign key column for the joined inheritance strategy
 */
@Entity
@PrimaryKeyJoinColumn(name = "user_id", columnDefinition = "int")
public class Doctor extends User {
    /******************** Attributes ********************/
    @Column(columnDefinition = "text")
    private String qualificationString;

    /** Professional description or biography of the doctor */
    @Column(columnDefinition = "text")
    private String description;

    /******************** Relationships ********************/
    /** List of appointments associated with this doctor */
    @OneToMany(
            fetch = FetchType.EAGER,
            mappedBy = "doctor",
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
            orphanRemoval = true
    )
    private List<Appointment> appointments;

    /** List of medical checkups performed by the doctor */
    @OneToMany(
            fetch = FetchType.EAGER,
            mappedBy = "doctor",
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
            orphanRemoval = true
    )
    private List<CheckUp> checkUps;

    /******************** Constructors ********************/
    /**
     * Constructor for creating a new doctor with basic information
     * Initializes empty lists for appointments, qualifications, and checkups
     */
    public Doctor(
            String firstName,
            String lastName,
            Gender gender,
            String phoneNumber,
            String email,
            String password,
            String qualificationString,
            String description
    ) {
        super(firstName, lastName, gender, phoneNumber, email, password);
        this.qualificationString = qualificationString;
        this.description = description;
        this.appointments = new ArrayList<>();
        this.checkUps = new ArrayList<>();
    }

    public Doctor(
            String firstName,
            String lastName,
            Gender gender,
            String phoneNumber,
            String email,
            String password
    ) {
        super(firstName, lastName, gender, phoneNumber, email, password);
        this.appointments = new ArrayList<>();
        this.checkUps = new ArrayList<>();
    }
}
