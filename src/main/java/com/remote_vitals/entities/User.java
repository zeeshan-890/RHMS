package com.remote_vitals.entities;

import com.remote_vitals.repositories.Gender;
import com.remote_vitals.repositories.Visibility;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.TableGenerator;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * Base User entity class that serves as the parent class for all user types in the system.
 * Uses JPA inheritance with JOINED strategy, meaning each user type will have its own table
 * with a foreign key reference to the base user table.
 * 
 * Lombok annotations are used to reduce boilerplate code:
 * @Data - Generates getters, setters, toString, equals, and hashCode
 * @NoArgsConstructor - Generates constructor with no parameters
 * @AllArgsConstructor - Generates constructor with all parameters
 * @SuperBuilder - Enables builder pattern with inheritance support
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class User {
    /******************** Attributes ********************/
    /**
     * Primary key for the user entity
     * Uses TABLE strategy for ID generation to ensure uniqueness across all user types
     */
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "UserGenerator")
    @TableGenerator(
            name = "UserGenerator",
            table = "id_generator",
            pkColumnName = "table_of_pk",
            valueColumnName = "value",
            pkColumnValue = "user",
            allocationSize = 10
    )
    private Integer id;

    /** Path or URL to the user's profile photo */
    private String profilePhoto;

    /** User's first name - required field */
    @Column(nullable = false)
    private String firstName;

    /** User's last name - required field */
    @Column(nullable = false)
    private String lastName;

    /** User's gender - stored as an enumerated type */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Gender gender;

    /** User's phone number */
    @Column(nullable = false,unique = true, length = 15)
    private String phoneNumber;

    /** Visibility setting for phone number */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Visibility pnVisibility;

    /** User's email address - required, unique, and validated */
    @Email
    @Column(name = "email", nullable = false, unique = true, length = 255)
    private String email;

    /** Visibility setting for email address */
    @Enumerated(EnumType.STRING)
    private Visibility eVisibility;

    /** User's password - required field */
    @Column(nullable = false)
    private String password;

    /******************** Constructor ********************/
    /**
     * Constructor for creating a new user with basic information
     * Sets default visibility to PRIVATE for phone and email
     */
    public User(String firstName, String lastName, Gender gender, String phoneNumber, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.pnVisibility = Visibility.PRIVATE;
        this.email = email;
        this.eVisibility = Visibility.PRIVATE;
        this.password = password;
    }
}
