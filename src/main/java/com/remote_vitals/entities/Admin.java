// ---------------------- // BEGIN // --------------------- //
package com.remote_vitals.entities;

// imports
import com.remote_vitals.repositories.Gender;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

// lombok annotations
@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@SuperBuilder

// JPA annotations
@Entity
@Table(name = "admin")
@PrimaryKeyJoinColumn(name = "user_id", columnDefinition = "int")
public class Admin extends User {
    /******************** Constructors ********************/
    public Admin(String firstName, String lastName, Gender gender, String phoneNumber, String email, String password) {
        super(firstName, lastName, gender, phoneNumber, email, password);
    }
}
